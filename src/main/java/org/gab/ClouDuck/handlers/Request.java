package org.gab.ClouDuck.handlers;

import lombok.Data;

@Data
public class Request {
    
    private String region;
    
    private String secretKey;
    
    private String accessKey;
    
    private String bucketName;
    
    private Operation operation;
    
    private String folder;
    
    private String filename;
    
    private byte[] file;
}
