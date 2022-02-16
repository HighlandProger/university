package ua.com.foxminded.dto;

import ua.com.foxminded.model.Group;

public class GroupDTO {

    private Long id;
    private String abbreviation;
    private String departmentName;
    private int courseEstablishYear;
    private int groupNumber;

    public GroupDTO(Group group, String departmentName, int courseEstablishYear) {

        this.id = group.getId();
        this.abbreviation = group.getAbbreviation();
        this.groupNumber = group.getGroupNumber();
        this.departmentName = departmentName;
        this.courseEstablishYear = courseEstablishYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getCourseEstablishYear() {
        return courseEstablishYear;
    }

    public void setCourseEstablishYear(int courseEstablishYear) {
        this.courseEstablishYear = courseEstablishYear;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
