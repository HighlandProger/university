package ua.com.foxminded.dto;

import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.AbstractService;

public class TeacherDTO {

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
     * Property - department id
     */
    private Long departmentId;

    /**
     * Empty constructor
     */
    public TeacherDTO() {
    }

    /**
     * Full constructor
     *
     * @param id           teacher's id
     * @param firstName    teacher's first name
     * @param lastName     teacher's last name
     * @param age          teacher's age
     * @param departmentId teacher's department id
     */
    public TeacherDTO(long id, String firstName, String lastName, int age, long departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.departmentId = departmentId;
    }

    /**
     * Constructor based on model Teacher
     *
     * @param teacher teacher with already defined properties
     */
    public TeacherDTO(Teacher teacher) {

        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.age = teacher.getAge();
        if (teacher.getDepartment() != null) {
            this.departmentId = teacher.getDepartment().getId();
        }
    }

    /**
     * Returns a Teacher object with defined properties.
     *
     * <p>Firstly creates a new Teacher object and sets its id, first name, last name, age properties.
     *
     * <p>DepartmentService searches for the department object by this department id property
     * to set it for the Teacher object.
     *
     * @param departmentService service for searching department by id in departments table
     * @return a Teacher object with defined properties
     * @see ua.com.foxminded.service.DepartmentService#getById(Long)
     */
    public Teacher getTeacher(AbstractService<Department> departmentService) {
        Teacher teacher = new Teacher();
        teacher.setId(this.id);
        teacher.setFirstName(this.firstName);
        teacher.setLastName(this.lastName);
        teacher.setAge(this.age);
        teacher.setDepartment(departmentService.getById(this.departmentId));

        return teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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
}
