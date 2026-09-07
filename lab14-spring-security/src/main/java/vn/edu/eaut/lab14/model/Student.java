package vn.edu.eaut.lab14.model;

/**
 * Sinh vien - du lieu duoc luu trong bo nho (danh sach) de tap trung minh hoa
 * Spring Security thay vi Spring Data JPA (da hoc o cac lab truoc).
 */
public class Student {

    private Long id;
    private String name;
    private String className;
    private int age;

    public Student() {
    }

    public Student(Long id, String name, String className, int age) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
