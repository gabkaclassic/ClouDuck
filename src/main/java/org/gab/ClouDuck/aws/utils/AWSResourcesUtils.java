package org.gab.ClouDuck.aws.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public abstract class AWSResourcesUtils {

    protected String accessKey;
    protected String secretKey;
    protected AmazonS3 client;

    protected AWSResourcesUtils(byte[] accessKey,
                                byte[] secretKey,
                                String region) {

        this.accessKey = new String(accessKey);
        this.secretKey = new String(secretKey);

        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(region))
                .build();
    }
}
