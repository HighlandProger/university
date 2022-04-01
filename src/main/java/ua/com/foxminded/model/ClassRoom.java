package ua.com.foxminded.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Model class ClassRoom.
 * ORM - classrooms
 */
@Entity
@Table(name = "classrooms")
public class ClassRoom {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - class number
     */
    private String classNumber;
    /**
     * Property - lessons
     */
    private List<Lesson> lessons;

    /**
     * Empty constructor
     */
    public ClassRoom() {
    }

    /**
     * Full Constructor
     *
     * @param id          class room's id
     * @param classNumber class room's number
     */
    public ClassRoom(Long id, String classNumber) {
        this.id = id;
        this.classNumber = classNumber;
    }

    /**
     * Constructor for creating using DAO
     *
     * @param classNumber class room's number
     */
    public ClassRoom(String classNumber) {
        this.classNumber = classNumber;
    }

    /**
     * This method is used to ORM property id
     * in column id at classrooms table
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
     * This method is used to ORM property classNumber
     * in column class_number at classrooms table
     */
    @Column(name = "class_number")
    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    /**
     * This method is used to cascade remove lessons in the removed class room
     */
    @OneToMany(mappedBy = "classRoom")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRoom)) return false;
        ClassRoom classRoom = (ClassRoom) o;
        return Objects.equals(getId(), classRoom.getId()) && getClassNumber().equals(classRoom.getClassNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClassNumber());
    }

    @Override
    public String toString() {
        return "ClassRoom{" + "id=" + id + ", classNumber='" + classNumber + '\'' + '}';
    }
}
