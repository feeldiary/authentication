package com.hanwhalife.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}
	
	@RequestMapping({ "/hello2" })
	public String greetPage() {
		return "Hello World";
	}

}