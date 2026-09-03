# BÁO CÁO BÀI TẬP 02 - LẬP TRÌNH WEB

## Thông tin
- **Họ tên**: [Điền tên của bạn]
- **MSSV**: [Điền MSSV của bạn]
- **Lớp**: [Điền lớp của bạn]
- **Ngày nộp**: 03/09/2026

## Link GitHub
**https://github.com/KajriVN/Lap_trinh_web**

---

## DANH SÁCH CÁC TASK ĐÃ HOÀN THÀNH

### ✅ Task 2: Bổ sung chức năng kích hoạt tài khoản bằng OTP qua email

**Mô tả**: Khi đăng ký tài khoản mới, hệ thống gửi mã OTP (6 chữ số) qua email. Người dùng phải nhập đúng OTP để kích hoạt tài khoản.

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/service/EmailService.java` - Service gửi email OTP
- `src/main/java/vn/iotstar/controller/auth/RegisterController.java` - Xử lý đăng ký
- `src/main/java/vn/iotstar/controller/auth/VerifyOTPController.java` - Xác thực OTP
- `src/main/webapp/views/auth/register.jsp` - Giao diện đăng ký
- `src/main/webapp/views/auth/verify-otp.jsp` - Giao diện nhập OTP
- `database.sql` - Thêm fields `is_active`, `otp_code`, `otp_expired` vào bảng `users`
- `src/main/java/vn/iotstar/model/User.java` - Thêm các trường OTP
- `src/main/java/vn/iotstar/dao/impl/UserDaoImpl.java` - Cập nhật insert/update
- `pom.xml` - Thêm dependency `jakarta.mail`

**Cách test**:
1. Truy cập: http://localhost:9090/BT1/register
2. Điền thông tin đăng ký (email phải là email thật)
3. Submit → Kiểm tra email nhận được mã OTP
4. Nhập OTP vào trang verify → Tài khoản được kích hoạt

---

### ✅ Task 3: Thực hiện chức năng đăng nhập

**Mô tả**: Người dùng đăng nhập bằng username và password. Chỉ cho phép đăng nhập nếu tài khoản đã được kích hoạt. Có chức năng "Ghi nhớ đăng nhập".

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/controller/auth/LoginController.java` - Xử lý đăng nhập
- `src/main/java/vn/iotstar/controller/auth/WaitingController.java` - Phân quyền sau login
- `src/main/webapp/views/auth/login.jsp` - Giao diện đăng nhập
- `src/main/java/vn/iotstar/service/impl/UserServiceImpl.java` - Kiểm tra tài khoản active

**Cách test**:
1. Truy cập: http://localhost:9090/BT1/login
2. Đăng nhập với tài khoản đã kích hoạt (ví dụ: `trung` / `123`)
3. Hệ thống chuyển hướng theo role:
   - Admin (roleid=1) → `/admin/home`
   - Manager (roleid=2) → `/manager/home`
   - User (roleid=5) → `/home`

---

### ✅ Task 4: Thực hiện chức năng quên mật khẩu gửi OTP qua mail

**Mô tả**: Người dùng quên mật khẩu có thể reset bằng cách nhận OTP qua email đã đăng ký.

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/controller/auth/ForgotPasswordController.java` - Xử lý quên mật khẩu
- `src/main/java/vn/iotstar/controller/auth/ResetPasswordController.java` - Đặt lại mật khẩu
- `src/main/webapp/views/auth/forgot-password.jsp` - Giao diện nhập email
- `src/main/webapp/views/auth/reset-password.jsp` - Giao diện nhập OTP và mật khẩu mới
- `src/main/java/vn/iotstar/service/EmailService.java` - Thêm method `sendResetPasswordOTP()`
- `src/main/java/vn/iotstar/service/impl/UserServiceImpl.java` - Thêm logic reset password

**Cách test**:
1. Truy cập: http://localhost:9090/BT1/forgot-password
2. Nhập email đã đăng ký → Submit
3. Kiểm tra email nhận OTP
4. Nhập OTP và mật khẩu mới → Reset thành công

---

### ✅ Task 6: Thêm bảng products với quan hệ 1-n với category

**Mô tả**: Tạo bảng products trong database với foreign key tới bảng category. Mỗi category có nhiều products.

**Files đã tạo/sửa**:
- `database.sql` - Tạo bảng `products` với FK đến `category.cate_id`
- `src/main/java/vn/iotstar/entity/Product.java` - JPA Entity cho Product
- `src/main/java/vn/iotstar/entity/Category.java` - Thêm `@OneToMany` relationship
- `src/main/resources/META-INF/persistence.xml` - Đăng ký Product entity

**Schema bảng products**:
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

**Dữ liệu mẫu**: 12 sản phẩm thuộc 3 categories (Điện thoại, Laptop, Phụ kiện)

---

### ✅ Task 5: Thực hiện CRUD cho bảng Products với upload Multipart

**Mô tả**: Quản lý sản phẩm với đầy đủ chức năng Create, Read, Update, Delete. Sử dụng Multipart để upload hình ảnh sản phẩm.

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/dao/IProductDao.java` - Interface DAO
- `src/main/java/vn/iotstar/dao/impl/ProductDaoJpa.java` - Implementation với JPA
- `src/main/java/vn/iotstar/service/IProductService.java` - Interface Service
- `src/main/java/vn/iotstar/service/impl/ProductServiceJpa.java` - Implementation
- `src/main/java/vn/iotstar/controller/product/ProductController.java` - Controller CRUD với `@MultipartConfig`
- `src/main/webapp/views/admin/list-product.jsp` - Danh sách sản phẩm
- `src/main/webapp/views/admin/add-product.jsp` - Form thêm sản phẩm
- `src/main/webapp/views/admin/edit-product.jsp` - Form sửa sản phẩm
- `pom.xml` - Đã có sẵn `commons-fileupload` và `commons-io`

