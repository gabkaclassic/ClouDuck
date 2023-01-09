package org.gab.ClouDuck.controllers;

import org.gab.ClouDuck.aws.utils.AWSBucketsUtils;
import org.gab.ClouDuck.aws.auth.AWSSetup;
import org.gab.ClouDuck.responses.BucketResponse;
import org.gab.ClouDuck.responses.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buckets")
public class BucketOperationController {

    @PutMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response create(@AWSSetup AWSBucketsUtils aws,
                                 @RequestParam String bucketName) {

        return aws.create(bucketName);
    }

    @GetMapping(value = "/list")
    public BucketResponse list(@AWSSetup AWSBucketsUtils aws) {

        return aws.list();
    }

    @DeleteMapping(value = "/delete")
    public BucketResponse delete(@AWSSetup AWSBucketsUtils aws, @RequestParam String bucketName) {

        return aws.delete(bucketName);
    }

    @GetMapping(value = "/exists")
    public BucketResponse exists(@AWSSetup AWSBucketsUtils aws, @RequestParam String bucketName) {

        return aws.exists(bucketName);
    }
}
