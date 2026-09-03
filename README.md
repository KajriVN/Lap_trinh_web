# BT1 & BT2 - Lập Trình Web

## Bài tập 01 (26/08/2026)
Bài tập: **Login với Cookie**, **Login với Session**, **CRUD Category** (Servlet + JDBC + JSP, mô hình 3 tầng MVC).

## Bài tập 02 (26/08/2026) - ĐÃ HOÀN THÀNH

### Các chức năng đã hoàn thành

#### 1. Đăng ký tài khoản với xác thực OTP qua email
- Người dùng đăng ký tài khoản mới
- Hệ thống gửi mã OTP (6 số) qua email
- Người dùng nhập OTP để kích hoạt tài khoản
- OTP có hiệu lực 5 phút

#### 2. Đăng nhập
- Đăng nhập bằng username và password
- Chỉ cho phép đăng nhập nếu tài khoản đã được kích hoạt
- Có chức năng "Ghi nhớ đăng nhập"
- Phân quyền: Admin (roleid=1), Manager (roleid=2), User (roleid=5)

#### 3. Quên mật khẩu với OTP
- Người dùng nhập email đã đăng ký
- Hệ thống gửi mã OTP qua email
- Người dùng nhập OTP và mật khẩu mới
- Đặt lại mật khẩu thành công

#### 4. Quản lý sản phẩm (CRUD)
- **Create**: Thêm sản phẩm mới với upload hình ảnh (Multipart)
- **Read**: Xem danh sách sản phẩm
- **Update**: Sửa thông tin sản phẩm
- **Delete**: Xóa sản phẩm
- Mối quan hệ 1-n giữa Category và Product

#### 5. Hiển thị sản phẩm
- **Trang chủ (/home)**: Hiển thị 10 sản phẩm mới nhất
- **Trang sản phẩm (/product)**: Hiển thị tất cả sản phẩm với phân trang 6 sản phẩm/trang
- **Chi tiết sản phẩm (/product/detail?id=...)**: Hiển thị thông tin chi tiết khi click vào sản phẩm

### Cấu trúc Database BT2

#### Bảng `users` (đã cập nhật)
```sql
CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NULL,
    username    VARCHAR(100) NOT NULL UNIQUE,
    fullname    VARCHAR(255) NULL,
    password    VARCHAR(255) NOT NULL,
    avatar      VARCHAR(255) NULL,
    roleid      INT NOT NULL DEFAULT 5,
    phone       VARCHAR(20)  NULL,
    createddate DATE NULL,
    is_active   TINYINT(1) DEFAULT 0,
    otp_code    VARCHAR(6) NULL,
    otp_expired DATETIME NULL
);
```

#### Bảng `products` (mới)
```sql
CREATE TABLE products (
    product_id   INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description  TEXT NULL,
    price        DECIMAL(10,2) NOT NULL,
    image        VARCHAR(255) NULL,
    cate_id      INT NOT NULL,
    status       INT DEFAULT 1,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cate_id) REFERENCES category(cate_id)
);
```

## 1. Yêu cầu môi trường
- JDK 17
- Maven 3.6+
- MySQL 8 hoặc SQL Server
- Gmail account (để gửi OTP)

## 2. Cài Maven (Windows)
1. Tải Maven binary zip: https://maven.apache.org/download.cgi
2. Giải nén ra `C:\maven`
3. Thêm `C:\maven\bin` vào biến môi trường `Path`
4. Kiểm tra: mở terminal mới gõ `mvn -version`

## 3. Tạo database
Chạy script (đổi user/pass MySQL nếu cần):
```
mysql -u root -p < database.sql
```
Script tạo DB `servletcrudmvc`, bảng `category`, `users`, `products` và dữ liệu mẫu.

## 4. Cấu hình

### 4.1 Cấu hình kết nối database
Mở `src/main/java/vn/iotstar/connection/DBConnection.java`, chỉnh `userID` và `password` cho khớp MySQL của bạn.

Hoặc cấu hình JPA trong `src/main/resources/META-INF/persistence.xml`:
```xml
<property name="jakarta.persistence.jdbc.url"
          value="jdbc:sqlserver://localhost:1433;databaseName=jakartaJPA;encrypt=false"/>
<property name="jakarta.persistence.jdbc.user"     value="sa"/>
<property name="jakarta.persistence.jdbc.password" value="1234567@a$"/>
```

