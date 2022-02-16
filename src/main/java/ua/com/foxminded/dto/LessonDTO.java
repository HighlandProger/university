package ua.com.foxminded.dto;

import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Teacher;

import java.time.LocalDateTime;

public class LessonDTO {

    private Long id;
    private String name;
    private String groupAbbreviation;
    private String teacherFullName;
    private LocalDateTime dateTime;
    private String classNumber;

    public LessonDTO(Lesson lesson, String groupAbbreviation, Teacher teacher, String classNumber) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.dateTime = lesson.getDateTime();
        this.groupAbbreviation = groupAbbreviation;
        this.teacherFullName = teacher.getFirstName() + " " + teacher.getLastName();
        this.classNumber = classNumber;
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

    public String getGroupAbbreviation() {
        return groupAbbreviation;
    }

    public void setGroupAbbreviation(String groupAbbreviation) {
        this.groupAbbreviation = groupAbbreviation;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
}
