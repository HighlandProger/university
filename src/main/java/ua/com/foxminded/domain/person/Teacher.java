package ua.com.foxminded.domain.person;

import ua.com.foxminded.domain.Department;
import ua.com.foxminded.domain.Person;

public class Teacher extends Person {

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
