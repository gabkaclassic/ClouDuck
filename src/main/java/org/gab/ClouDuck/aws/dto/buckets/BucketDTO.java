package org.gab.ClouDuck.aws.dto.buckets;

import com.amazonaws.services.s3.model.Bucket;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gab.ClouDuck.aws.dto.serializers.BucketDTOSerializer;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = BucketDTOSerializer.class)
public class BucketDTO {
    private String name;
    private BucketOwnerDTO owner;
    private Date creationDate;

    public BucketDTO(Bucket bucket) {

        setName(bucket.getName());
        setOwner(new BucketOwnerDTO(bucket.getOwner()));
        setCreationDate(bucket.getCreationDate());
    }
}
