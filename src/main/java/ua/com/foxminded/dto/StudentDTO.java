package ua.com.foxminded.dto;

import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;

public class StudentDTO {

    private Long id;
    private String groupName;
    private String firstName;
    private String lastName;
    private int age;

    public StudentDTO(Student student, Group group){
        this.id = student.getId();
        this.groupName = group.getAbbreviation();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.age = student.getAge();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
