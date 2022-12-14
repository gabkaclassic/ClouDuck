package org.gab.ClouDuck.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.ClouDuck.users.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private static final String SUCCESS_STATUS = "Success";
    private static final String ERROR_STATUS = "Error";
    private static final String SUCCESS_MESSAGE = "Success process of key registration, it will expire ( %s )";
    private static final String ERROR_MESSAGE = "Error process of key registration: ( %s )";
    
    private String key;
    
    private String status;
    
    private String message;
    
    public static RegistrationResponse success(User user) {
        
        return new RegistrationResponse(user.getKey(), SUCCESS_STATUS, String.format(SUCCESS_MESSAGE, user.getExpire().toString()));
    }
    
    public static RegistrationResponse error(Exception exception) {
        
        return new RegistrationResponse(null, ERROR_STATUS, String.format(ERROR_MESSAGE, exception.getMessage()));
    }
}
