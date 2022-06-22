package com.team03.issuetracker.oauth.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth")
public class AuthProperties {

    private final Map<String, VendorProperties> vendors;

    public VendorProperties getVendorProperties(String vendor) {
        return vendors.get(vendor);
    }
}
