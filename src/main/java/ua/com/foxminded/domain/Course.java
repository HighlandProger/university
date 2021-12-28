package ua.com.foxminded.domain;

public class Course {

    private Long id;
    private int establishYear;

    public Course(){
    }

    public Course(int establishYear){
        this.establishYear = establishYear;
        this.id = establishYear%10L;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEstablishYear() {
        return establishYear;
    }

    public void setEstablishYear(int establishYear) {
        this.establishYear = establishYear;
    }
}
