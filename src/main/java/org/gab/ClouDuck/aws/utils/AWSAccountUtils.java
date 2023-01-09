package org.gab.ClouDuck.aws.utils;

import org.gab.ClouDuck.crypt.Cryptographer;
import org.gab.ClouDuck.responses.Response;
import org.gab.ClouDuck.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AWSAccountUtils {

    private static String defaultRegion;

    private static UserService userService;

    private static Cryptographer cryptographer;

    @Autowired
    public void setUserService(UserService userService) {
        AWSAccountUtils.userService = userService;
    }

    @Autowired
    public void setCryptographer(Cryptographer cryptographer) {
        AWSAccountUtils.cryptographer = cryptographer;
    }

    @Value("${aws.default_region}")
    public void setDefaultRegion(String defaultRegion) {
        AWSAccountUtils.defaultRegion = defaultRegion;
    }

    public static Response registration(String id, String accessKey, String secretKey, String region) {

        region = (region == null) ? defaultRegion : region;

        try {
            userService.createUser(
                    id,
                    cryptographer.encrypt(accessKey.getBytes()),
                    cryptographer.encrypt(secretKey.getBytes()),
                    region
            );
            return Response.success();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.error(e);
        }
    }
    public static AWSObjectsUtils objectLogin(String id) {

        var user = userService.findById(id);

        return new AWSObjectsUtils(
                cryptographer.decrypt(user.getAccessKey()),
                cryptographer.decrypt(user.getSecretKey()),
                user.getRegion().getName()
        );
    }

    public static AWSBucketsUtils bucketLogin(String id) {

        var user = userService.findById(id);

        return new AWSBucketsUtils(
                cryptographer.decrypt(user.getAccessKey()),
                cryptographer.decrypt(user.getSecretKey()),
                user.getRegion().getName()
        );
    }
    public static Response deleteAccount(String id) {

        try {
            userService.delete(id);
            return Response.success();
        } catch (Exception e) {
            return Response.error(e);
        }
    }
}