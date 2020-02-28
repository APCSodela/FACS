package com.pup.cea.facs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/dynamic")
@Controller
public class DynamicController {
	@RequestMapping("/table")
	public String Table() {
		return "dynamic/dyTable";
	}
	@RequestMapping("/submit")
	public String View(Model model,HttpServletRequest req) {
		return "dynamic/view";
	}

}
