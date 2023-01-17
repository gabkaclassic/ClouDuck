package org.gab.ClouDuck.controllers.aws;

import org.gab.ClouDuck.aws.utils.AWSAccountUtils;
import org.gab.ClouDuck.controllers.SecurityData;
import org.gab.ClouDuck.controllers.exceptions.annotations.BasicResponseExceptionHandler;
import org.gab.ClouDuck.exceptions.InvalidSecretKeyException;
import org.gab.ClouDuck.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("account")
@BasicResponseExceptionHandler
public class AccountController {

    private final SecurityData data;

    @Autowired
    public AccountController(SecurityData data) {
        this.data = data;
    }

    @GetMapping("/registration")
    public Response registration(@RequestParam String id,
                                 @RequestParam String secretKey,
                                 @RequestParam String awsAccessKey,
                                 @RequestParam String awsSecretKey,
                                 @RequestParam(required = false) String region) throws IOException, InvalidSecretKeyException {

        if(!data.checkSecretKey(secretKey))
            throw new InvalidSecretKeyException();

        return AWSAccountUtils.registration(id, awsAccessKey, awsSecretKey, region);
    }

    @GetMapping("/logout")
    public Response logout(@RequestParam String id, @RequestParam String secretKey) throws IOException, InvalidSecretKeyException {

        if(!data.checkSecretKey(secretKey))
            throw new InvalidSecretKeyException();

        return AWSAccountUtils.logout(id);
    }
}