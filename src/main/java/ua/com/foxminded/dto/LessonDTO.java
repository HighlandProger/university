package ua.com.foxminded.dto;

import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Teacher;

import java.time.LocalDateTime;

public class LessonDTO {

    private Long id;
    private String name;
    private String groupAbbreviation;
    private String teacher;
    private LocalDateTime dateTime;
    private String classNumber;

    public LessonDTO(Lesson lesson, Group group, Teacher teacher, ClassRoom classRoom) {
        this.id = lesson.getId();
        this.name = lesson.getName();
        this.groupAbbreviation = group.getAbbreviation();
        this.teacher = teacher.getFirstName() + " " + teacher.getLastName() + ", " + teacher.getAge();
        this.dateTime = lesson.getDateTime();
        this.classNumber = classRoom.getClassNumber();
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
