package org.gab.ClouDuck.controllers.aws;

import org.gab.ClouDuck.aws.utils.AWSObjectsUtils;
import org.gab.ClouDuck.aws.auth.AWSSetup;
import org.gab.ClouDuck.responses.ObjectResponse;
import org.gab.ClouDuck.responses.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("objects")
public class ObjectOperationsController {
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectResponse getFile(@AWSSetup AWSObjectsUtils aws,
                            @RequestParam(required = false) String bucket,
                            @RequestParam String filename) throws IOException {
        
        return aws.get(bucket, filename);
    }
    
    @PutMapping(value = "/put", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectResponse putFile(@AWSSetup AWSObjectsUtils aws,
                             @RequestParam(required = false) String bucket,
                             @RequestParam String filename,
                             @RequestParam MultipartFile file) throws IOException {
        
        return aws.save(bucket, filename, file);
    }
    
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectResponse deleteFile(@AWSSetup AWSObjectsUtils aws,
                               @RequestParam(required = false) String bucket,
                               @RequestParam String filename) throws IOException {
        
        return aws.delete(bucket, filename);
    }

    @PutMapping(value = "/copy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectResponse copy(@AWSSetup AWSObjectsUtils aws,
                               @RequestParam String oldBucketName,
                               @RequestParam String oldObjectName,
                               @RequestParam String newBucketName,
                               @RequestParam String newObjectName) throws IOException {

        return aws.copy(oldBucketName, oldObjectName, newBucketName, newObjectName);
    }
}
