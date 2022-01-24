package ua.com.foxminded.model;

import java.util.Objects;

public class Teacher extends Person {

    private Long id;
    private Long departmentId;

    public Teacher() {
    }

    public Teacher(Long id, Long departmentId, String firstName, String lastName, int age) {
        super(firstName, lastName, age);
        this.id = id;
        this.departmentId = departmentId;
    }

    public Teacher(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }

    public Teacher(String firstName, String lastName, int age, long departmentId) {
        super(firstName, lastName, age);
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", departmentId=" + departmentId + ", " + super.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getDepartmentId(), teacher.getDepartmentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDepartmentId());
    }
}
