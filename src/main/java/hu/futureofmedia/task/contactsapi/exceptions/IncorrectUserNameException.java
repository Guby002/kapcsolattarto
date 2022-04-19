package hu.futureofmedia.task.contactsapi.exceptions;

public class IncorrectUserNameException extends Exception {
    public IncorrectUserNameException(String errorMessage) {
        super(errorMessage);
    }
}