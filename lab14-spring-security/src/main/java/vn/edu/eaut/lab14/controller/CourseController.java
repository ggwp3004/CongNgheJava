package vn.edu.eaut.lab14.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.edu.eaut.lab14.model.Course;

/**
 * Bai 6: /courses/** chi ADMIN duoc truy cap (cau hinh trong SecurityConfig).
 * Neu USER co gang vao /courses se bi chuyen den trang 403 (Bai 7).
 */
@Controller
public class CourseController {

    @GetMapping("/courses")
    public String list(Model model) {
        model.addAttribute("courses", List.of(
                new Course(1L, "IT3242", "Cong nghe Java", 3),
                new Course(2L, "IT3080", "Co so du lieu", 3),
                new Course(3L, "IT3090", "He dieu hanh", 3)
        ));
        return "courses/list";
    }
}
