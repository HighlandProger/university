package ua.com.foxminded.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Model class Student.
 * ORM - teachers table
 */
@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - department
     */
    private Department department;
    /**
     * Property - lessons
     */
    private List<Lesson> lessons;

    /**
     * Empty constructor
     */
    public Teacher() {
    }

    /**
     * Full Constructor
     *
     * @param id         teacher's id
     * @param firstName  teacher's first name
     * @param lastName   teacher's last name
     * @param age        teacher's age
     * @param department teacher's department
     */
    public Teacher(Long id, String firstName, String lastName, int age, Department department) {
        super(firstName, lastName, age);
        this.id = id;
        this.department = department;
    }

    /**
     * Constructor only with superclass params
     *
     * @param firstName teacher's first name
     * @param lastName  teacher's last name
     * @param age       teacher's age
     */
    public Teacher(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }

    /**
     * Constructor for creating using DAO
     *
     * @param firstName  teacher's first name
     * @param lastName   teacher's last name
     * @param age        teacher's age
     * @param department teacher's department
     */
    public Teacher(String firstName, String lastName, int age, Department department) {
        super(firstName, lastName, age);
        this.department = department;
    }

    /**
     * This method is used to ORM property id
     * in column id at teachers table
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
     * This method is used to ORM for param department
     * in column department_id at teachers table.
     * Reference - depends on property id in Department class
     */
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "department_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * This method is used to cascade remove lessons with the removed teacher
     */
    @OneToMany(mappedBy = "teacher")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", " + super.toString() + ", department=" + department + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return getId().equals(teacher.getId()) && getDepartment().equals(teacher.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepartment());
    }
}
