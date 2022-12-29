package org.gab.ClouDuck.controllers;

import org.gab.ClouDuck.aws.AWSUtils;
import org.gab.ClouDuck.responses.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class UserController {
    
    private final AWSUtils awsUtils;
    
    @Autowired
    public UserController(AWSUtils awsUtils) {
        
        this.awsUtils = awsUtils;
    }
    
    @GetMapping(value = {"/update", "/registry"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegistrationResponse getFile(@RequestParam(required = false) String key) {
        
        return awsUtils.registration(key);
    }
}