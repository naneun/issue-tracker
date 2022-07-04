package com.team03.issuetracker.common.api;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PortController {

	private final Environment env;

	@GetMapping("/port")
	public String profile() {
		return env.getProperty("PORT");
	}

}
