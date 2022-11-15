package Exceptions;

public class AccountNotFoundException extends Exception{
    
    public AccountNotFoundException(String message) {
        super (message);
    }
    
    public AccountNotFoundException() {
        this ("Invalid credentials, please try again.");
    }
}
