# 基于 Spring Boot + MyBatis 的基础级数据增删改查模板

本项目是针对数据库操作的模板工程，基于 Spring Boot + MyBatis，提供基础级增删改查（CRUD）实现方案，支持**逐项、分批**操作，可处理离线单任务**千万级**数据量，便于初学者学习基础。

## 关联项目

由于此前在频繁集成数据，因而写了以下两套模板，前者可以说是后者的基础。

> 💬相关
>
> 基于 Spring Boot + MyBatis 的基础级数据增删改查模板（本项目）
>
> - 代码仓库：https://github.com/dreature1328/springboot-mybatis-crud-template
> 
>
>基于 Spring Boot + MyBatis 的轻量级数据集成模板
> 
>- 代码仓库：https://github.com/dreature1328/springboot-mybatis-integration-template

## 架构设计

**MVC 分层架构**：采用多泛型设计，即实体类型 `T` + 主键类型 `ID` + 成员类型（如服务类 `S` 或 映射器 `M`），结合继承实现

- **控制层**（`controller`）：继承基类，负责处理 HTTP 请求与响应，便于接口测试
- **服务层**（`service`）：继承基类，分离接口与实现，便于事务管理
- **持久层**（`mapper`）：继承基接口，基于原生 MyBatis 实现 SQL 映射（暂不考虑 MyBatis-Plus）

**基础设施**

- **数据库连接池**：Spring Boot 默认集成的 HikariCP
- **事务控制**：Spring 声明式事务（`@Transactional`）

## 数据映射

将实体类（`Data.java`）与数据表（`data_table.sql`）进行映射。

| 实体字段     | 表字段        | 类型映射              |
| ------------ | ------------- | --------------------- |
| id           | id            | Long ↔ BIGINT         |
| numericValue | numeric_value | Integer ↔ INT         |
| decimalValue | decimal_value | Double ↔ DOUBLE       |
| textContent  | text_content  | String ↔ VARCHAR(255) |
| activeFlag   | active_flag   | Boolean ↔ TINYINT(1)  |

## 执行方式

为适应不同数据量级和场景，部分方法有多种执行方式，通过重载形式或函数名进行区分。

映射器内部仅实现“单项”和“单批”（若有）原子化操作，而“逐项”与“分批”交由服务类在外部实现。

| 类型 | 命名逻辑                                                     | 实现逻辑                                                     |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 单项 | `execute(Object entity)`                                     | 直接处理单个对象                                             |
| 逐项 | `execute(Object... entities)`<br />`execute(List<Object> entities)` | 遍历数组（可变参数）或列表，对每个元素依次单项处理           |
| 单批 | `executeBatch(List<Object> entities)`                        | 将整个列表视作单个批次，通过批量优化机制，系统调用或资源开销 |
| 分批 | `executeBatch(List<Object> entities, int batchSize)`         | 将整个列表分割成多个子批次，对每批依次单批处理，规避内存溢出风险 |

对于数据库交互，批量优化策略主要是通过**动态拼接**长 SQL 合并操作（如 `INSERT INTO ... VALUES (...), (...), ...`），降低网络 I/O 开销。

## 启动流程

1. **数据源配置**：
   - 编辑 `application.properties` 文件，配置数据源参数
   - 执行 `data_table.sql` 脚本，初始化数据库表结构

2. **项目启动**：运行 `Application.java` 主类，启动 Spring Boot 应用
3. **接口调用**：查看 `/swagger-ui/index.html`（默认）接口文档，发起请求调用 HTTP 接口

## 相关脚本

脚本位于 `scripts/` 目录

- 数据表结构定义脚本：`data_table.sql` 
- 模拟数据生成脚本：`generate_mock_data.py`

> 注：旧版模板式代码段生成脚本已被移除
