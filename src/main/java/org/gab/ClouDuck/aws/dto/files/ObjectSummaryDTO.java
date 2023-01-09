package org.gab.ClouDuck.aws.dto.files;

import com.amazonaws.services.s3.model.Owner;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.Data;
import org.gab.ClouDuck.aws.dto.buckets.BucketOwnerDTO;

@Data
public class ObjectSummaryDTO {
    private BucketOwnerDTO owner;
    private String name;
    private String eTag;
    private long size;


    public ObjectSummaryDTO(S3ObjectSummary object) {

        setSize(object.getSize());
        setName(object.getKey());
        setOwner(object.getOwner());
        setETag(object.getETag());
    }

    public void setOwner(Owner owner) {
        this.owner = new BucketOwnerDTO(owner);
    }
}
