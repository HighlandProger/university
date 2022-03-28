package ua.com.foxminded.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Model class Department.
 * ORM - departments
 */
@Entity
@Table(name = "departments")
public class Department implements Serializable {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - name
     */
    private String name;

    /**
     * Empty constructor
     */
    public Department() {
    }

    /**
     * Full Constructor
     *
     * @param id   department's id
     * @param name department's name
     */
    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor for creating using DAO
     *
     * @param name department's name
     */
    public Department(String name) {
        this.name = name;
    }

    /**
     * This method is used to ORM property id
     * in column id at departments table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * This method is used to ORM property name
     * in column name at departments table
     */
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
