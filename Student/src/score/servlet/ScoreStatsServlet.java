package score.servlet;

import impl.StudentImpl;
import impl.TeacherImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;
import util.DB;
import entity.Operator;
import entity.Student;
import entity.Teacher;

public class ScoreStatsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");

		Operator operator = (Operator) request.getSession().getAttribute("log_operator");
		if (operator == null) {
			response.getWriter().write("{\"error\":\"未登录\"}");
			return;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> overall = new HashMap<String, Object>();
		Map<String, Integer> buckets = new HashMap<String, Integer>();
		List<Map<String, Object>> subjectStats = new ArrayList<Map<String, Object>>();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		String whereSql = "";
		List<String> params = new ArrayList<String>();
		String scopeLabel = "全校成绩";
		int roleId = operator.getRole().getId();

		try {
			if (roleId == 2) {
				Teacher teacher = new TeacherImpl().query("ope_id", String.valueOf(operator.getId())).get(0);
				scopeLabel = teacher.getName() + "（教师）可见成绩";
				whereSql = " WHERE (s.stu_id IN (SELECT stu_id FROM student WHERE cla_id IN "
						+ "(SELECT cla_id FROM classes WHERE cla_tec = ?)) "
						+ "OR s.cla2sub_id IN (SELECT cla2sub_id FROM cla2sub WHERE tec_id IN "
						+ "(SELECT tec_id FROM teacher WHERE tec_name = ?)))";
				params.add(teacher.getName());
				params.add(teacher.getName());
			} else if (roleId == 3) {
				Student student = new StudentImpl().query("ope_id", String.valueOf(operator.getId())).get(0);
				scopeLabel = student.getName() + "（学生）个人成绩";
				whereSql = " WHERE s.stu_id = ?";
				params.add(String.valueOf(student.getId()));
			}

			conn = DB.getConn();

			String overallSql = "SELECT IFNULL(ROUND(AVG(s.sco_count),2),0) avg_score, "
					+ "IFNULL(SUM(CASE WHEN s.sco_count >= 60 THEN 1 ELSE 0 END),0) pass_count, "
					+ "IFNULL(SUM(CASE WHEN s.sco_count < 60 THEN 1 ELSE 0 END),0) fail_count, "
					+ "COUNT(*) total_count FROM score s" + whereSql;
			pst = conn.prepareStatement(overallSql);
			fillParams(pst, params);
			rs = pst.executeQuery();
			if (rs.next()) {
				int total = rs.getInt("total_count");
				overall.put("avgScore", rs.getDouble("avg_score"));
				overall.put("passCount", rs.getInt("pass_count"));
				overall.put("failCount", rs.getInt("fail_count"));
				overall.put("totalCount", total);
				overall.put("passRate", total == 0 ? 0 : Math.round(rs.getInt("pass_count") * 10000.0 / total) / 100.0);
			}
			rs.close();
			pst.close();

			buckets.put("lt60", countByRange(conn, whereSql, params, "s.sco_count < 60"));
			buckets.put("s60_69", countByRange(conn, whereSql, params, "s.sco_count >= 60 AND s.sco_count < 70"));
			buckets.put("s70_79", countByRange(conn, whereSql, params, "s.sco_count >= 70 AND s.sco_count < 80"));
			buckets.put("s80_89", countByRange(conn, whereSql, params, "s.sco_count >= 80 AND s.sco_count < 90"));
			buckets.put("gte90", countByRange(conn, whereSql, params, "s.sco_count >= 90"));

			String subjectSql = "SELECT sub.sub_name subject_name, "
					+ "ROUND(AVG(s.sco_count),2) avg_score, "
					+ "SUM(CASE WHEN s.sco_count >= 60 THEN 1 ELSE 0 END) pass_count, "
					+ "SUM(CASE WHEN s.sco_count < 60 THEN 1 ELSE 0 END) fail_count, "
					+ "COUNT(*) total_count "
					+ "FROM score s JOIN subject sub ON s.sub_id = sub.sub_id "
					+ whereSql + " GROUP BY s.sub_id, sub.sub_name ORDER BY avg_score DESC";
			pst = conn.prepareStatement(subjectSql);
			fillParams(pst, params);
			rs = pst.executeQuery();
			while (rs.next()) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("subject", rs.getString("subject_name"));
				item.put("avgScore", rs.getDouble("avg_score"));
				item.put("passCount", rs.getInt("pass_count"));
				item.put("failCount", rs.getInt("fail_count"));
				item.put("totalCount", rs.getInt("total_count"));
				subjectStats.add(item);
			}

			result.put("scope", scopeLabel);
			result.put("overall", overall);
			result.put("buckets", buckets);
			result.put("subjects", subjectStats);
			response.getWriter().write(JSONSerializer.toJSON(result).toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"error\":\"统计失败\"}");
		} finally {
			DB.close(conn, pst, rs);
		}
	}

	private void fillParams(PreparedStatement pst, List<String> params) throws Exception {
		for (int i = 0; i < params.size(); i++) {
			pst.setString(i + 1, params.get(i));
		}
	}

	private int countByRange(Connection conn, String whereSql, List<String> params, String rangeSql) throws Exception {
		PreparedStatement countPst = null;
		ResultSet countRs = null;
		try {
			String sql = "SELECT COUNT(*) c FROM score s" + whereSql
					+ (whereSql.length() > 0 ? " AND " : " WHERE ") + rangeSql;
			countPst = conn.prepareStatement(sql);
			fillParams(countPst, params);
			countRs = countPst.executeQuery();
			return countRs.next() ? countRs.getInt("c") : 0;
		} finally {
			DB.close(null, countPst, countRs);
		}
	}
}
