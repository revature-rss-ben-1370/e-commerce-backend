package com.revature.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://2205java-sre-p3.s3-website-us-east-1.amazonaws.com/", allowCredentials = "true")
public class PodMonitoringController {
	
	static String hostname = System.getenv("HOSTNAME");
	
	@GetMapping("/hostname")
    public ResponseEntity<String> getHostname() {
        return ResponseEntity.ok(hostname);
    }
}
