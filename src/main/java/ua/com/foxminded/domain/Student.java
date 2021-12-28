package ua.com.foxminded.domain;

public class Student extends Person {

    private Long id;
    private Long groupId;

    public Student() {
    }

    public Student(Long id, Long groupId, String firstName, String lastName, int age) {
        super(firstName, lastName, age);
        this.id = id;
        this.groupId = groupId;
    }

    public Student(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
        return "Student{" +
            "id=" + id +
            ", groupId=" + groupId +
            ", " + super.toString() +
            '}';
    }
}
