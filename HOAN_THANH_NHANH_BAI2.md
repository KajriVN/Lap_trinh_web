# HOÀN THÀNH BÀI TẬP 02 - NHÁNH main-bai2

## ✅ ĐÃ HOÀN THÀNH TẤT CẢ 8 TASKS

### Link GitHub nhánh main-bai2:
**https://github.com/KajriVN/Lap_trinh_web/tree/main-bai2**

### Danh sách commits (8 commits với message tiếng Việt "phèn"):

1. ✅ **Task 2**: `day la chuc nang dang ky tai khoan voi xac thuc OTP qua email ne`
   - Đăng ký tài khoản + OTP verification qua email
   - Files: RegisterController, VerifyOTPController, EmailService, register.jsp, verify-otp.jsp

2. ✅ **Task 3**: `hello day la chuc nang dang nhap nha`
   - Đăng nhập với kiểm tra tài khoản đã kích hoạt
   - Files: LoginController, WaitingController, login.jsp

3. ✅ **Task 4**: `done chuc nang quen mat khau roi do`
   - Quên mật khẩu + reset bằng OTP qua email
   - Files: ForgotPasswordController, ResetPasswordController, forgot-password.jsp, reset-password.jsp

4. ✅ **Task 6**: `cai nay co chuc nang la them bang products vao database voi moi quan he 1-n voi category do`
   - Tạo bảng products với FK đến category
   - Files: Product.java entity, cập nhật Category.java, database.sql, persistence.xml

5. ✅ **Task 5**: `day la CRUD san pham voi upload file bang Multipart do nha`
   - CRUD Products với @MultipartConfig
   - Files: ProductController, ProductDao, ProductService, list-product.jsp, add-product.jsp, edit-product.jsp

6. ✅ **Task 7**: `done hien thi 10 san pham moi nhat tren trang chu roi nhe`
   - Hiển thị 10 sản phẩm mới nhất
   - Files: HomeController, home.jsp

7. ✅ **Task 8**: `cai nay co chuc nang phan trang 6 san pham tren 1 trang do nha`
   - Phân trang 6 sản phẩm/trang
   - Files: ProductListController, product-list.jsp

8. ✅ **Task 9**: `xong het roi do nhe hien thi chi tiet san pham khi bam vao`
   - Chi tiết sản phẩm khi click
   - Files: ProductDetailController, product-detail.jsp

---

## 📊 Thống kê:

- **Tổng số commits**: 8 commits riêng biệt cho từng task
- **Tổng số files thay đổi**: 37 files
- **Dòng code thêm mới**: 2,600+ dòng
- **Nhánh**: `main-bai2`

---

## 🎯 Các chức năng đã implement:

### Authentication & OTP:
- ✅ Đăng ký với OTP qua email (5 phút hiệu lực)
- ✅ Xác thực OTP để kích hoạt tài khoản
- ✅ Đăng nhập (chỉ cho phép tài khoản đã kích hoạt)
- ✅ Quên mật khẩu với OTP reset

### Product Management:
- ✅ Bảng products 1-n với category
- ✅ CRUD Products với Multipart upload
- ✅ Hiển thị 10 sản phẩm mới nhất trang chủ
- ✅ Phân trang 6 sản phẩm/trang
- ✅ Chi tiết sản phẩm

### Technologies:
- ✅ Jakarta Mail 2.0.1 (gửi OTP)
- ✅ Apache Commons FileUpload 1.5 (Multipart)
- ✅ JPA/Hibernate 6.6.1
- ✅ Jakarta EE 10
- ✅ @MultipartConfig annotation

---

## 📝 Cách sử dụng:

### 1. Clone và checkout nhánh:
```bash
git clone https://github.com/KajriVN/Lap_trinh_web.git
cd Lap_trinh_web
git checkout main-bai2
```

### 2. Import database:
```bash
mysql -u root -p < database.sql
```

### 3. Cấu hình email trong EmailService.java:
```java
private static final String FROM_EMAIL = "your-email@gmail.com";
private static final String PASSWORD = "your-app-password";
```

### 4. Build và chạy:
```bash
mvn clean package
mvn jetty:run
```

### 5. Truy cập:
- Trang chủ: http://localhost:9090/BT1/home
- Đăng ký: http://localhost:9090/BT1/register
- Đăng nhập: http://localhost:9090/BT1/login
- Admin products: http://localhost:9090/BT1/admin/products

---

## 🔗 Links quan trọng:

- **Nhánh main-bai2**: https://github.com/KajriVN/Lap_trinh_web/tree/main-bai2
- **Nhánh main** (có tất cả code): https://github.com/KajriVN/Lap_trinh_web

---

**Ngày hoàn thành**: 03/09/2026  
**Tất cả 8 tasks đã hoàn thành với commit riêng biệt!** 🎉