### 4.2 Cấu hình Email (BT2)
Mở file `src/main/java/vn/iotstar/service/EmailService.java` và cập nhật:
```java
private static final String FROM_EMAIL = "your-email@gmail.com";
private static final String PASSWORD = "your-app-password";
```

**Lưu ý**: Cần tạo App Password từ Google Account:
1. Vào Google Account > Security
2. Bật 2-Step Verification
3. Tạo App Password cho Mail

Thư mục upload icon mặc định: `<thư mục home>/bt1_upload` (đổi trong `Constant.java` nếu muốn).

## 5. Chạy project
```
mvn clean package
mvn jetty:run
```
Mở trình duyệt: http://localhost:9090/BT1/

## 6. Các đường dẫn

### BT1 - Chức năng cơ bản
| Chức năng | URL |
|---|---|
| Trang chủ | `/BT1/` |
| Login Cookie | `/BT1/cookie/login` |
| Login Session | `/BT1/session/login` |
| CRUD Category | `/BT1/admin/category/list` |

### BT2 - Chức năng mới
| Chức năng | URL |
|---|---|
| Trang chủ (10 SP mới) | `/BT1/home` |
| Đăng ký | `/BT1/register` |
| Xác thực OTP | `/BT1/verify-otp` |
| Đăng nhập | `/BT1/login` |
| Quên mật khẩu | `/BT1/forgot-password` |
| Reset mật khẩu | `/BT1/reset-password` |
| Danh sách sản phẩm | `/BT1/product` |
| Chi tiết sản phẩm | `/BT1/product/detail?id=1` |
| Quản lý sản phẩm (Admin) | `/BT1/admin/products` |

## 7. Cấu trúc (3 tầng)
```
model → dao (interface) → dao.impl → service (interface) → service.impl → controller → views (jsp)
```

### Cấu trúc project BT2
```
src/main/java/vn/iotstar/
├── controller/
│   ├── auth/              # Đăng ký, đăng nhập, quên mật khẩu, OTP
│   ├── product/           # Quản lý và hiển thị sản phẩm
│   └── category/          # Quản lý danh mục
├── dao/                   # Data Access Object
│   ├── IProductDao.java
│   └── impl/ProductDaoJpa.java
├── service/               # Business Logic
│   ├── EmailService.java  # Gửi email OTP
│   ├── IProductService.java
│   └── impl/ProductServiceJpa.java
├── entity/                # JPA Entities (Product, Category)
├── model/                 # POJO Models (User)
└── util/                  # Utilities (Constant)

src/main/webapp/views/
├── auth/                  # Login, Register, Verify OTP, Forgot Password
├── admin/                 # CRUD Products, Categories
├── home.jsp               # Trang chủ - 10 sản phẩm mới nhất
├── product-list.jsp       # Danh sách sản phẩm phân trang
└── product-detail.jsp     # Chi tiết sản phẩm
```

## 8. Công nghệ sử dụng

- **Backend**: Java 17, Jakarta EE 10, Servlet
- **Database**: SQL Server / MySQL
- **ORM**: Hibernate 6.6.1 (JPA)
- **Upload file**: Apache Commons FileUpload 1.5 (Multipart)
- **Email**: Jakarta Mail 2.0.1
- **View**: JSP, JSTL
- **Server**: Jetty 12 / Tomcat 10

## 9. Tài khoản mặc định

- Username: `trung`
- Password: `123`
- Role: Admin (đã kích hoạt)

## 10. Lưu ý

1. **Upload file**: File được lưu tại `%USERPROFILE%/bt1_upload` (Windows) hoặc `~/bt1_upload` (Linux/Mac)
2. **Email OTP**: Cần cấu hình email Gmail với App Password mới gửi được OTP
3. **Database**: Project hỗ trợ cả MySQL và SQL Server, chỉ cần đổi cấu hình trong persistence.xml
4. **Multipart Upload**: Sử dụng Apache Commons FileUpload theo tài liệu từ giáo viên

## 11. Link GitHub

https://github.com/KajriVN/Lap_trinh_web

---

**Cập nhật lần cuối**: 03/09/2026
