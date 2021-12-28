package ua.com.foxminded.domain;

public class Department {

    private Long id;
    private String name;

    public Department(){
    }

    public Department(String name){
        this.name = name;
    }

    public Department(long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
