<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成绩统计分析</title>
<link href="../css/score_stats.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="../js/score_stats.js" type="text/javascript"></script>
</head>
<body>
	<div class="page">
		<div class="head">
			<h2>成绩统计分析</h2>
			<p id="scope">数据范围：加载中...</p>
		</div>
		<div class="cards">
			<div class="card">
				<div class="label">平均分</div>
				<div id="avgScore" class="value">-</div>
			</div>
			<div class="card">
				<div class="label">及格人数</div>
				<div id="passCount" class="value">-</div>
			</div>
			<div class="card">
				<div class="label">不及格人数</div>
				<div id="failCount" class="value">-</div>
			</div>
			<div class="card">
				<div class="label">及格率</div>
				<div id="passRate" class="value">-</div>
			</div>
		</div>

		<div class="chart-box">
			<h3>成绩分布图</h3>
			<canvas id="scoreChart" width="900" height="320"></canvas>
		</div>

		<div class="table-box">
			<h3>分科目统计</h3>
			<table id="subjectTable" border="1" cellspacing="0" cellpadding="5" bordercolor="#d0dae6">
				<tr>
					<th>科目</th>
					<th>平均分</th>
					<th>及格人数</th>
					<th>不及格人数</th>
					<th>总人数</th>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
