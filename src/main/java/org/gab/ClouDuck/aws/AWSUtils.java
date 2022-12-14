package org.gab.ClouDuck.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import lombok.NoArgsConstructor;
import org.gab.ClouDuck.exceptions.ExpiredKeyException;
import org.gab.ClouDuck.exceptions.UserNotFoundException;
import org.gab.ClouDuck.responses.RegistrationResponse;
import org.gab.ClouDuck.responses.Response;
import org.gab.ClouDuck.users.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@NoArgsConstructor
public class AWSUtils {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private AmazonS3 client;
    private Bucket bucket;
    private UserService userService;
    
    public AWSUtils(String accessKey,
                    String secretKey,
                    String bucketName, UserService userService) {
    
        this(accessKey, secretKey, bucketName);
        this.userService = userService;
    }
    
    public AWSUtils(String accessKey,
                    String secretKey,
                    String bucketName) {
        
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        
        client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_1)
                .build();
        
        if(!client.doesBucketExistV2(this.bucketName))
            client.createBucket(this.bucketName);
        
        this.bucket = client.listBuckets().stream().filter(b -> b.getName().equals(bucketName)).findAny().orElseThrow();
    }
    
    public AWSUtils(String accessKey,
                    String secretKey,
                    String bucketName,
                    String region, UserService userService) {
        
        this(accessKey, secretKey, bucketName, region);
        this.userService = userService;
    }
    
    public AWSUtils(String accessKey,
                    String secretKey,
                    String bucketName,
                    String region) {
        
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        
        client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(region))
                .build();
        
        if(!client.doesBucketExistV2(this.bucketName))
            client.createBucket(this.bucketName);
        
        this.bucket = client.listBuckets().stream().filter(b -> b.getName().equals(bucketName)).findAny().orElseThrow();
    }
    
    public Response save(String key,
                         String folder,
                         String filename,
                         MultipartFile file) {
    
        File file1;
        
        try {
            file1 = new File(System.getProperty("java.io.tmpdir")+"/"+filename);
            file.transferTo(file1);
    
            var result = client.putObject(bucketName, getFilename(key, folder, filename), file1);
            file1.deleteOnExit();
            
            return Response.success(result.getContentMd5());
        } catch (IOException | ExpiredKeyException | UserNotFoundException e) {
            return Response.error(e.getMessage());
        }
    
    
    }
    
    public Response save(String key,
                         String folder,
                         String filename,
                         byte[] file) {
        
        File file1;
        
        try {
            file1 = new File(System.getProperty("java.io.tmpdir")+"/"+filename);
            Files.write(file1.toPath(), file);
            var result = client.putObject(bucketName, getFilename(key, folder, filename), file1);
            
            file1.deleteOnExit();
            
            return Response.success(result.getContentMd5());
        } catch (IOException | ExpiredKeyException | UserNotFoundException e) {
            return Response.error(e.getMessage());
        }
    
        
    }
    
    public Response update(String key,
                          String folder,
                          String filename,
                          MultipartFile file) {
    
        delete(key, folder, filename);
    
        return save(key, folder, filename, file);
    }
    
    public Response update(String key,
                           String folder,
                           String filename,
                           byte[] file) {
        
        delete(key, folder, filename);
        
        return save(key, folder, filename, file);
    }
        public Response get(String key, String folder, String filename) {
    
            byte[] file;
            try {
            var result = client.getObject(bucketName,  getFilename(key, folder, filename));
    
                file = result.getObjectContent().readAllBytes();
            } catch (IOException | ExpiredKeyException | UserNotFoundException e) {
                return Response.error(e.getMessage());
            }
    
            return Response.success(file);
    }
    
    public Response delete(String key, String folder, String filename) {
        
        try {
            client.deleteObject(bucketName, getFilename(key, folder, filename));
        }
        catch (ExpiredKeyException | UserNotFoundException e) {
            return Response.error(e.getMessage());
        }
        return Response.success("Success");
    }
    
    private String getFilename(String key, String folder, String filename) throws ExpiredKeyException, UserNotFoundException {
        
        return userService.rootFolderByKey(key) + (((folder == null || folder.isEmpty()) ? "" : (folder + "/")) + filename);
    }
    
    public RegistrationResponse registration(String key) {
    
        return userService.registrationOrUpdateKey(key);
    }
}
