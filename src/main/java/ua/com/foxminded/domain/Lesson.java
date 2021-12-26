package ua.com.foxminded.domain;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Teacher;

import java.util.Date;

public class Lesson {

    private String name;
    private Teacher teacher;
    private Group group;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
