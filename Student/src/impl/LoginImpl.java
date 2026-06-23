package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.DB;
import dao.ILogin;
import entity.Operator;
import entity.Privilege;

public class LoginImpl implements ILogin {
	private PrivilegeImpl1 privilegeImpl = new PrivilegeImpl1();
	private RoleImpl roleImpl = new RoleImpl();
	private List<Privilege> list_privilege;
	private PreparedStatement pst;
	private Operator log_operator;
	private HttpSession session;
	private String checkResult;
	private Connection conn;
	private ResultSet rs;

	// 登录验证
	public String login(HttpServletRequest request, Operator operator) {
		session = request.getSession();
		checkResult = "success";
		log_operator = new Operator();
		try {
			conn = DB.getConn();
			if (conn == null) {
				checkResult = "数据库连接失败，请检查数据库是否启动及配置是否正确！";
				session.setAttribute("isLogin", "false");
				return checkResult;
			}
			pst = conn
					.prepareStatement("SELECT * FROM operator WHERE ope_name = ?");
			pst.setString(1, operator.getName());
			rs = pst.executeQuery();
			if (!rs.next()) {
				checkResult = "账户不存在，请重新输入！";
				session.setAttribute("isLogin", "false");
			} else {
				// 先把 operator 表的数据读到本地变量，避免后续查询时结果集被关闭
				int ope_id = rs.getInt(1);
				String ope_name = rs.getString(2);
				String ope_pwd = rs.getString(3);
				String rol_id = rs.getString(4);

				if (!operator.getPwd().equals(ope_pwd)) {
					checkResult = "您输入的密码不正确，请重新输入！";
					session.setAttribute("isLogin", "false");
				} else {
					// 登录成功
					session.setAttribute("isLogin", "true");

					// 获得该用户的完整信息
					log_operator.setId(ope_id);
					log_operator.setName(ope_name);
					log_operator.setPwd(ope_pwd);
					log_operator.setRole(roleImpl.query("rol_id", rol_id).get(0));
					session.setAttribute("log_operator", log_operator);

					// 根据用户，获取对应的角色对应的权限
					list_privilege = privilegeImpl.query("rol_id", log_operator
							.getRole().getId()
							+ "");
					List<Privilege> list = new ArrayList<Privilege>();
					list.add(list_privilege.get(0));
					
					for (int i = 1; i < list_privilege.size(); i++) {
                          int y=0;
						for(int x=0;x<list.size();x++){
							
							if(!list.get(x).getMenu_name().equals(
									list_privilege.get(i).getMenu_name())){
								y++;
							}
						}
						if (y==list.size()) {
							list.add(list_privilege.get(i));

						}

					}
					session.setAttribute("list", list);
					session.setAttribute("list_privilege", list_privilege);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkResult = "登录失败：" + (e.getMessage() == null ? e.toString() : e.getMessage());
			session.setAttribute("isLogin", "false");
		} finally {
			DB.close(conn, pst, rs);
		}
		return checkResult;
	}
}