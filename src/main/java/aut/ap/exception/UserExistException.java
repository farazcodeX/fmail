package aut.ap.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(String massage) {
        super(massage);
    }
}
