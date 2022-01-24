package ua.com.foxminded.model;

import java.util.Objects;

public class Student extends Person {

    private Long id;
    private Long groupId;

    public Student() {
    }

    public Student(Long id, Long groupId, String firstName, String lastName, int age) {
        super(firstName, lastName, age);
        this.id = id;
        this.groupId = groupId;
    }

    public Student(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }

    public Student(String firstName, String lastName, int age, long groupId) {
        super(firstName, lastName, age);
        this.groupId =groupId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(getGroupId(), student.getGroupId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId());
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", groupId=" + groupId + ", " + super.toString() + '}';
    }
}
