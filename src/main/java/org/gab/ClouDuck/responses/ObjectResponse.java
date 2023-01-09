package org.gab.ClouDuck.responses;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.gab.ClouDuck.aws.dto.files.ObjectDTO;
import org.gab.ClouDuck.aws.dto.files.ObjectSummaryDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectResponse extends Response {
    private ObjectDTO object;
    private List<ObjectSummaryDTO> objects;
    private boolean exists;
    private ObjectResponse(String status, String message) {
        super(status, message);
    }
    private ObjectResponse(String status, String message, S3Object object) throws IOException {
        super(status, message);
        this.object = new ObjectDTO(object);
    }

    public ObjectResponse(String status, String message, boolean exists) {
        super(status, message);
        this.exists = exists;
    }

    public ObjectResponse(String status, String message, List<S3ObjectSummary> objects) throws IOException {
        super(status, message);
        setObjects(objects);
    }

    public static ObjectResponse success() throws IOException {

        return success(new ArrayList<>());
    }

    public static ObjectResponse success(S3Object object) throws IOException {

        return new ObjectResponse(SUCCESS_STATUS, "Successful operation with object", object);
    }

    public static ObjectResponse success(List<S3ObjectSummary> objects) throws IOException {

        return new ObjectResponse(SUCCESS_STATUS, "Successful operation with object", objects);
    }
    public static ObjectResponse success(boolean exists) {

        return new ObjectResponse(SUCCESS_STATUS, "Successful operation with object", exists);
    }
    public static ObjectResponse error(Exception exception) {

        return new ObjectResponse(ERROR_STATUS, String.format(ERROR_MESSAGE, exception.getMessage()));
    }

    public ObjectDTO getObject() {
        return object;
    }

    public void setObject(ObjectDTO object) {
        this.object = object;
    }

    public List<ObjectSummaryDTO> getObjects() {
        return objects;
    }

    public void setObjects(List<S3ObjectSummary> objects) throws IOException {
        List<ObjectSummaryDTO> list = new ArrayList<>();
        for (S3ObjectSummary s3Object : objects) {
            var objectDTO = new ObjectSummaryDTO(s3Object);
            list.add(objectDTO);
        }
        this.objects = list;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
