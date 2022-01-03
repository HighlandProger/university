package ua.com.foxminded.domain;

import java.util.Objects;

public class Course {

    private Long id;
    private int establishYear;

    public Course() {
    }

    public Course(int establishYear) {
        this.establishYear = establishYear;
        this.id = establishYear % 10L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEstablishYear() {
        return establishYear;
    }

    public void setEstablishYear(int establishYear) {
        this.establishYear = establishYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return establishYear == course.establishYear &&
            id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, establishYear);
    }
}
