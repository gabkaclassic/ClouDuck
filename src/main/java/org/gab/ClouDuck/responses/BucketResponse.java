package org.gab.ClouDuck.responses;

import org.gab.ClouDuck.aws.dto.buckets.BucketDTO;

import com.amazonaws.services.s3.model.Bucket;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BucketResponse extends Response {

    private List<BucketDTO> buckets = new ArrayList<>();

    private BucketDTO bucket;

    private boolean exists;

    private BucketResponse(String status, String message) {
        super(status, message);
    }

    private BucketResponse(String status, String message, List<Bucket> buckets) {
        super(status, message);
        setBuckets(buckets);
    }

    private BucketResponse(String status, String message, Bucket buckets) {
        super(status, message);
        setBucket(bucket);
    }

    private BucketResponse(String status, String message, boolean exists) {
        super(status, message);
        setExists(exists);
    }
    public static BucketResponse success(List<Bucket> buckets) {

        return new BucketResponse(SUCCESS_STATUS, "Successful operation with bucket", buckets);
    }

    public static BucketResponse success(Bucket bucket) {

        return new BucketResponse(SUCCESS_STATUS, "Successful operation with bucket", bucket);
    }
    public static BucketResponse success(boolean exists) {

        return new BucketResponse(SUCCESS_STATUS, "Successful operation with bucket", exists);
    }
    public static BucketResponse success() {

        return success(new ArrayList<>());
    }

    public static BucketResponse error(Exception exception) {

        return new BucketResponse(ERROR_STATUS, String.format(ERROR_MESSAGE, exception.getMessage()));
    }
    public List<BucketDTO> getBuckets() {
        return buckets;
    }
    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets.stream().map(BucketDTO::new).collect(Collectors.toList());
    }

    public BucketDTO getBucket() {
        return bucket;
    }

    public void setBucket(BucketDTO bucket) {
        this.bucket = bucket;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