**Các chức năng**:
- **Create**: POST `/admin/product/add` với `enctype="multipart/form-data"`
- **Read**: GET `/admin/products`
- **Update**: POST `/admin/product/edit?id=X` với multipart
- **Delete**: GET `/admin/product/delete?id=X`

**Upload file sử dụng**:
```java
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 50)    // 50MB
```

**Cách test**:
1. Đăng nhập với tài khoản Admin
2. Truy cập: http://localhost:9090/BT1/admin/products
3. Thêm/sửa/xóa sản phẩm với upload hình ảnh

---

### ✅ Task 7: Hiển thị 10 sản phẩm mới nhất lên trang chủ

**Mô tả**: Trang chủ hiển thị 10 sản phẩm được thêm gần đây nhất, sắp xếp theo `created_date DESC`.

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/controller/product/HomeController.java` - Controller trang chủ
- `src/main/webapp/views/home.jsp` - Giao diện trang chủ với grid layout
- `src/main/java/vn/iotstar/dao/impl/ProductDaoJpa.java` - Method `findLatest(int limit)`

**Query sử dụng**:
```java
String jpql = "SELECT p FROM Product p ORDER BY p.createdDate DESC";
query.setMaxResults(10);
```

**Cách test**:
1. Truy cập: http://localhost:9090/BT1/home
2. Xem 10 sản phẩm mới nhất hiển thị dạng grid
3. Click vào sản phẩm để xem chi tiết

---

### ✅ Task 8: Hiển thị tất cả sản phẩm phân trang 6sp/trang tại /product

**Mô tả**: Trang `/product` hiển thị tất cả sản phẩm với phân trang, mỗi trang 6 sản phẩm.

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/controller/product/ProductListController.java` - Controller phân trang
- `src/main/webapp/views/product-list.jsp` - Giao diện danh sách với pagination
- `src/main/java/vn/iotstar/dao/impl/ProductDaoJpa.java` - Method `findAll(int page, int pageSize)`

**Logic phân trang**:
```java
int page = 0; // Trang hiện tại
int pageSize = 6; // 6 sản phẩm/trang
query.setFirstResult(page * pageSize);
query.setMaxResults(pageSize);
```

**Cách test**:
1. Truy cập: http://localhost:9090/BT1/product
2. Xem 6 sản phẩm đầu tiên
3. Click pagination để chuyển trang: `/product?page=1`, `/product?page=2`...

---

### ✅ Task 9: Hiển thị chi tiết sản phẩm

**Mô tả**: Khi click vào sản phẩm từ trang chủ hoặc trang /product, hiển thị trang chi tiết với đầy đủ thông tin.

**Files đã tạo/sửa**:
- `src/main/java/vn/iotstar/controller/product/ProductDetailController.java` - Controller chi tiết
- `src/main/webapp/views/product-detail.jsp` - Giao diện chi tiết sản phẩm
- `src/main/java/vn/iotstar/dao/impl/ProductDaoJpa.java` - Method `findById(int productId)`

**Thông tin hiển thị**:
- Hình ảnh lớn
- Tên sản phẩm
- Giá
- Danh mục
- Mô tả chi tiết
- Trạng thái (Còn hàng/Hết hàng)

**Cách test**:
1. Từ trang chủ hoặc /product, click vào bất kỳ sản phẩm nào
2. Xem chi tiết tại: http://localhost:9090/BT1/product/detail?id=1

---

### ✅ Task 10: Upload file bằng Multipart

**Mô tả**: Đã implement upload file theo tài liệu giáo viên cung cấp.

**Công nghệ sử dụng**:
- Apache Commons FileUpload 1.5
- Apache Commons IO 2.15.1
- Jakarta Servlet API với `@MultipartConfig`

**Implementation**:
```java
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50)

Part part = req.getPart("image");
if (part.getSize() > 0) {
    String filename = part.getSubmittedFileName();
    String ext = filename.substring(filename.lastIndexOf(".") + 1);
    String fname = System.currentTimeMillis() + "." + ext;
    part.write(uploadPath + "/" + fname);
}
```

