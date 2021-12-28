package ua.com.foxminded.domain;

import java.util.Date;

public class Lesson {

    private Long id;
    private String name;
    private Long teacherId;
    private Long groupId;
    private Date date;

    public Lesson() {
    }

    public Lesson(String name, long teacherId, long groupId, Date date) {
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.date = date;
    }

    public Lesson(long id, String name, long teacherId, long groupId, Date date) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.groupId = groupId;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
