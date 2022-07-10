package com.fse.eauction.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackContoller {
	
	@RequestMapping("/message")
	public String test() {
		return "Entered fallback. No service instances are available to process your request. Please try after some time.";
	}

}
