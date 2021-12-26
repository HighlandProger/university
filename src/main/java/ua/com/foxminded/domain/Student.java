package ua.com.foxminded.domain;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Person;

public class Student extends Person {

    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
