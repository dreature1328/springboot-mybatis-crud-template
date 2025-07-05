# 基于 Spring Boot + MyBatis 的基础级数据增删改查模板

本项目是针对数据库操作的模板工程，基于 Spring Boot + MyBatis，提供基础级增删改查（CRUD）实现方案，支持**逐项、分批**操作，可处理单任务**千万级**数据量，便于初学者理解流程。

## 关联项目

由于此前在频繁集成数据，因而写了以下两套模板，前者可以说是后者的基础。

> 💬相关
>
> 基于 Spring Boot + MyBatis 的基础级数据增删改查模板（本项目）
>
> - 技术博客：https://blog.csdn.net/weixin_42077074/article/details/128868655
> - GitHub 源码：https://github.com/dreature1328/springboot-mybatis-crud-template
>
>
> 基于 Spring Boot + MyBatis 的轻量级数据集成模板
>
> - 技术博客：https://blog.csdn.net/weixin_42077074/article/details/129802650
> - GitHub 源码：https://github.com/dreature1328/springboot-mybatis-integration-template

## 架构设计

项目采用 MVC 分层架构。

持久层基于原生 MyBatis 实现（未采用 MyBatis-Plus），便于理解逻辑和灵活处理 SQL。

> 💬相关
>
> 基于 MyBatis 逐项、分批增删改查
>
> - 技术博客：https://blog.csdn.net/weixin_42077074/article/details/129405833

## 数据映射

将实体类（`Data.java`）与数据表（`data_table.sql`）进行映射。

| 实体属性       | 表字段          | 类型映射           |
|----------------|-----------------|--------------------------|
| id             | id              | Long ↔ BIGINT            |
| numericValue   | numeric_value   | Integer ↔ INT            |
| decimalValue   | decimal_value   | Double ↔ DOUBLE          |
| textContent    | text_content    | String ↔ VARCHAR(255)    |
| activeFlag     | active_flag     | Boolean ↔ TINYINT(1)     |

## 启动流程

1. **数据库初始化**：执行 `data_table.sql` 脚本，创建数据表
2. **应用配置**：编辑 `application.properties` 文件，配置数据源参数
3. **项目启动**：运行 `Application.java` 主类，启动 Spring Boot 应用
4. **接口测试**：发起请求调用 Controller 层接口，执行增删改查操作

## 相关脚本

脚本位于 `script/` 目录

- 数据表结构定义脚本：`data_table.sql` 
- 随机数据生成脚本：`generate_mock_data.py`

> 注：旧版模板式代码段生成脚本已被移除
