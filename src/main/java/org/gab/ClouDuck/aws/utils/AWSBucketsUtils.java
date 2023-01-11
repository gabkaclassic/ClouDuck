package org.gab.ClouDuck.aws.utils;

import org.gab.ClouDuck.responses.BucketResponse;

public class AWSBucketsUtils extends AWSResourcesUtils {

    public AWSBucketsUtils(byte[] accessKey, byte[] secretKey, String region) {
        super(accessKey, secretKey, region);
    }

    public BucketResponse create(String bucketName) {

        client.createBucket(bucketName);
        return BucketResponse.success();
    }
    public BucketResponse list() {

        return BucketResponse.success(client.listBuckets());
    }

    public BucketResponse delete(String bucketName) {

        client.deleteBucket(bucketName);
        return BucketResponse.success();
    }

    public BucketResponse exists(String bucketName) {

        return BucketResponse.success(client.doesBucketExistV2(bucketName));
    }
}
