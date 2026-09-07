# Lab 14 - Bao mat ung dung voi Spring Security

Cong nghe Java (IT3242) - Chuong 4: Phat trien ung dung voi Spring Framework.

## 1. Import vao Eclipse

1. Mo Eclipse, chon **File > Import... > Maven > Existing Maven Projects**.
2. Bam **Browse...**, chon thu muc `lab14-spring-security` (thu muc chua file `pom.xml`).
3. Eclipse se tu nhan dien `pom.xml`, tick chon project va bam **Finish**.
4. Doi Eclipse tai dependency ve xong (thanh trang thai goc duoi cung).
5. Chuot phai vao project > **Run As > Maven build...** > Goals: `spring-boot:run`
   (hoac chuot phai vao `Lab14Application.java` > **Run As > Java Application**).

> Yeu cau: JDK 17+ da duoc cau hinh trong Eclipse (Window > Preferences > Java >
> Installed JREs), va project duoc set dung JDK 17 (chuot phai project > Properties
> > Java Build Path / Java Compiler).

## 2. Chay bang dong lenh (kiem tra ngoai Eclipse)

```bash
mvn clean package
mvn spring-boot:run
```

Truy cap: http://localhost:8080

## 3. Tai khoan demo (Bai 3 - user trong bo nho)

| Tai khoan | Mat khau | Vai tro |
|-----------|----------|---------|
| admin     | 123456   | ADMIN   |
| user      | 123456   | USER    |

## 4. Cac URL chinh

| URL | Quyen truy cap |
|-----|-----------------|
| `/`, `/about` | Cong khai |
| `/login` | Cong khai (trang dang nhap) |
| `/students` | ADMIN, USER (xem danh sach) |
| `/students/create`, `/students/edit/**`, `/students/delete/**` | Chi ADMIN |
| `/courses` | Chi ADMIN (Bai 6) |
| `/error/403` | Trang bao loi khi bi tu choi quyen (Bai 7) |

## 5. Mapping voi cac bai tap trong de

| Bai | Noi dung | Vi tri trong ma nguon |
|-----|----------|------------------------|
| 1 | Them dependency Spring Security + Thymeleaf Security | `pom.xml` |
| 2 | Cau hinh Security co ban | `config/SecurityConfig.java` |
| 3 | User trong bo nho | `SecurityConfig.userDetailsService()` |
| 4 | Trang dang nhap | `controller/AuthController.java`, `templates/auth/login.html` |
| 5 | An/hien chuc nang theo quyen | `templates/students/list.html`, `templates/fragments/navbar.html` |
| 6 | `/courses/**` chi ADMIN | `SecurityConfig` (matcher `/courses/**`) + `CourseController` |
| 7 | Trang bao loi 403 | `SecurityConfig.exceptionHandling().accessDeniedPage()`, `templates/error/403.html` |
| 8 | Menu khac nhau cho ADMIN/USER | `templates/fragments/navbar.html` (menu "Hoc phan" chi ADMIN thay) |
| 9 | Bao ve chuc nang xoa sinh vien | `SecurityConfig` (matcher `/students/delete/**`), nut Xoa trong `students/list.html` chi ADMIN thay |
| 10 (nang cao) | User luu trong CSDL thay vi bo nho | `model/AppUser.java`, `repository/AppUserRepository.java`, `service/JpaUserDetailsService.java`, `config/DataInitializer.java` (xem huong dan kich hoat ben duoi) |

## 6. Kich hoat Bai 10 (user trong CSDL thay vi bo nho)

Mac dinh project van dung `InMemoryUserDetailsManager` (dung code goi y trong de,
Bai 3) de de doi chieu. Da chuan bi san toan bo ha tang cho Bai 10 (bang H2
`app_user`, repository, `JpaUserDetailsService`). De chuyen sang dung no:

1. Mo `config/SecurityConfig.java`, xoa (hoac comment) toan bo method
   `userDetailsService(...)` dang tra ve `InMemoryUserDetailsManager`.
2. Khoi dong lai ung dung. Spring se tu dong dung `JpaUserDetailsService`
   (annotate `@Service`) vi day la `UserDetailsService` bean duy nhat con lai.
3. `DataInitializer` da tu dong tao san 2 ban ghi `admin/123456` (ADMIN) va
   `user/123456` (USER) trong bang H2 `app_user` khi ung dung khoi dong.
4. Co the xem du lieu tai `http://localhost:8080/h2-console`
   (JDBC URL: `jdbc:h2:mem:lab14db`, User: `sa`, Password: de trong).

## 7. Chup anh minh chung (muc 9 trong de bai)

Goi y cac buoc chup anh:

1. Dang nhap bang `admin/123456` -> vao `/students` -> thay nut "Them sinh vien"
   va cot "Thao tac" (Sua/Xoa).
2. Dang xuat, dang nhap lai bang `user/123456` -> vao `/students` -> khong thay
   nut them/sua/xoa, chi xem duoc danh sach.
3. Van dang nhap voi `user`, thu truy cap `http://localhost:8080/students/create`
   hoac `http://localhost:8080/courses` -> bi chuyen den trang **403** tuy chinh.

## 8. Cau truc thu muc

```
lab14-spring-security/
├── pom.xml
├── README.md
└── src/main/
    ├── java/vn/edu/eaut/lab14/
    │   ├── Lab14Application.java
    │   ├── config/
    │   │   ├── SecurityConfig.java
    │   │   └── DataInitializer.java        (Bai 10 - tuy chon)
    │   ├── controller/
    │   │   ├── HomeController.java
    │   │   ├── AuthController.java
    │   │   ├── StudentController.java
    │   │   └── CourseController.java       (Bai 6)
    │   ├── model/
    │   │   ├── Student.java
    │   │   ├── Course.java
    │   │   └── AppUser.java                (Bai 10 - tuy chon)
    │   ├── repository/
    │   │   └── AppUserRepository.java      (Bai 10 - tuy chon)
    │   └── service/
    │       └── JpaUserDetailsService.java  (Bai 10 - tuy chon)
    └── resources/
        ├── application.properties
        ├── static/css/style.css
        └── templates/
            ├── home.html
            ├── about.html
            ├── auth/login.html
            ├── error/403.html
            ├── fragments/navbar.html
            ├── students/list.html
            ├── students/form.html
            └── courses/list.html
```
