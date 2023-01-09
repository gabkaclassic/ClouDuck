package org.gab.ClouDuck.aws.dto.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.gab.ClouDuck.aws.dto.buckets.BucketDTO;

import java.io.IOException;

public class BucketDTOSerializer extends StdSerializer<BucketDTO> {

    public BucketDTOSerializer() {
        this(null);
    }

    public BucketDTOSerializer(Class<BucketDTO> t) {
        super(t);
    }

    public void serialize(
            BucketDTO value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("name", value.getName());
        jgen.writeObjectField("owner", value.getOwner());
        jgen.writeStringField("creation date", value.getCreationDate().toString());
        jgen.writeEndObject();
    }
}
