package org.gab.ClouDuck.aws.utils;

import org.gab.ClouDuck.responses.BucketResponse;

public class AWSBucketsUtils extends AWSResourcesUtils {

    public AWSBucketsUtils(byte[] accessKey, byte[] secretKey, String region) {
        super(accessKey, secretKey, region);
    }

    public BucketResponse create(String bucketName) {

        try {
            client.createBucket(bucketName);
            return BucketResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            return BucketResponse.error(e);
        }
    }
    public BucketResponse list() {

        try {
            return BucketResponse.success(client.listBuckets());
        }
        catch (Exception e) {
            e.printStackTrace();
            return BucketResponse.error(e);
        }
    }

    public BucketResponse delete(String bucketName) {

        try {
            client.deleteBucket(bucketName);
            return BucketResponse.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            return BucketResponse.error(e);
        }
    }

    public BucketResponse exists(String bucketName) {

        try {
            return BucketResponse.success(client.doesBucketExistV2(bucketName));
        }
        catch (Exception e) {
            return BucketResponse.error(e);
        }
    }
}
