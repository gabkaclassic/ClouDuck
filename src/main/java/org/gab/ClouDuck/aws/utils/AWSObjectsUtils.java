package org.gab.ClouDuck.aws.utils;

import org.gab.ClouDuck.responses.ObjectResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AWSObjectsUtils extends AWSResourcesUtils{
    public AWSObjectsUtils(byte[] accessKey, byte[] secretKey, String region) {
        super(accessKey, secretKey, region);
    }

    public ObjectResponse save(String bucketName,
                               String filename,
                               MultipartFile file) throws IOException {
    
        return save(bucketName, filename, file.getBytes());
    }
    
    public ObjectResponse save(String bucketName,
                               String filename,
                               byte[] file) {
        
        File file1;
        
        try {
            file1 = new File(System.getProperty("java.io.tmpdir")+"/"+filename);
            Files.write(file1.toPath(), file);
            client.putObject(bucketName, filename, file1);
            
            file1.deleteOnExit();
            
            return ObjectResponse.success();
        } catch (IOException e) {
            e.printStackTrace();
            return ObjectResponse.error(e);
        }
    }

    public ObjectResponse get(String bucketName, String filename) {

        try {
            return ObjectResponse.success(client.getObject(bucketName, filename));
        } catch (IOException e) {
            e.printStackTrace();
            return ObjectResponse.error(e);
        }
    }
    
    public ObjectResponse delete(String bucketName, String filename) throws IOException {

        try {
            client.deleteObject(bucketName, filename);

        }
        catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.error(e);
        }

        return ObjectResponse.success();
    }

    public ObjectResponse exists(String bucketName, String filename) {

        try {
            return ObjectResponse.success(client.doesObjectExist(bucketName, filename));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.error(e);
        }
    }

    public ObjectResponse list(String bucketName) {

        try {
            return ObjectResponse.success(client.listObjects(bucketName).getObjectSummaries());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.error(e);
        }
    }

    public ObjectResponse copy(String oldBucketName, String oldObjectName, String newBucketName, String newObjectName) {

        try {
            client.copyObject(oldBucketName, oldObjectName, newBucketName, newObjectName);
            return ObjectResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ObjectResponse.error(e);
        }
    }
}
