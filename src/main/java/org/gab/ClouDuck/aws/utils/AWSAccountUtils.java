package org.gab.ClouDuck.aws.utils;

import org.gab.ClouDuck.crypt.Cryptographer;
import org.gab.ClouDuck.responses.Response;
import org.gab.ClouDuck.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.NoSuchElementException;

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

    public static AWSObjectsUtils objectLogin(String id) throws NoSuchElementException {

        var user = userService.findById(id);

        return new AWSObjectsUtils(
                cryptographer.decrypt(user.getAccessKey()),
                cryptographer.decrypt(user.getSecretKey()),
                user.getRegion().getName()
        );
    }
    public static Response registration(String id, String secretKey, String awsAccessKey, String region) throws IOException {

        region = (region == null) ? defaultRegion : region;
        userService.createUser(
                id,
                cryptographer.encrypt(awsAccessKey.getBytes()),
                cryptographer.encrypt(secretKey.getBytes()),
                region
        );
        return Response.success();
    }
    public static AWSBucketsUtils bucketLogin(String id) throws NoSuchElementException {

        var user = userService.findById(id);

        return new AWSBucketsUtils(
                cryptographer.decrypt(user.getAccessKey()),
                cryptographer.decrypt(user.getSecretKey()),
                user.getRegion().getName()
        );
    }
    public static Response deleteAccount(String id) throws IOException {

        userService.delete(id);
        return Response.success();
    }

    @CacheEvict(value = {"login"})
    public static Response logout(String id) throws IOException {

        return Response.success();
    }
}