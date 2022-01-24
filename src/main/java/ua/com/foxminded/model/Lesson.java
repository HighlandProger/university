package ua.com.foxminded.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Lesson {

    private static final String TIME_PATTERN = "dd.MM.yyyy HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);

    private Long id;
    private String name;
    private Long teacherId;
    private Long groupId;
    private LocalDateTime dateTime;

    public Lesson() {
    }

    public Lesson(Long id, String name, Long teacherId, Long groupId, LocalDateTime dateTime) {

        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.dateTime = dateTime;
    }

    public Lesson(String name, Long teacherId, Long groupId, LocalDateTime dateTime) {

        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return getName().equals(lesson.getName()) && getTeacherId().equals(lesson.getTeacherId()) && getGroupId().equals(lesson.getGroupId()) && getDateTime().equals(lesson.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTeacherId(), getGroupId(), getDateTime());
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Lesson{" + "id=" + id + ", name='" + name + '\'' + ", teacherId=" + teacherId + ", groupId=" + groupId + ", dateTime=" + dateTime.format(formatter) + '}';
    }
}
