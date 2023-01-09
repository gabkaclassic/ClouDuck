package org.gab.ClouDuck.requests;

import lombok.Data;

@Data
public class Request {
    
    private String region;
    
    private String key;
    
    private Operation operation;

    private String filename;
    
    private byte[] file;
}
