package vn.edu.eaut.lab14.model;

/**
 * Hoc phan - dung cho Bai 6: cau hinh URL /courses/** chi cho ADMIN truy cap.
 */
public class Course {

    private Long id;
    private String code;
    private String name;
    private int credit;

    public Course() {
    }

    public Course(Long id, String code, String name, int credit) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
