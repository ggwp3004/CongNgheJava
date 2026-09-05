# Hướng dẫn chạy Lab 13 trên Eclipse

## 1. Yêu cầu
- Eclipse **phiên bản có hỗ trợ Spring** (khuyên dùng **Spring Tool Suite - STS**, tải tại
  https://spring.io/tools) hoặc Eclipse IDE for Enterprise Java Developers + cài thêm plugin
  **m2e** (Maven, thường có sẵn) — không bắt buộc phải có Spring Tools nếu bạn chỉ chạy bằng Maven.
- JDK 17 trở lên đã cài và trỏ đúng trong Eclipse (Window > Preferences > Java > Installed JREs).

## 2. Import project vào Eclipse
1. Giải nén file `lab13-spring-data-jpa.zip` ra một thư mục bất kỳ (không để trong thư mục có dấu
   tiếng Việt hoặc khoảng trắng để tránh lỗi Maven).
2. Mở Eclipse/STS.
3. Vào **File > Import... > Maven > Existing Maven Projects** > Next.
4. Ở ô "Root Directory", bấm **Browse** rồi chọn thư mục `lab13-spring-data-jpa` vừa giải nén.
5. Eclipse sẽ tự nhận diện `pom.xml`, tick chọn project rồi bấm **Finish**.
6. Chờ Eclipse tải dependency Maven (xem tiến trình ở góc dưới phải, thanh "Building workspace").
   Nếu chưa tự tải, click phải vào project > **Maven > Update Project... (Alt+F5)**.

## 3. Chạy ứng dụng
Có 2 cách:

**Cách 1 — Chạy bằng Eclipse (khuyên dùng khi debug):**
- Mở file `Lab13Application.java` (trong package `vn.edu.eaut.lab13`).
- Click phải > **Run As > Java Application** (hoặc **Spring Boot App** nếu dùng STS).

**Cách 2 — Chạy bằng Maven (giống terminal):**
- Click phải vào project > **Run As > Maven build...**
- Ô "Goals" nhập: `spring-boot:run`
- Bấm **Run**.

## 4. Kiểm thử
- Mở trình duyệt: http://localhost:8080/students
- Xem H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:eautdb`
  - User: `sa`, Password: (để trống)
- Thử các chức năng: Thêm, Sửa, Xóa, Tìm kiếm theo họ tên.

## 5. Nếu Eclipse báo lỗi "package jakarta.persistence does not exist"
- Đây là do dependency chưa được tải đủ. Click phải project > **Maven > Update Project (Alt+F5)**,
  tick **Force Update of Snapshots/Releases**, bấm OK.
- Kiểm tra lại project đang dùng đúng JDK 17 (click phải project > Properties > Java Build Path >
  Libraries).

## 6. Bài 8–10 (tự làm)
- **Bài 8, 9**: Tạo tương tự Student — thêm `Course.java` (entity), `CourseRepository`,
  `CourseService`, `CourseController`, và 2 file template `courses/list.html`, `courses/form.html`
  theo đúng mẫu Student đã có sẵn trong project này.
- **Bài 10**: Trong `pom.xml`, comment dependency H2 lại và bỏ comment dependency MySQL.
  Trong `application.properties`, comment phần cấu hình H2 và bỏ comment phần cấu hình MySQL,
  chỉnh lại username/password đúng với MySQL trên máy bạn. Chạy lại project, mở
  MySQL Workbench để chụp ảnh bảng dữ liệu `students`.
