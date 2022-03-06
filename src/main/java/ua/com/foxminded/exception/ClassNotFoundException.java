package ua.com.foxminded.exception;

public class ClassNotFoundException extends RuntimeException{

    public ClassNotFoundException(String message){
        super(message);
    }
}
