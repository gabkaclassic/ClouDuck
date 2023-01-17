package org.gab.ClouDuck.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gab.ClouDuck.controllers.SecurityData;
import org.gab.ClouDuck.crypt.Cryptographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class UtilsConfiguration {
    @Bean
    public ObjectMapper mapper() {

        return new ObjectMapper();
    }

    @Bean
    @Scope(scopeName = "singleton")
    public SecurityData data(@Autowired Cryptographer cryptographer, @Value("${secret_key}") String secret) {
        return new SecurityData(cryptographer, secret);
    }
}
