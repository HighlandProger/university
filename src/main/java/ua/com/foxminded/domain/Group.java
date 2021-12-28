package ua.com.foxminded.domain;

public class Group {

    private Long id;
    private Long departmentId;
    private Long courseId;
    private int groupNumber;

    public Group(){
    }

    public Group(Long departmentId, Long courseId, int groupNumber){
        this.id = Long.parseLong("" + departmentId + courseId + groupNumber);
        this.departmentId = departmentId;
        this.courseId = courseId;
        this.groupNumber = groupNumber;
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
