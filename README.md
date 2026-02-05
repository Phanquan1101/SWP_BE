# Crowdsourced Waste Collection & Recycling Platform (Monolith, Single‑Enterprise)

> Enterprise tables (`enterprises`, `enterprise_waste_capabilities`) vẫn tồn tại để giữ tương thích Flyway nhưng **không được dùng** trong chế độ single‑enterprise hiện tại.

## 1) Giới thiệu dự án
- **Mục tiêu**: Nền tảng crowdsourcing giúp người dân báo cáo điểm rác, đơn vị vận hành điều phối thu gom, ghi nhận thưởng điểm và minh bạch trạng thái xử lý.
- **Actors**:  
  - **Citizen**: tạo báo cáo rác, theo dõi trạng thái.  
  - **Enterprise Manager**: (single enterprise) duyệt/assign collector theo khu vực & khả năng tiếp nhận.  
  - **Collector**: nhận việc, cập nhật trạng thái thu gom.  
  - **Admin**: quản trị người dùng, master data (khu vực, loại rác, khả năng tiếp nhận).
- **Mô hình single‑enterprise**: chỉ một đơn vị vận hành; điều phối dựa trên **Area** và **Waste Capability** theo loại rác.

## 2) Kiến trúc tổng quan
- Monolithic Spring Boot. Tầng: **Controller → Service → Repository**, DTO + Mapper (MapStruct), Exception tập trung.
- Packages chính (`src/main/java/com/crowdsourced/wasteplatform`):
  - `config`: Security, JWT, OpenAPI.
  - `controller`: REST endpoints (auth, health, area, waste category, waste capability, …).
  - `service`: nghiệp vụ; mỗi domain một service.
  - `repository`: Spring Data JPA access DB.
  - `entity`: JPA entities.
  - `dto`: request/response; không trả Entity ra ngoài.
  - `mapper`: MapStruct map Entity ↔ DTO.
  - `exception`: ApiResponse, ErrorCode, GlobalExceptionHandler.

## 3)` Công nghệ
- Spring Boot 3, Spring Security 6.
- JWT access + refresh, BCrypt password.
- MySQL + Flyway migrations.
- Swagger / OpenAPI (springdoc).
- Validation (Jakarta), MapStruct, Lombok.
- Tests: JUnit 5 + MockMvc.`

## 4) Setup môi trường
### Yêu cầu
- Java 21+, Maven 3.9+, MySQL 8.x.

### Tạo database MySQL
```sql
CREATE DATABASE waste_platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'root'@'%' IDENTIFIED BY '12345';
GRANT ALL ON waste_platform.* TO 'root'@'%';
```

### Cấu hình `src/main/resources/application.yml`
```yaml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/waste_platform?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh
    username: root
    password: "12345"
  jpa:
    hibernate:
      ddl-auto: validate
  flyway:
    enabled: true
app:
  jwt:
    secret: <chuỗi bí mật 64+ ký tự>
    access-token-exp-minutes: 30
    refresh-token-exp-days: 14
