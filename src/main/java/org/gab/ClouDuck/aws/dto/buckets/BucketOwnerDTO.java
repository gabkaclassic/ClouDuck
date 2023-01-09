package org.gab.ClouDuck.aws.dto.buckets;

import com.amazonaws.services.s3.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketOwnerDTO {

    private String displayName;

    private String ID;

    public BucketOwnerDTO(Owner owner) {

        setDisplayName(owner.getDisplayName());
        setID(owner.getId());
    }

}
