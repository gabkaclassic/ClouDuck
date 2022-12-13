package org.gab.ClouDuck.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.gab.ClouDuck.aws.AWSUtils;
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
    
    @Bean
    public AWSUtils awsUtils() {
        return new AWSUtils(accessKey, secretKey, bucketName);
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
