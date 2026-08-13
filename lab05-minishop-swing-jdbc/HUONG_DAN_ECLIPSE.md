# Hướng dẫn chạy project MiniShop trong Eclipse (dùng Maven)

## 1. Giải nén file zip
Giải nén `lab05-minishop-swing-jdbc.zip` ra một thư mục bất kỳ. Bạn sẽ thấy:
```
lab05-minishop-swing-jdbc/
├── pom.xml
├── database/minishop_db.sql
└── src/main/java/vn/edu/eaut/lab5/...
```

## 2. Chuẩn bị CSDL
1. Mở MySQL Workbench / phpMyAdmin / mysql CLI.
2. Chạy toàn bộ nội dung file `database/minishop_db.sql` để tạo database `minishop_db`
   cùng 4 bảng và dữ liệu mẫu.

## 3. Import vào Eclipse (đúng theo màn hình bạn đang thấy)
1. **File → Import... → Maven → Existing Maven Projects → Next**.
2. Ở ô "Root Directory", bấm **Browse...** và chọn đúng thư mục `lab05-minishop-swing-jdbc`
   (thư mục chứa file `pom.xml`, KHÔNG phải thư mục cha chứa nó).
3. Eclipse sẽ hiện danh sách project tìm thấy (tick chọn `pom.xml` của
   `lab05-minishop-swing-jdbc`) → bấm **Finish**.
4. Eclipse (qua m2e) sẽ tự động tải `mysql-connector-j-8.4.0` từ Maven Central về
   (cần có internet). Đợi thanh tiến trình ở góc dưới phải chạy xong.

> Nếu Eclipse báo lỗi thiếu Maven hoặc project chưa build được, chuột phải vào project
> → **Maven → Update Project...** (Alt+F5) để Eclipse tải lại dependency.

## 4. Cấu hình kết nối CSDL
Mở file `src/main/java/vn/edu/eaut/lab5/config/DBHelper.java`, chỉnh lại:
```java
private static final String URL = "jdbc:mysql://localhost:3306/minishop_db?...";
private static final String USER = "root";
private static final String PASSWORD = "";   // đổi thành mật khẩu MySQL của bạn
```

## 5. Chạy chương trình
1. Mở file `src/main/java/vn/edu/eaut/lab5/App.java`.
2. Chuột phải → **Run As → Java Application**.
3. Console sẽ in `Ket noi CSDL thanh cong!` nếu kết nối đúng, sau đó cửa sổ MiniShop
   (JTabbedPane với 4 tab: Sản phẩm, Khách hàng, Hóa đơn, Thống kê) sẽ hiện lên.

## Nếu máy không có internet (không tải được Maven dependency)
Bạn vẫn có thể chạy mà không cần Maven tải jar:
1. Tải sẵn `mysql-connector-j-8.4.0.jar` từ nơi khác, đặt vào thư mục project (VD: `lib/`).
2. Chuột phải project → **Build Path → Configure Build Path → Libraries → Add JARs...**
   → chọn file jar đó (bổ sung thủ công, song song với Maven).
3. Cách này project vẫn chạy được, chỉ là Maven không tự quản lý dependency đó nữa.

## Cấu trúc project
```
lab05-minishop-swing-jdbc/
├── pom.xml
├── database/minishop_db.sql
└── src/main/java/vn/edu/eaut/lab5/
    ├── App.java
    ├── config/DBHelper.java
    ├── model/  (SanPham, KhachHang, HoaDon, ChiTietHoaDon)
    ├── dal/    (SanPhamDAL, KhachHangDAL, HoaDonDAL, ThongKeDAL)
    ├── bus/    (SanPhamBUS, KhachHangBUS, HoaDonBUS, ThongKeBUS)
    ├── ui/     (MainFrame, SanPhamPanel, KhachHangPanel, HoaDonPanel, ThongKePanel)
    └── util/   (MessageUtil, PhoneDocumentFilter)
```

## Các chức năng đã cài đặt (Phần A - 5 bài)
- **Bài 1**: Kết nối JDBC qua `DBHelper`, kiểm tra kết nối khi khởi động (`App.main`).
- **Bài 2**: CRUD + tìm kiếm sản phẩm theo tên.
- **Bài 3**: CRUD khách hàng + validate SĐT (chặn ngay lúc gõ bằng `DocumentFilter`,
  validate lại ở BUS).
- **Bài 4**: Lập hóa đơn — chọn KH/SP bằng JComboBox, thêm dòng vào bảng tạm, tính tổng tiền,
  lưu bằng **transaction** (hóa đơn + chi tiết + trừ tồn kho cùng lúc, rollback nếu lỗi).
- **Bài 5**: Thống kê doanh thu theo khoảng ngày, hóa đơn cao nhất, sản phẩm bán chạy nhất —
  chạy bằng **SwingWorker** để không treo giao diện.

## Ghi chú
- Phần B (bài 6–10: danh mục, phân trang, xuất file, đăng nhập phân quyền) để trống
  đúng theo yêu cầu đề bài ("sinh viên tự thiết kế") — dùng cấu trúc 3 lớp có sẵn làm khuôn mẫu.
- Lỗi `No suitable driver found` / `ClassNotFoundException: com.mysql.cj.jdbc.Driver`
  → do Maven chưa tải xong dependency, thử **Maven → Update Project**.
