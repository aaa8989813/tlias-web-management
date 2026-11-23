# Tlias 教学管理系统

基于 Java 21、Spring Boot 3 和 MyBatis 的教学管理系统后端，采用单体架构，覆盖部门、员工、班级、学员管理及数据统计。

本项目是学习实践项目，基于黑马程序员 Tlias 教学内容完成，并进行了功能联调和问题修复。保留原有 `com.itheima` 包名；本仓库用于展示学习与复盘成果。前端使用配套 Vue 项目，本仓库仅包含后端源码，不包含前端资源及真实业务数据。

## 功能与实现

| 模块 | 主要实现 |
| --- | --- |
| 部门管理 | 部门增删改查；通过自定义注解和 AOP 记录操作人、参数、返回值及耗时 |
| 员工管理 | 条件分页、批量删除、工作经历的一对多映射；使用事务维护关联数据 |
| 班级与学员 | 基础增删改查、条件分页；删除班级前校验关联学员 |
| 登录校验 | JWT 签发与 Filter 校验；ThreadLocal 传递用户身份并在请求结束后清理 |
| 统计 | 员工职位、性别分布；班级学员人数及学员学历分布 |
| 文件上传 | 阿里云 OSS，UUID 文件名、按年月组织路径、环境变量读取访问凭据 |

技术栈：Java 21、Spring Boot 3.5.8、MyBatis、MySQL、PageHelper、JWT、AOP、阿里云 OSS、Maven、Lombok。

## 代码结构

```text
src/main/java/com/itheima/
  controller/       HTTP 接口
  service/impl/     业务逻辑与事务
  mapper/           MyBatis Mapper
  pojo/             实体及返回对象
  filter/           JWT 登录校验
  aop/              操作日志
  utils/            JWT、用户上下文、OSS
src/main/resources/com/itheima/mapper/  动态 SQL 与关联映射
src/test/java/                         自动化测试
sql/tlias.sql                         项目原始表结构（无数据，已移除删表语句）
```

## 本地运行

环境：JDK 21、Maven 3.9.x、MySQL 8.x。仓库也包含 Maven Wrapper；Windows 可将下列 `mvn` 替换为 `./mvnw.cmd`，Linux/macOS 使用 `./mvnw`。

### 1. 创建本地数据库

在 MySQL 客户端中执行：

```sql
CREATE DATABASE tlias CHARACTER SET utf8mb4;
USE tlias;
SOURCE /absolute/path/to/tlias-web-management/sql/tlias.sql;
```

`tlias.sql` 来自项目数据库导出的实际表结构，包含部门、员工、工作经历、班级、学员及两类日志，共 7 张表，不包含业务数据。发布时移除了导出连接信息和 `DROP TABLE` 语句，保留原始字段、约束、索引及自增值；使用 `utf8mb4_0900_ai_ci` 排序规则，需要 MySQL 8.0 或以上版本。

此文件替代此前根据代码整理的 `schema.sql`，两者不要重复导入。仅在新的空数据库中执行；已有表会报错，不会自动覆盖，也不作为已有数据库的迁移脚本。初始化后没有登录账号或统计数据，需要自行创建演示数据。可在本地 SQL 客户端创建首个账号，将下例的占位密码替换为自己的本地演示密码（手机号为虚构示例）：

```sql
INSERT INTO emp (username, password, name, gender, phone, create_time, update_time)
VALUES ('demo', '<your-local-demo-password>', '演示用户', 1, '00000000000', NOW(), NOW());
```

当前登录逻辑仍按教学实现直接比较密码。原始教学表结构中，员工密码字段的默认值为 `123456`；未显式设置密码的新员工会使用该值，但导入脚本本身不会创建任何账号。请自行设置演示密码，仅使用本地演示数据。密码哈希、角色权限等尚待补充，不应直接部署为公网业务系统。

### 2. 配置环境变量

在启动应用的同一个 PowerShell 终端中设置：

```powershell
$env:DB_URL = 'jdbc:mysql://localhost:3306/tlias?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai'
$env:DB_USERNAME = '<your-db-user>'
$env:DB_PASSWORD = '<your-db-password>'

# 生成随机的 JWT 签名密钥，不输出密钥内容。
$jwtBytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($jwtBytes)
$rng.Dispose()
$env:JWT_SECRET = [Convert]::ToBase64String($jwtBytes)
```

`JWT_SECRET` 必须为至少 32 字节随机密钥的 Base64 表示。换用新的密钥会使旧令牌失效；调试期间如需保留登录状态，请在本机安全保存并复用密钥。IDE 启动时，在运行配置的环境变量中设置相同变量。项目不会自动加载 `.env` 文件。

如需测试文件上传，另行配置自己的 OSS 资源：

```powershell
$env:OSS_ENDPOINT = 'https://oss-cn-beijing.aliyuncs.com'
$env:OSS_REGION = 'cn-beijing'
$env:OSS_BUCKET_NAME = '<your-bucket>'
$env:OSS_ACCESS_KEY_ID = '<your-access-key-id>'
$env:OSS_ACCESS_KEY_SECRET = '<your-access-key-secret>'
```

不配置 OSS 凭据也可运行其他模块。上传返回文件 URL，文件访问权限需要与自己的 Bucket 策略匹配。

### 3. 构建与启动

```powershell
mvn test
mvn package -DskipTests
java -jar target/tlias-web-management-0.0.1-SNAPSHOT.jar
```

默认监听 `8080` 端口，可通过 `SERVER_PORT` 修改。日志写入当前工作目录下的 `logs/`。

数据库上下文测试默认跳过。配置好测试库后，可设置 `$env:RUN_DB_TESTS = 'true'` 再执行 `mvn test`。JWT 和 Filter 单元测试不需要数据库，但需要上述 `JWT_SECRET`。

### 4. 调用接口

```http
POST /login
Content-Type: application/json

{"username":"demo","password":"<your-local-demo-password>"}
```

登录成功后，读取响应中的 `data.token`，后续请求通过 `token` 请求头传入。配套前端可将 `/api` 代理到 `http://localhost:8080` 并去掉 `/api` 前缀。

| 接口 | 用途 |
| --- | --- |
| `POST /login` | 登录 |
| `GET /depts` | 部门列表 |
| `GET /emps?page=1&pageSize=10` | 员工分页 |
| `GET /clazzs?page=1&pageSize=10` | 班级分页 |
| `GET /students?page=1&pageSize=10` | 学员分页 |
| `GET /report/empJobData` | 员工职位统计 |
| `GET /report/empGenderData` | 员工性别统计 |
| `GET /report/studentCountData` | 班级人数统计 |
| `GET /report/studentDegreeData` | 学员学历统计 |
| `POST /upload` | 上传文件，multipart 字段为 `file` |

## 复盘与发布整理

- 修正动态 SQL 判断中的 Java 属性名，区分数据库下划线字段与 Java 驼峰属性。
- 从已验证 JWT 的 `id` 声明获取用户身份，并通过 `finally` 清理请求线程的上下文。
- 避免员工密码出现在 JSON 返回值和 Lombok 生成的 `toString()` 中。
- 为 Lombok 注解处理器补充版本，修复构建时找不到生成方法的问题。
- 发布副本移除本机密码、云凭据、旧示例令牌和绝对日志路径，JWT 签名密钥改由环境变量提供。
- 增加 JWT 和 Filter 回归测试；整理运行说明，并使用项目实际 SQL 表结构作为数据库初始化脚本。

现有代码仍保留部分教学示例。后续可继续完善参数校验、密码哈希、权限控制、违纪次数与扣分规则以及数据库集成测试。当前仓库不声称已具备生产级安全或并发能力。
