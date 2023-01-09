package org.gab.ClouDuck.configurations;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AWSClientService {

    private static Map<String, AWSSecurityTokenService> tokenServicesList;

    static {
        tokenServicesList = new HashMap<>();
        for(var region: Regions.values())
            tokenServicesList.put(
                    region.getName(),
                    AWSSecurityTokenServiceClientBuilder
                            .standard()
                            .withEndpointConfiguration(
                                    new AwsClientBuilder.EndpointConfiguration("sts-endpoint.amazonaws.com", region.getName())
                            ).build()
            );
    }
    public AWSSecurityTokenService temporaryTokenService(String region) {
        return tokenServicesList.get(region);
    }
}