```

### Flyway
- Migrations nằm ở `src/main/resources/db/migration` (V1…V6). Chạy tự động khi khởi động ứng dụng.
- Lịch sử ghi ở bảng `flyway_schema_history`.

### Chạy dự án
```bash
mvn clean test          # chạy toàn bộ test
mvn spring-boot:run     # chạy ứng dụng
```
Hoặc chạy trực tiếp từ IntelliJ (Main class: `com.crowdsourced.wasteplatform.Single391SwpApplication`).

## 5) Tài khoản & roles mặc định (seed bởi Flyway V2)
| Email                   | Password      | Role                  | Ghi chú                 |
|-------------------------|---------------|-----------------------|-------------------------|
| admin@example.com       | Password@123  | ROLE_ADMIN            | Quản trị                |
| citizen@example.com     | Password@123  | ROLE_CITIZEN          | Dân cư                  |
| enterprise@example.com  | Password@123  | ROLE_ENTERPRISE_MANAGER | Legacy/seed, dùng cho luồng enterprise (planned) |
| collector@example.com   | Password@123  | ROLE_COLLECTOR        | Thu gom                 |

- **Citizen** có thể tự đăng ký qua `/auth/register`.
- Enterprise Manager / Collector thực tế sẽ được Admin gán role (seed chỉ để demo).

## 6) RBAC & prefix endpoint
- ADMIN: quản lý user/role, master data (Area, Waste Category, Waste Capability).
- CITIZEN: báo cáo rác của mình.
- ENTERPRISE_MANAGER: duyệt/assign (luồng enterprise planned).
- COLLECTOR: cập nhật trạng thái thu gom (planned).
- Prefix:
  - `/auth/**` public (register/login/refresh/logout).
  - `/admin/**` yêu cầu ROLE_ADMIN.
  - `/citizen/**` yêu cầu ROLE_CITIZEN.
  - `/collector/**` yêu cầu ROLE_COLLECTOR.
  - `/enterprise/**` planned.
- JWT: Access token gửi qua `Authorization: Bearer <token>`. Refresh token dùng cho `/auth/refresh`, bị revoke khi `/auth/logout`.

## 7) Luồng nghiệp vụ chi tiết
### 7.1 Auth
- **Register citizen**: `POST /auth/register`  
  ```json
  { "email": "new@citizen.com", "password": "Password@123", "fullName": "New Citizen", "areaId": "<optional area UUID>" }
  ```
  → Trả tokens + profile (ROLE_CITIZEN). Validate unique email/phone, areaId nếu có phải tồn tại.
- **Login**: `POST /auth/login` {identifier (email/phone), password} → tokens.
- **Call protected**: `GET /health/protected` kèm `Authorization: Bearer <access>`.
- **Refresh**: `POST /auth/refresh` {refreshToken} → access mới.
- **Logout**: `POST /auth/logout` {refreshToken} → refresh bị revoke, dùng lại sẽ 401.

### 7.2 Master Data (Admin)
- **Area**  
  - Tạo: `POST /admin/areas` `{name, parentId?}`  
  - Cập nhật: `PUT /admin/areas/{id}`  
  - Ngừng kích hoạt: `PATCH /admin/areas/{id}/deactivate`  
  - Xem danh sách: `GET /areas` (trả danh sách active).
- **Waste Category**  
  - Tạo: `POST /admin/waste-categories` `{code, name}` (code unique).  
  - Cập nhật: `PUT /admin/waste-categories/{id}` `{name}`  
  - Deactivate: `PATCH /admin/waste-categories/{id}/deactivate`  
  - Danh sách: `GET /waste-categories?includeInactive=false`.
- **Waste Capability (global theo category)**  
  - Upsert: `PUT /admin/waste-capabilities/{wasteCategoryId}` `{dailyCapacityKg, accepting?}`  
  - Toggle accepting: `PATCH /admin/waste-capabilities/{wasteCategoryId}/toggle`  
  - Danh sách: `GET /admin/waste-capabilities`  
  - Rule: unique per waste_category_id, `daily_capacity_kg >= 0`.

### 7.3 Waste Report (Citizen) – *planned*  
- Citizen tạo báo cáo: `POST /citizen/reports` với areaId, wasteCategoryId, estimatedWeightKg, lat/lng, mediaUrls; status PENDING; ghi status history.  
- Xem danh sách: `GET /citizen/reports`; Chi tiết + history: `GET /citizen/reports/{id}`.  

### 7.4 Xử lý báo cáo (Enterprise Manager) – *planned*  
- Inbox theo area/capability: `GET /enterprise/reports/inbox`.  
- Accept: `POST /enterprise/reports/{id}/accept` (PENDING → ACCEPTED, kiểm tra capability.is_accepting, ghi history).  
- Reject: `POST /enterprise/reports/{id}/reject` + reason (PENDING → REJECTED, history).  

### 7.5 Phân công & Thu gom (Collector) – *planned*  
- Assign collector: `POST /enterprise/reports/{id}/assign {collectorId}` (collector phải ROLE_COLLECTOR và cùng area). Status ACCEPTED → ASSIGNED.  
- Collector xem việc: `GET /collector/assignments`.  
- Collector cập nhật trạng thái: `PATCH /collector/assignments/{id}/status` (ASSIGNED → ON_THE_WAY → COLLECTED, upload proof). Report status đồng bộ và ghi history.  

### 7.6 Reward/Points – *planned*  
- Khi report COLLECTED/COMPLETED sẽ tạo point_transactions EARN theo rule capability.  

### 7.7 Complaints/Notifications – *planned*  
- Complaints của user, admin xử lý; thông báo in-app các sự kiện quan trọng.  

## 8) Hướng dẫn test nhanh bằng Swagger
- Mở: `http://localhost:8080/swagger-ui/index.html`
- Bấm **Authorize**, dán `Bearer <access_token>`.
- Checklist 10 phút:
  1. `POST /auth/register` tạo citizen mới.
  2. Login admin (`admin@example.com` / `Password@123`), tạo Area, Waste Category, Waste Capability.
  3. Login citizen mới, (planned) tạo report.
  4. (planned) Login enterprise manager, accept & assign.
  5. (planned) Login collector, cập nhật trạng thái.

## 9) Coding conventions & notes
- Không trả Entity ra ngoài; luôn dùng DTO + `ApiResponse<T>`.
- Trạng thái báo cáo phải đổi qua service chuyên trách (ReportStatusService – planned) và luôn ghi `report_status_history`.
- Collector phải có `area_id` khi nhận job (rule sẽ enforce trong luồng assignment planned).
- Validation bằng Jakarta; lỗi trả về theo `ApiResponse` trong `GlobalExceptionHandler`.

## 10) Troubleshooting
- **Flyway checksum**: Không sửa migration cũ (V1–V4); nếu cần thay đổi, tạo migration mới (V5+).  
- **401/403**: Kiểm tra header `Authorization: Bearer <access>` và role mapping `/admin/**`, `/citizen/**`, `/collector/**`.  
- **DB connection**: kiểm tra `spring.datasource.url`, quyền user MySQL, timezone `serverTimezone`.

---
Đã cập nhật README.md với đầy đủ hướng dẫn triển khai & nghiệp vụ hiện có/planned.  
