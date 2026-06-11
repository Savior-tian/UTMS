<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>welcome page</title>
		<style type="text/css">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}
body {
	padding: 32px 24px;
	background:
		radial-gradient(circle at 0% 0%, rgba(44, 151, 222, 0.15), transparent 35%),
		radial-gradient(circle at 100% 100%, rgba(25, 165, 140, 0.12), transparent 36%),
		linear-gradient(180deg, #f6f9fd 0%, #edf3f9 100%);
	font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
}
.container {
	max-width: 980px;
	margin: 0 auto;
	background: #ffffff;
	border: 1px solid #dfeaf3;
	border-radius: 16px;
	box-shadow: 0 16px 40px rgba(27, 70, 105, 0.12);
	padding: 28px 30px 32px;
}
.title {
	font-size: 28px;
	line-height: 1.4;
	color: #17466e;
	margin-bottom: 12px;
}
.subtitle {
	font-size: 20px;
	color: #2c6f97;
	margin: 12px 0 20px;
}
.divider {
	height: 2px;
	border: 0;
	background: linear-gradient(90deg, #2e82bd 0%, #3ab8a0 100%);
	margin-bottom: 24px;
}
.intro {
	line-height: 1.95;
	font-size: 16px;
	color: #304c63;
}
.intro p {
	margin-bottom: 8px;
}
</style>
	</head>
	<body>
		<div class="container">
			<h2 class="title">欢迎进入学生信息管理系统</h2>
			<hr class="divider"/>
			<h3 class="subtitle">系统简介</h3>
			<div class="intro">
				<p>本项目采用 JSP + Servlet + JavaBean + MySQL 技术栈，构建面向高校场景的 Web 学生信息管理系统。</p>
				<p>系统围绕学生全周期管理，覆盖基础档案、班级专业、课程安排与学习成绩等核心业务数据。</p>
				<p>在教学组织方面，可结合专业培养方案管理课程与授课关系，支持教师教学活动和学生学习过程的信息化记录。</p>
				<p>在业务管理方面，系统通过操作员角色与权限控制，保障不同人员在统一平台内按职责高效协同，提升管理质量与运行稳定性。</p>
			</div>
		</div>
	</body>
</html>
