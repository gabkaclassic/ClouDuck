package org.gab.ClouDuck.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "error";
    private String status;
    
    private String message;
    
    private byte[] file;

    public Response(String status) {
        this.status = status;
    }
    
    public Response(String status, String message) {
        this.status = status;
    }
    
    public Response(String status, byte[] file) {
        this.status = status;
        this.file = file;
    }
    
    public static Response success(String message) {
        
        return new Response(SUCCESS_STATUS, message);
    }
    
    public static Response success(byte[] file) {
        
        return new Response(SUCCESS_STATUS, file);
    }
    
    public static Response error(String message) {
        
        return new Response(ERROR_STATUS, message);
    }
}
