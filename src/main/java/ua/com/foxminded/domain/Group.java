package ua.com.foxminded.domain;

import java.util.Objects;

public class Group {

    private Long id;
    private Long departmentId;
    private Long courseId;
    private int groupNumber;

    public Group() {
    }

    public Group(Long departmentId, Long courseId, int groupNumber) {
        this.departmentId = departmentId;
        this.courseId = courseId;
        this.groupNumber = groupNumber;
    }

    public Group(Long id, Long departmentId, Long courseId, int groupNumber) {
        this.id = id;
        this.departmentId = departmentId;
        this.courseId = courseId;
        this.groupNumber = groupNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return getGroupNumber() == group.getGroupNumber() && getDepartmentId().equals(group.getDepartmentId()) && getCourseId().equals(group.getCourseId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDepartmentId(), getCourseId(), getGroupNumber());
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

}
