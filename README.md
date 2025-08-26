# cheapGpt# CheapGPT 企业账号池系统

## 项目介绍

这是一个使用 Java Spring Boot 实现的企业级 ChatGPT 账号池管理系统。该系统将多个 ChatGPT 企业版账号组成账号池，并可拆分成多个子账号供不同用户使用。

## 核心功能

### 1. 账号池管理
- **企业账号管理**：管理多个 ChatGPT 企业版主账号
- **子账号拆分**：每个企业账号可拆分成多个子账号
- **轮询分配**：系统自动轮询选择可用的子账号

### 2. 用户管理
- **管理员账号**：admin/admin123（默认）
- **用户认证**：基于 Spring Security 的登录系统
- **权限控制**：管理员和普通用户权限分离

### 3. 聊天功能
- **子账号选择**：用户聊天时自动分配可用子账号
- **实时聊天**：简洁的聊天界面
- **占位实现**：当前为模拟实现，可扩展对接真实 OpenAI API

## 技术栈

- **后端框架**：Spring Boot 3.5.5
- **数据库**：H2 内存数据库（可切换为 PostgreSQL/MySQL）
- **安全框架**：Spring Security
- **模板引擎**：Thymeleaf
- **构建工具**：Maven
- **JDK 版本**：17+

## 系统架构

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   企业账号1      │    │   企业账号2      │    │   企业账号N      │
│  ORG-001        │    │  ORG-002        │    │  ORG-XXX        │
└─────────────────┘    └─────────────────┘    └─────────────────┘
        │                      │                      │
        ▼                      ▼                      ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   子账号池       │    │   子账号池       │    │   子账号池       │
│ sub-1, sub-2... │    │ sub-3, sub-4... │    │ sub-N, sub-M... │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │
                              ▼
                    ┌─────────────────┐
                    │   轮询调度器     │
                    │  Round Robin    │
                    └─────────────────┘
                              │
                              ▼
                    ┌─────────────────┐
                    │   用户聊天      │
                    │  Chat Interface │
                    └─────────────────┘
```

## 数据模型

### 企业账号 (EnterpriseAccount)
- ID、名称、组织ID、凭证、启用状态、备注

### 子账号 (SubAccount)
- ID、关联企业账号、密钥、凭证、启用状态、并发限制

### 用户 (AppUser)
- ID、用户名、密码哈希、管理员标志、分配的子账号

## 快速开始

### 1. 启动应用
```bash
# 构建项目
./mvnw clean package -DskipTests

# 启动应用
java -jar target/cheappool-0.0.1-SNAPSHOT.jar
```

### 2. 访问系统
- **主页**：http://localhost:8080
- **登录**：admin / admin123
- **H2 控制台**：http://localhost:8080/h2

### 3. 功能体验
1. 登录系统后查看子账号列表
2. 点击"开始聊天"体验聊天功能
3. 系统会自动分配可用的子账号

## 扩展建议

### 1. 对接真实 OpenAI API
在 `ChatService` 中替换占位实现：
```java
// 使用真实的 OpenAI API 调用
// 使用 subAccount.getCredential() 作为 API Key
// 使用 subAccount.getEnterpriseAccount().getOrganizationId() 作为组织 ID
```

### 2. 数据库切换
修改 `application.yml` 中的数据源配置：
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cheapgpt
    username: your_username
    password: your_password
```

### 3. 高级功能
- 账号健康检查
- 并发限制控制
- 使用统计和监控
- 用户配额管理
- API 密钥轮换

## 项目结构

```
src/main/java/com/cheappool/
├── config/           # 配置类
│   ├── SecurityConfig.java
│   └── DataInitializer.java
├── domain/           # 实体类
│   ├── EnterpriseAccount.java
│   ├── SubAccount.java
│   └── AppUser.java
├── repository/       # 数据访问层
├── service/          # 业务逻辑层
│   ├── AccountPoolService.java
│   ├── ChatService.java
│   └── CustomUserDetailsService.java
└── web/             # 控制器层
    ├── AuthController.java
    ├── DashboardController.java
    └── ChatController.java

src/main/resources/
├── templates/        # Thymeleaf 模板
│   ├── login.html
│   ├── dashboard.html
│   └── chat.html
├── static/          # 静态资源
│   ├── css/app.css
│   └── js/app.js
└── application.yml  # 应用配置
```

## 许可证

本项目使用 Mozilla Public License 2.0 许可证。
