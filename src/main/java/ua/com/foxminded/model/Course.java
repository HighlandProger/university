package ua.com.foxminded.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Model class Courses.
 * ORM - courses
 */
@Entity
@Table(name = "courses")
public class Course {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - establish year
     */
    private int establishYear;

    /**
     * Empty constructor
     */
    public Course() {
    }

    /**
     * Full Constructor
     *
     * @param id            course's id
     * @param establishYear course's establish year
     */
    public Course(Long id, int establishYear) {
        this.id = id;
        this.establishYear = establishYear;
    }

    /**
     * Constructor for creating using DAO
     *
     * @param establishYear course's establishYear
     */
    public Course(int establishYear) {
        this.establishYear = establishYear;
    }

    /**
     * This method is used to ORM property id
     * in column id at courses table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method is used to ORM property establishYear
     * in column establish_year at courses table
     */
    @Column(name = "establish_year", nullable = false)
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
        return getEstablishYear() == course.getEstablishYear();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEstablishYear());
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", establishYear=" + establishYear +
            '}';
    }
}
