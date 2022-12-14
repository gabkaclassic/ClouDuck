package org.gab.ClouDuck.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String key) {
        super(String.format("User with key ( %s ) is not found", key));
    }
    
    public String getMessage() {
        
        return "User with this key is not found";
    }
}
