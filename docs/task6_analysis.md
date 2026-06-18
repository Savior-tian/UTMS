# 任务6 补充分析

## 1. dao 包中各 Java 文件的用途

| 文件 | 类型 | 用途说明 |
|---|---|---|
| `ILogin.java` | 接口 | 定义登录验证相关操作，如根据用户名密码查询操作员。 |
| `IOperator.java` | 接口 | 定义操作员（用户账号）的增删改查，对应 `operator` 表。 |
| `IRole.java` | 接口 | 定义角色查询操作，对应 `role` 表。 |
| `IPrivilege.java` | 接口 | 定义权限查询操作，对应 `privilege` 表。 |
| `IStudent.java` | 接口 | 定义学生信息的增删改查，对应 `student` 表。 |
| `ITeacher.java` | 接口 | 定义教师信息的增删改查，对应 `teacher` 表。 |
| `IClasses.java` | 接口 | 定义班级信息的增删改查，对应 `classes` 表。 |
| `IMajor.java` | 接口 | 定义专业信息的增删改查，对应 `major` 表。 |
| `ISubject.java` | 接口 | 定义课程信息的增删改查，对应 `subject` 表。 |
| `IScore.java` | 接口 | 定义成绩的查询、更新、删除等操作，对应 `score` 表。 |
| `ICla2Sub.java` | 接口 | 定义班级课程安排（排课）的增删改查，对应 `cla2sub` 表。 |
| `IPicture.java` | 接口 | 定义头像上传、校验、保存等操作。 |

**设计意义**：dao 包采用接口编程，符合 MVC 中模型层（M）的持久化抽象。业务 Servlet 通过接口调用，实现类位于 `impl` 包，便于后续更换数据库或 ORM 框架时低耦合替换。

## 2. 任务3（分析类图）与任务6（MVC 类图）的关系

| 对比维度 | 任务3：面向对象分析类图 | 任务6：MVC 设计类图 |
|---|---|---|
| **阶段** | 面向对象分析（OOA），关注问题域 | 面向对象设计（OOD），关注软件架构 |
| **关注点** | 实体、属性、关联、业务规则 | 分层结构、职责划分、交互关系 |
| **颗粒度** | 粗粒度，聚焦业务对象 | 细粒度，聚焦代码结构（JSP/Servlet/JavaBean） |
| **主要元素** | Student、Teacher、Score、Classes 等实体类 | View（JSP/CSS/JS）、Controller（Servlet/Filter）、Model（Entity+DAO+Impl） |
| **关系** | 任务3中的实体类直接演化为任务6中 Model 层的 `entity` 包；任务6在其基础上增加了 DAO 接口/实现、Servlet 控制器、JSP 视图，完成从分析到设计的落地 | |

**总结**：任务3是“做什么”的抽象描述，任务6是“怎么做”的具体架构。任务6中的实体类几乎全部继承自任务3的分析结果，同时按照 MVC 模式补充了控制层与视图层，使系统具备可运行性。
