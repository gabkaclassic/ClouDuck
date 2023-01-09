package org.gab.ClouDuck.aws.dto.files;

import com.amazonaws.services.s3.model.S3Object;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectDTO {
    private String name;

    private byte[] content;

    private Map<String, Object> metadata;

    public ObjectDTO(S3Object object) throws IOException {

        setName(object.getKey());
        setContent(object.getObjectContent().readAllBytes());
        setMetadata(object.getObjectMetadata().getRawMetadata());
    }
}
