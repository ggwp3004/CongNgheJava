package vn.edu.eaut.lab13.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.eaut.lab13.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Bai 7: tim kiem sinh vien theo ho ten (khong phan biet hoa/thuong)
    List<Student> findByFullNameContainingIgnoreCase(String keyword);

    boolean existsByStudentCode(String studentCode);
}
