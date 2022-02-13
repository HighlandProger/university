package ua.com.foxminded.dto;

import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Teacher;

public class TeacherDTO {

    private Long id;
    private String departmentName;
    private String firstName;
    private String lastName;
    private int age;

    public TeacherDTO(Teacher teacher, Department department){
        this.id = teacher.getId();
        this.departmentName = department.getName();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.age = teacher.getAge();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
