package ua.com.foxminded.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

/**
 * Model class Student.
 * ORM - students
 */
@Entity
@Table(name = "students")
public class Student extends Person {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - group
     */
    private Group group;

    /**
     * Empty constructor
     */
    public Student() {
    }

    /**
     * Full Constructor
     *
     * @param id        student's id
     * @param firstName student's first name
     * @param lastName  student's last name
     * @param age       student's age
     * @param group     student's group
     */
    public Student(Long id, String firstName, String lastName, int age, Group group) {
        super(firstName, lastName, age);
        this.id = id;
        this.group = group;
    }

    /**
     * Constructor only with super class params
     *
     * @param firstName student's first name
     * @param lastName  student's last name
     * @param age       student's age
     */
    public Student(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }

    /**
     * Constructor for creating using DAO
     *
     * @param firstName student's first name
     * @param lastName  student's last name
     * @param age       student's age
     * @param group     student's group
     */
    public Student(String firstName, String lastName, int age, Group group) {
        super(firstName, lastName, age);
        this.group = group;
    }

    /**
     * This method is used to ORM property id
     * in column id at students table
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
     * This method is used to ORM for property group
     * in column group_id at students table.
     * Reference - depends on property id in Group class
     */
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getGroup(), student.getGroup());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGroup());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            super.toString() +
            ", group=" + group +
            '}';
    }
}
