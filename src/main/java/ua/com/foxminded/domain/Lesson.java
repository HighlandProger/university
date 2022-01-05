package ua.com.foxminded.domain;

import java.sql.Timestamp;
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

    public Lesson(String name, Long teacherId, Long groupId, String dateString) {
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.dateTime = LocalDateTime.parse(dateString, formatter);
    }

    public Lesson(Long id, String name, Long teacherId, Long groupId, String dateString) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.dateTime = LocalDateTime.parse(dateString, formatter);
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

    public void setDateTime(Timestamp timestamp) {
        this.dateTime = timestamp.toLocalDateTime();
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
            dateTime.equals(lesson.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teacherId, groupId, dateTime);
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", teacherId=" + teacherId +
            ", groupId=" + groupId +
            ", dateTime=" + dateTime.format(formatter) +
            '}';
    }
}
