package org.gab.ClouDuck.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.gab.ClouDuck.handlers.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AWSUtils {
    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private AmazonS3 client;
    private Bucket bucket;
    
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
    
    public Response save(String folder,
                         String filename,
                         MultipartFile file) {
    
        File file1;
        
        try {
            file1 = new File(System.getProperty("java.io.tmpdir")+"/"+filename);
            file.transferTo(file1);
        } catch (IOException e) {
            return Response.error(e.getMessage());
        }
        
        var result = client.putObject(bucketName, getFilename(folder, filename), file1);
        file1.deleteOnExit();
    
        return Response.success(result.getContentMd5());
    }
    
    public Response save(String folder,
                         String filename,
                         byte[] file) {
        
        File file1;
        
        try {
            file1 = new File(System.getProperty("java.io.tmpdir")+"/"+filename);
            Files.write(file1.toPath(), file);
        } catch (IOException e) {
            return Response.error(e.getMessage());
        }
        
        var result = client.putObject(bucketName, getFilename(folder, filename), file1);
        file1.deleteOnExit();
        
        return Response.success(result.getContentMd5());
    }
    
    public Response update(String folder,
                          String filename,
                          MultipartFile file) {
    
        delete(folder, filename);
    
        return save(folder, filename, file);
    }
    
    public Response update(String folder,
                           String filename,
                           byte[] file) {
        
        delete(folder, filename);
        
        return save(folder, filename, file);
    }
        public Response get(String folder,
                            String filename) {
        
        
        var result = client.getObject(bucketName,  getFilename(folder, filename));
    
        byte[] file;
        try {
            file = result.getObjectContent().readAllBytes();
        } catch (IOException e) {
            return Response.error(e.getMessage());
        }
    
        return Response.success(file);
    }
    
    public Response delete(String folder, String filename) {
        
        client.deleteObject(bucketName, getFilename(folder, filename));
        
        return Response.success("Success");
    }
    
    private String getFilename(String folder, String filename) {
        
        return ((folder == null || folder.isEmpty()) ? "" : (folder + "/")) + filename;
    }
}
