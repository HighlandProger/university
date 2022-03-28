package ua.com.foxminded.dto;

import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.AbstractService;

/**
 * DTO class LessonDTO.
 */
public class StudentDTO {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - first name
     */
    private String firstName;
    /**
     * Property - last name
     */
    private String lastName;
    /**
     * Property - age
     */
    private int age;
    /**
     * Property - group id
     */
    private Long groupId;

    /**
     * Empty constructor
     */
    public StudentDTO() {
    }

    /**
     * Full constructor
     *
     * @param id        student's id
     * @param firstName student's first name
     * @param lastName  student's last name
     * @param age       student's age
     * @param groupId   student's group id
     */
    public StudentDTO(Long id, String firstName, String lastName, int age, Long groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.groupId = groupId;
    }

    /**
     * Constructor based on model Student
     *
     * @param student student with already defined properties
     */
    public StudentDTO(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.age = student.getAge();
        if (student.getGroup() != null) {
            this.groupId = student.getGroup().getId();
        }
    }

    /**
     * Returns a Student object with defined properties.
     *
     * <p>Firstly creates a new Student object and sets its id, first name, last name, age properties.
     *
     * <p>GroupService searches for the group object by this groupId property
     * to set it for the Student object.
     *
     * @param groupService service for searching group by id in groups table
     * @return a Student object with defined properties
     * @see ua.com.foxminded.service.GroupService#getById(Long)
     */
    public Student getStudent(AbstractService<Group> groupService) {

        Student student = new Student();
        student.setId(this.id);
        student.setFirstName(this.firstName);
        student.setLastName(this.lastName);
        student.setAge(this.age);
        student.setGroup(groupService.getById(this.groupId));

        return student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
