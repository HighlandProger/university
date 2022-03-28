package ua.com.foxminded.dto;

import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.AbstractService;

/**
 * DTO class GroupDTO.
 */
public class GroupDTO {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - department id
     */
    private Long departmentId;
    /**
     * Property - course id
     */
    private Long courseId;
    /**
     * Property - group number
     */
    private int groupNumber;

    /**
     * Empty constructor
     */
    public GroupDTO() {
    }

    /**
     * Constructor based on model Group
     *
     * @param group group with already defined properties
     */
    public GroupDTO(Group group) {
        this.id = group.getId();

        if (group.getDepartment() != null) {
            this.departmentId = group.getDepartment().getId();
        }

        if (group.getCourse() != null) {
            this.courseId = group.getCourse().getId();
        }

        this.groupNumber = group.getGroupNumber();
    }

    /**
     * Returns a Group object with defined properties.
     *
     * <p>Firstly creates a new Group object and sets its id and groupNumber.
     *
     * <p>DepartmentService searches for department object by this depratmentId property
     * to set it for the Group object.
     *
     * <p>CourseService searches for course object by this courseId property
     * to set it for the Group object.
     *
     * @param departmentService service for searching department by id in departments table
     * @param courseService     service for searching course by id in courses table
     * @return a Group object with defined properties
     * @see ua.com.foxminded.service.DepartmentService#getById(Long)
     * @see ua.com.foxminded.service.CourseService#getById(Long)
     */
    public Group getGroup(AbstractService<Department> departmentService,
                          AbstractService<Course> courseService) {

        Group group = new Group();
        group.setId(this.id);
        group.setGroupNumber(this.groupNumber);
        group.setDepartment(departmentService.getById(this.departmentId));
        group.setCourse(courseService.getById(this.courseId));

        return group;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
