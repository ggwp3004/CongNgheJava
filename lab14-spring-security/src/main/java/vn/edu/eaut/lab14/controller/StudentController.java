package vn.edu.eaut.lab14.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.edu.eaut.lab14.model.Student;

/**
 * Quan ly sinh vien - danh sach luu trong bo nho (Map) de tap trung vao chu de
 * Spring Security cua Lab 14.
 *
 * Phan quyen thuc te duoc thuc hien trong SecurityConfig (Bai 2, Bai 9):
 * - Xem danh sach: ADMIN + USER
 * - Them / xoa / sua: chi ADMIN
 *
 * Giao dien (Bai 5, Bai 8) dung sec:authorize de an/hien nut theo quyen.
 */
@Controller
public class StudentController {

    private final Map<Long, Student> students = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public StudentController() {
        Student s1 = new Student(idSequence.getAndIncrement(), "Nguyen Van A", "CNTT1", 20);
        Student s2 = new Student(idSequence.getAndIncrement(), "Tran Thi B", "CNTT2", 21);
        students.put(s1.getId(), s1);
        students.put(s2.getId(), s2);
    }

    @GetMapping("/students")
    public String list(Model model) {
        model.addAttribute("students", List.copyOf(students.values()));
        return "students/list";
    }

    @GetMapping("/students/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/form";
    }

    @PostMapping("/students/create")
    public String create(@ModelAttribute Student student) {
        student.setId(idSequence.getAndIncrement());
        students.put(student.getId(), student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", students.get(id));
        return "students/form";
    }

    @PostMapping("/students/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Student student) {
        student.setId(id);
        students.put(id, student);
        return "redirect:/students";
    }

    @PostMapping("/students/delete/{id}")
    public String delete(@PathVariable Long id) {
        students.remove(id);
        return "redirect:/students";
    }
}
