package ua.com.foxminded.domain;

import ua.com.foxminded.util.DateUtils;

import java.util.Objects;

public class Lesson {

    private Long id;
    private String name;
    private Long teacherId;
    private Long groupId;
    private Long date;

    public Lesson() {
    }

    public Lesson(String name, Long teacherId, Long groupId, Long date) {
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.date = date;
    }

    public Lesson(String name, Long teacherId, Long groupId, String dateString) {
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.date = DateUtils.getMillisecondsFromDateString(dateString);
    }

    public Lesson(Long id, String name, Long teacherId, Long groupId, Long date) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.date = date;
    }

    public Lesson(Long id, String name, Long teacherId, Long groupId, String dateString) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.date = DateUtils.getMillisecondsFromDateString(dateString);
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) &&
            name.equals(lesson.name) &&
            teacherId.equals(lesson.teacherId) &&
            groupId.equals(lesson.groupId) &&
            date.equals(lesson.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacherId, groupId, date);
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", teacherId=" + teacherId +
            ", groupId=" + groupId +
            ", date=" + date +
            '}';
    }
}
