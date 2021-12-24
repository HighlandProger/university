package ua.com.foxminded.domain;

import ua.com.foxminded.domain.person.Teacher;

import java.util.List;

public class Department {

    private int id;
    private String name;
    private List<Teacher> teachers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
