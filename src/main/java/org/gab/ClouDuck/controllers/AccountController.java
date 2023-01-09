package org.gab.ClouDuck.controllers;

import org.gab.ClouDuck.aws.utils.AWSAccountUtils;
import org.gab.ClouDuck.responses.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {
    @GetMapping("/registration")
    public Response registration(@RequestParam String id,
                                 @RequestParam String accessKey,
                                 @RequestParam String secretKey,
                                 @RequestParam(required = false) String region) {

        return AWSAccountUtils.registration(id, accessKey, secretKey, region);
    }
}