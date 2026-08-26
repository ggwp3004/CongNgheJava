# Lab 10 - Hướng dẫn chạy trên Eclipse

## 1. Chuẩn bị
- Cài JDK 17 (hoặc 21), Eclipse IDE for Enterprise Java and Web Developers (bản có sẵn WTP).
- Cài Apache Tomcat 10.x (tải bản zip, giải nén ra một thư mục, ví dụ `C:\tomcat10`).
  Lưu ý: XAMPP **không thay được Tomcat**, chỉ dùng XAMPP cho phần MySQL.
- Cài CSDL — có 2 cách, chọn 1:
  - **Dùng XAMPP** (đơn giản nhất): mở XAMPP Control Panel, bật **MySQL** (không cần bật Apache
    của XAMPP). Vào `http://localhost/phpmyadmin` → tab **SQL** → dán nội dung file
    `sql/schema.sql` → bấm **Go**. Mặc định XAMPP dùng user `root`, không mật khẩu — project này
    đã cấu hình sẵn theo mặc định đó trong `persistence.xml`.
  - **Dùng MySQL cài riêng**: `mysql -u root -p < sql/schema.sql`, rồi tự sửa lại user/password
    trong `persistence.xml` cho khớp.

## 2. Import project vào Eclipse
1. Mở Eclipse → `File` → `Import...` → `Maven` → `Existing Maven Projects`.
2. Chọn thư mục `lab10-secured-app` (thư mục chứa `pom.xml`) → `Finish`.
3. Eclipse sẽ tự tải dependency trong `pom.xml` (cần Internet ở bước này).
4. Chuột phải vào project → `Properties` → `Project Facets` → tick chọn
   `Dynamic Web Module` (version 6.0) và `Java` (17). Nếu Eclipse báo project chưa
   có facet, chọn `Convert to Faceted Form` trước.
5. Kiểm tra `Deployment Assembly` (chuột phải project → Properties → Deployment
   Assembly): đảm bảo `src/main/webapp` map vào `/` và Maven Dependencies được đưa vào
   `WEB-INF/lib`.

## 3. Cấu hình Tomcat trong Eclipse
1. Mở tab `Servers` (Window → Show View → Servers).
2. Chuột phải → `New` → `Server` → chọn `Apache Tomcat v10.x` → trỏ tới thư mục cài Tomcat.
3. Kéo project `lab10-secured-app` vào server vừa tạo (Add and Remove...).

## 4. Sửa thông tin kết nối CSDL (nếu cần)
File `src/main/resources/META-INF/persistence.xml` đã để sẵn cấu hình mặc định cho XAMPP
(user `root`, không mật khẩu, port `3306`). Nếu bạn dùng MySQL cài riêng hoặc đã đặt mật khẩu
cho root trong XAMPP, sửa lại 3 dòng sau cho đúng:
- `jakarta.persistence.jdbc.url` (tên CSDL, host, port)
- `jakarta.persistence.jdbc.user`
- `jakarta.persistence.jdbc.password`

## 5. Chạy ứng dụng
1. Chuột phải project → `Run As` → `Run on Server`.
2. Truy cập `http://localhost:8080/lab10-secured-app/login.jsp`.
3. Đăng nhập bằng tài khoản mẫu (đã có sẵn trong `schema.sql`, hoặc do
   `AppListener` tự tạo nếu bảng `users` đang rỗng):
   - admin@eaut.edu.vn / admin123
   - staff@eaut.edu.vn / staff123
   - user@eaut.edu.vn / user123

## 6. Cấu trúc chức năng đã hoàn thành
- Đăng nhập / đăng xuất, lưu currentUser vào session (`AuthController`).
- `AuthenticationFilter`: chặn `/admin/*`, `/staff/*`, `/user/*` nếu chưa đăng nhập.
- `AuthorizationFilter`: ADMIN mới vào được `/admin/*`; ADMIN hoặc STAFF vào được `/staff/*`.
- Menu hiển thị theo vai trò (`views/header.jsp`).
- Trang lỗi 403/404/500 thân thiện, cấu hình trong `web.xml`.
- Module nghiệp vụ mẫu: **Sản phẩm** (`/staff/products`) - CRUD đầy đủ, có validate và transaction.
- Quản lý tài khoản cho ADMIN (`/admin/users`): thêm/sửa/khóa/mở/xóa/tìm kiếm.
- Hồ sơ cá nhân + đổi mật khẩu cho user đã đăng nhập (`/user/profile`).

## 7. Việc bạn cần tự bổ sung để nộp bài đầy đủ
Đề bài yêu cầu **tối thiểu 3 module nghiệp vụ** (ví dụ: Sinh viên, Sách, Sản phẩm...).
Project này đã dựng sẵn khung xác thực/phân quyền + 1 module mẫu (**Sản phẩm**) để bạn
copy theo đúng mẫu (Entity → Repository → Service → Controller → JSP) và tạo thêm
2 module còn lại (ví dụ Student, Book) theo đúng cấu trúc tương tự.

Ngoài ra, hãy:
- Chụp ảnh màn hình chạy thực tế cho báo cáo.
- Viết báo cáo mô tả kiến trúc, sơ đồ luồng login → filter → controller.
- Đóng gói `Lab10_MSSV_HoTen.zip` gồm source code + `sql/schema.sql` + ảnh chạy + báo cáo.
