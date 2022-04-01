package ua.com.foxminded.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Abstract class Person for Student and Teacher classes
 */
@MappedSuperclass
public abstract class Person {

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
     * Empty constructor
     */
    Person() {
    }

    /**
     * Full Constructor
     *
     * @param firstName person's first name
     * @param lastName  person's last name
     * @param age       person's age
     */
    Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * This method is used to ORM for property firstName
     * in column first_name at students and teachers tables
     */
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method is used to ORM for property lastName
     * in column last_name at students and teachers tables
     */
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method is used to ORM for property age
     * in column age at students and teachers tables
     */
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", age=" + age +
            '}';
    }
}
