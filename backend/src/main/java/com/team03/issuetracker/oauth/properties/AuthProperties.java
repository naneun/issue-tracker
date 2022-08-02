package com.team03.issuetracker.oauth.properties;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

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
