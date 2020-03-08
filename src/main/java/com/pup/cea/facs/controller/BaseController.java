package com.pup.cea.facs.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pup.cea.facs.model.UserInfo;
import com.pup.cea.facs.service.AppUserService;

@Controller
public class BaseController {
	
	@Autowired
	AppUserService userService;
	
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	public UserInfo getUserInfo() {
		return userService.findByUsername(getAuth().getName());
	}
	
	@RequestMapping(value="/")
	public String index() {
		return "redirect:/login";
	}
	@RequestMapping(value="/login")
	public String showLoginPage() {
		return "login";
	}
	@RequestMapping(value="/login-error")
	public String showLoginErrorMessage(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
	@RequestMapping("/check-role")
	public String checkRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Collection<? extends GrantedAuthority> authorities =((UserDetails)principal).getAuthorities();
		boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		boolean isDept =  authorities.contains(new SimpleGrantedAuthority("ROLE_DEPT"));
		boolean isChecker = authorities.contains(new SimpleGrantedAuthority("ROLE_CHECKER"));
		
		System.out.println("YOU ARE IN /check-role");
		System.out.println("AUTHENTICATED USER'S NAME: " + auth.getName());
		System.out.println("IS AUTHENTICATED: " + auth.isAuthenticated());
		System.out.println("CONTAINS USER ROLE: " + authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
 
		if(isAdmin) {
			return "redirect:/administrator/account/view";
		} else if(isChecker) {
			return "redirect:/daily/checklist";
		} else if(isDept) {
			return "redirect:/faculty/view";
		} else {
			//role is non existent
			return "errorsssss/404.html";
		}
	}
	
	@RequestMapping(value="/login_faculty")
	public String showLoginForFaculty() {
		return "loginFaculty";
	}
	@RequestMapping(value="/logout-success")
	public String logoutSuccess() {
		return "redirect:/login";
	}
	
	/*
	 * @RequestMapping(value="/home") public String home() { return
	 * "redirect:/daily"; }
	 */
	
}
