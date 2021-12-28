package ua.com.foxminded.domain;

public class Teacher extends Person {

    private Long id;
    private Long departmentId;

    public Teacher() {
    }

    public Teacher(Long id, Long departmentId, String firstName, String lastName, int age) {
        super(firstName, lastName, age);
        this.id = id;
        this.departmentId = departmentId;
    }

    public Teacher(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
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

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", departmentId=" + departmentId +
            ", " + super.toString() +
            '}';
    }
}
