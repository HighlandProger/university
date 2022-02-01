package ua.com.foxminded.model;

import java.util.Objects;

public class ClassRoom {

    private Long id;
    private String classNumber;

    public ClassRoom(Long id, String classNumber) {
        this.id = id;
        this.classNumber = classNumber;
    }

    public ClassRoom(String classNumber) {
        this.classNumber = classNumber;
    }

    public ClassRoom() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRoom)) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return Objects.equals(getId(), classRoom.getId()) && getClassNumber().equals(classRoom.getClassNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClassNumber());
    }

    @Override
    public String toString() {
        return "ClassRoom{" + "id=" + id + ", classNumber='" + classNumber + '\'' + '}';
    }
}
