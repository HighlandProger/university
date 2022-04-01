package ua.com.foxminded.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Model class Group.
 * ORM - groups
 */
@Entity
@Table(name = "groups")
public class Group {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - department
     */
    private Department department;
    /**
     * Property - course
     */
    private Course course;
    /**
     * Property - group number
     */
    private int groupNumber;
    /**
     * Property - students
     */
    private List<Student> students;
    /**
     * Property - lessons
     */
    private List<Lesson> lessons;

    /**
     * Empty constructor
     */
    public Group() {
    }

    /**
     * Full Constructor
     *
     * @param id          group's id
     * @param department  group's department
     * @param course      group's course
     * @param groupNumber group's group number
     */
    public Group(Long id, Department department, Course course, int groupNumber) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.groupNumber = groupNumber;
    }

    /**
     * Constructor for creating using DAO
     *
     * @param department  group's department
     * @param course      group's course
     * @param groupNumber group's group number
     */
    public Group(Department department, Course course, int groupNumber) {
        this.department = department;
        this.course = course;
        this.groupNumber = groupNumber;
    }

    /**
     * This method is used to ORM property id
     * in column id at groups table
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
     * This method is used to ORM for property department
     * in column department_id at groups table.
     * Reference - depends on property id in Department class
     */
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "department_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * This method is used to ORM for property course
     * in column course_id at groups table.
     * Reference - depends on property id in Course class
     */
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * This method is used to ORM property groupNumber
     * in column group_number at groups table
     */
    @Column(name = "group_number")
    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * This method is used to cascade remove students in the removed group
     */
    @OneToMany(mappedBy = "group")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * This method is used to cascade remove lessons in the removed group
     */
    @OneToMany(mappedBy = "group")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String buildAbbreviation() {
        return "" + department.getId() + course.getId() + groupNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return getGroupNumber() == group.getGroupNumber() && Objects.equals(getId(), group.getId()) && Objects.equals(getDepartment(), group.getDepartment()) && Objects.equals(getCourse(), group.getCourse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepartment(), getCourse(), getGroupNumber());
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + id +
            ", department=" + department +
            ", course=" + course +
            ", groupNumber=" + groupNumber +
            '}';
    }
}
