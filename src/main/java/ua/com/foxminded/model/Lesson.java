package ua.com.foxminded.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model class Lesson.
 * ORM - lessons
 */
@Entity
@Table(name = "lessons")
public class Lesson {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - name
     */
    private String name;
    /**
     * Property - teacher
     */
    private Teacher teacher;
    /**
     * Property - group
     */
    private Group group;
    /**
     * Property - date time
     */
    private LocalDateTime dateTime;
    /**
     * Property - class room
     */
    private ClassRoom classRoom;

    /**
     * Empty constructor
     */
    public Lesson() {
    }

    /**
     * Full Constructor
     *
     * @param id        lesson's id
     * @param name      lesson's name
     * @param teacher   lesson's teacher
     * @param dateTime  lesson's date time
     * @param classRoom lesson's class room
     */
    public Lesson(Long id, String name, Teacher teacher, Group group, LocalDateTime dateTime, ClassRoom classRoom) {

        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.group = group;
        this.dateTime = dateTime;
        this.classRoom = classRoom;
    }

    /**
     * Constructor for creating using DAO
     *
     * @param name      lesson's name
     * @param teacher   lesson's teacher
     * @param dateTime  lesson's date time
     * @param classRoom lesson's class room
     */
    public Lesson(String name, Teacher teacher, Group group, LocalDateTime dateTime, ClassRoom classRoom) {

        this.name = name;
        this.teacher = teacher;
        this.group = group;
        this.dateTime = dateTime;
        this.classRoom = classRoom;
    }

    /**
     * This method is used to ORM property id
     * in column id at lessons table
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
     * This method is used to ORM property name
     * in column name at lessons table
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is used to ORM for property teacher
     * in column teacher_id at lessons table.
     * Reference - depends on property id in Teacher class
     */
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * This method is used to ORM for property group
     * in column group_id at lessons table.
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

    /**
     * This method is used to ORM property dateTime
     * in column date_time at lessons table
     */
    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * This method is used to ORM for property classRoom
     * in column class_room_id at lessons table.
     * Reference - depends on property id in ClassRoom class
     */
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JoinColumn(name = "class_room_id")
    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson)) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(getId(), lesson.getId()) && Objects.equals(getName(), lesson.getName()) && Objects.equals(getTeacher(), lesson.getTeacher()) && Objects.equals(getGroup(), lesson.getGroup()) && Objects.equals(getDateTime(), lesson.getDateTime()) && Objects.equals(getClassRoom(), lesson.getClassRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTeacher(), getGroup(), getDateTime(), getClassRoom());
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", teacher=" + teacher +
            ", group=" + group +
            ", dateTime=" + dateTime +
            ", classRoom=" + classRoom +
            '}';
    }
}