**Thư mục lưu file**: `%USERPROFILE%/bt1_upload` (có thể đổi trong `Constant.java`)

---

## CÔNG NGHỆ SỬ DỤNG

| Công nghệ | Version | Mục đích |
|-----------|---------|----------|
| Java | 17 | Ngôn ngữ lập trình |
| Jakarta EE | 10.0.0 | Servlet, JSP |
| Hibernate | 6.6.1 | JPA/ORM |
| MySQL / SQL Server | 8.x / 2019+ | Database |
| Maven | 3.6+ | Build tool |
| Jetty | 12.0.14 | Application server |
| Jakarta Mail | 2.0.1 | Gửi email OTP |
| Commons FileUpload | 1.5 | Upload file multipart |
| Commons IO | 2.15.1 | Xử lý file |
| JSTL | 3.0.1 | JSP Tag Library |

---

## CẤU TRÚC PACKAGE

```
vn.iotstar
├── config
│   └── JpaConfig.java                    # Cấu hình EntityManager
├── connection
│   └── DBConnection.java                 # JDBC Connection
├── controller
│   ├── auth                              # Authentication controllers
│   │   ├── LoginController.java
│   │   ├── RegisterController.java
│   │   ├── VerifyOTPController.java
│   │   ├── ForgotPasswordController.java
│   │   ├── ResetPasswordController.java
│   │   └── WaitingController.java
│   ├── product                           # Product controllers
│   │   ├── HomeController.java
│   │   ├── ProductController.java        # CRUD Admin
│   │   ├── ProductListController.java    # Phân trang
│   │   └── ProductDetailController.java
│   └── category
│       ├── CategoryController.java
│       └── DownloadImageController.java
├── dao
│   ├── UserDao.java
│   ├── ICategoryDao.java
│   ├── IProductDao.java
│   └── impl
│       ├── UserDaoImpl.java
│       ├── CategoryDaoJpa.java
│       └── ProductDaoJpa.java
├── entity                                # JPA Entities
│   ├── Category.java
│   ├── Product.java
│   └── Video.java
├── model                                 # POJO Models
│   └── User.java
├── service
│   ├── UserService.java
│   ├── EmailService.java                 # Gửi OTP
│   ├── ICategoryService.java
│   ├── IProductService.java
│   └── impl
│       ├── UserServiceImpl.java
│       ├── CategoryServiceJpa.java
│       └── ProductServiceJpa.java
└── util
    └── Constant.java
```

---

## HƯỚNG DẪN CÀI ĐẶT VÀ CHẠY

### 1. Clone project
```bash
git clone https://github.com/KajriVN/Lap_trinh_web.git
cd Lap_trinh_web
```

### 2. Import database
```bash
mysql -u root -p < database.sql
```

### 3. Cấu hình email
Mở `src/main/java/vn/iotstar/service/EmailService.java`:
```java
private static final String FROM_EMAIL = "your-email@gmail.com";
private static final String PASSWORD = "your-app-password";
```

**Cách tạo App Password**:
1. Đăng nhập Gmail
2. Vào: https://myaccount.google.com/security
3. Bật "2-Step Verification"
4. Tạo "App passwords" cho Mail
5. Copy password 16 ký tự và paste vào code

### 4. Build và chạy
```bash
mvn clean package
mvn jetty:run
```

### 5. Truy cập
- Trang chủ: http://localhost:9090/BT1/home
- Đăng ký: http://localhost:9090/BT1/register
- Đăng nhập: http://localhost:9090/BT1/login

---

## SCREENSHOTS

### Trang đăng ký
![Register](docs/screenshots/register.png)

### Xác thực OTP
![Verify OTP](docs/screenshots/verify-otp.png)

### Trang chủ - 10 sản phẩm mới nhất
![Home](docs/screenshots/home.png)

### Danh sách sản phẩm phân trang
![Product List](docs/screenshots/product-list.png)

### Chi tiết sản phẩm
![Product Detail](docs/screenshots/product-detail.png)

### CRUD Admin
![Admin CRUD](docs/screenshots/admin-products.png)

---

## KẾT LUẬN

Đã hoàn thành đầy đủ tất cả 8 tasks theo yêu cầu:
- ✅ Task 2: OTP kích hoạt tài khoản
- ✅ Task 3: Đăng nhập
- ✅ Task 4: Quên mật khẩu với OTP
- ✅ Task 6: Bảng products 1-n với category
- ✅ Task 5: CRUD Products với Multipart
- ✅ Task 7: 10 sản phẩm mới nhất
- ✅ Task 8: Phân trang 6sp/trang
- ✅ Task 9: Chi tiết sản phẩm
- ✅ Task 10: Upload file Multipart

Code đã được push lên GitHub và sẵn sàng nộp bài!

**Link GitHub**: https://github.com/KajriVN/Lap_trinh_web
