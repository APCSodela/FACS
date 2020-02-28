package com.pup.cea.facs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@Controller
public class TestController {
	
	@RequestMapping("faccard")
	public String profileCard() {
		return "test/facultyCard";
	}
	@RequestMapping("facprofile")
	public String facProfile() {
		return "test/facultyProfile";
	}


}
