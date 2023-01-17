package org.gab.ClouDuck.controllers.security;

import org.gab.ClouDuck.controllers.SecurityData;
import org.gab.ClouDuck.controllers.exceptions.annotations.BasicResponseExceptionHandler;
import org.gab.ClouDuck.exceptions.InvalidSecretKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("security")
@BasicResponseExceptionHandler
public class KeyManagementController {

    private final SecurityData data;

    @Autowired
    public KeyManagementController(SecurityData data) {
        this.data = data;
    }

    @PostMapping("/interactionKey")
    @ResponseStatus(HttpStatus.OK)
    public void setKey(@RequestParam("secretKey") String secret, @RequestParam("key") String key) throws InvalidSecretKeyException {

        data.setInteractionKey(secret, key);
    }
}
