# UTMS 学生信息管理系统（JSP + Servlet + JavaBean + MySQL）

## 1. 项目简介
UTMS（University Teaching Management System）是一个面向高校教学管理场景的 Web 学生信息管理系统，核心覆盖：
1. 登录认证与会话控制
2. 学生信息管理
3. 教师信息管理
4. 专业与班级管理
5. 课程与成绩管理
6. 班级课程安排与导出
7. 基于角色的权限菜单控制

本项目采用经典 Java Web 架构：JSP（视图）+ Servlet（控制层）+ DAO/Entity（数据层）+ MySQL（存储）。

## 2. 技术栈与目录
- 后端：JDK 8、Servlet/JSP、JSTL
- 前端：HTML/CSS/JavaScript、jQuery 1.8.3
- 数据库：MySQL（脚本见 Student/student.sql）
- 容器：Tomcat 8/9（兼容 Servlet 2.5 配置）

关键目录：
- Student/src：Java 源码
- Student/WebRoot：JSP、CSS、JS、静态资源
- Student/WebRoot/WEB-INF/web.xml：Servlet 与 Filter 映射
- Student/student.sql：数据库结构与初始化数据

## 3. 功能模块
1. 认证与权限
- 登录与注销
- 编码过滤、登录过滤
- 角色权限菜单动态展示

2. 学生管理
- 新增、查询、编辑、删除
- 学生个人信息查看
- 同班同学查询

3. 教师管理
- 新增、查询、编辑、删除
- 教师个人信息查看

4. 专业与班级管理
- 专业新增、查询、编辑、删除
- 班级新增、查询、编辑、删除
- 班级课程关联管理

5. 课程与成绩管理
- 课程新增、查询、编辑、删除
- 成绩查询、编辑更新
- 班级成绩导出

## 4. 本次提交的改进点
1. 界面视觉升级
- 登录页：渐变背景 + 卡片化表单 + 焦点态与按钮交互优化
- 顶栏：品牌化渐变、用户菜单胶囊风格
- 侧栏：层级更清晰，悬停高亮更明显
- 欢迎页：卡片化排版，信息结构更清晰

2. 新增教学统计功能（任务7）
- 新增 `ScoreStatsServlet` 成绩统计接口
- 新增 `score_stats.jsp` 统计页面
- 支持教师/管理员查看：平均分、及格/不及格人数、及格率、分段分布图、分科目统计表

3. UML 建模材料（任务2~6）
- 用例图：`docs/usecase.png`
- 面向对象分析类图：`docs/analysis_class.png`
- 学生查询成绩顺序图：`docs/sequence.png`
- 数据库 E-R 图：`docs/er.png`
- MVC 设计类图：`docs/mvc_class.png`
- dao 包分析与类图关系说明：`docs/task6_analysis.md`

4. 工程文档补齐
- 新增 WBS.md：任务拆分、四人分工、计划/实际耗时
- 完善本 README：部署、运行、验收说明

## 5. 环境准备
1. 安装 JDK 8 并配置 JAVA_HOME
2. 安装 MySQL 5.7/8.0（建议字符集 UTF-8）
3. 安装 Tomcat 8/9
4. 使用 IDEA/Eclipse 导入 Student 为 Dynamic Web Project

## 6. 数据库初始化
1. 创建数据库 student（UTF8）
2. 执行 Student/student.sql
3. 校验表是否创建成功（major、classes、student、subject、score、operator、privilege 等）

## 7. 运行步骤
1. 将 Student 部署到 Tomcat
2. 启动 Tomcat
3. 访问：http://localhost:8080/Student/
4. 使用测试账号登录（如 admin/admin）

## 8. 验收建议
1. 管理员账号验证全量菜单可见
2. 教师账号验证受限菜单可见、可查询成绩和班级课程
3. 学生账号验证个人信息与成绩查询
4. 检查新增、编辑、删除、查询链路是否可用
5. 检查页面样式在主流浏览器下显示正常

## 9. 风险与后续优化
1. 目前使用 frameset，后续可升级为现代布局模板
2. 前端 JS 仍以 jQuery 为主，后续可逐步模块化
3. 可补充统一异常页面与操作日志
4. 可引入分页组件与更细粒度权限控制

## 10. 贡献成员
- 田星宇
- 宁静
- 苟敏
- 侯亚琴

详见 WBS.md。
