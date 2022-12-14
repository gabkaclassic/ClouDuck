package org.gab.ClouDuck.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.gab.ClouDuck.aws.AWSUtils;
import org.gab.ClouDuck.handlers.Handler;
import org.gab.ClouDuck.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfiguration {
    
    @Value("${aws.access_key}")
    private String accessKey;
    @Value("${aws.secret_key}")
    private String secretKey;
    @Value("${aws.bucket_name}")
    private String bucketName;
    
    @Value("${aws.region}")
    private String region;
    
    private final UserService userService;
    
    @Autowired
    public UtilConfiguration(UserService userService) {
    
        this.userService = userService;
    }
    
    @Bean
    public AWSUtils awsUtils() {
        return new AWSUtils(accessKey, secretKey, bucketName, region, userService);
    }
    
    @Bean
    public Handler handler() {
        return new Handler(awsUtils());
    }
    
    
    @Bean
    public ObjectMapper mapper() {
        
        return new ObjectMapper();
    }
    
    @Bean
    public ObjectWriter writer() {
        
        return mapper().writer().withDefaultPrettyPrinter();
    }
}
