package com.pup.cea.facs.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pup.cea.facs.model.UserInfo;
import com.pup.cea.facs.model.security.UserLogin;
import com.pup.cea.facs.service.AppUserService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	AppUserService userService;
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String adminHome() {
		return "administrator/administratorHome";
	}
	@RequestMapping(value="/view-users")
	public String viewUsers(Model model) {
		/*
		 * System.out.println(userService.findUsers().get(1).getUserInfo().getImagedata(
		 * ));
		 */
		model.addAttribute("userList",userService.findUsers());
		return "administrator/viewUsers";
	}
	@RequestMapping(value="/add-user")
	public String addUser(Model model) {
		
		UserLogin userLogin = new UserLogin();
		UserInfo userInfo = new UserInfo();
		model.addAttribute("userObject", userLogin);
		model.addAttribute("userInfoObject", userInfo);
		
		return "administrator/addUser";
	}
	@RequestMapping(value="/save-user", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute("userObject") UserLogin userLogin,
						   @ModelAttribute("userInfoObject") UserInfo userInfo,
						   @RequestParam("imagefile") MultipartFile file,
							HttpServletRequest req) throws IOException  {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		
		//this is if the user is being updated
		if (userLogin.getId()!=0 && userLogin.getPassword()==null) {
			userLogin.setPassword(userService.findUser(userLogin.getId()).getPassword());
		} else {
			String encodedPassword = bCrypt.encode(userLogin.getPassword());
			userLogin.setPassword(encodedPassword);
		}
		
		if(file.getSize()==0) {
//			if (userService.findUser(userLogin.getId()).getUserInfo().getImagedata()!=null) {
//				userInfo.setImagedata(userService.findUser(userLogin.getId()).getUserInfo().getImagedata());
//				userInfo.setImagesize(userService.findUser(userLogin.getId()).getUserInfo().getImagesize());
//				userInfo.setContenttype(userService.findUser(userLogin.getId()).getUserInfo().getContenttype());
//			} else {
//				userInfo.setImagedata(null);
//				userInfo.setContenttype(null);
//				userInfo.setImagesize(null);
//			}
			userInfo.setImagedata(null);
			userInfo.setContenttype(null);
			userInfo.setImagesize(null);
		} else {
			userInfo.setImagedata(file.getBytes());
			userInfo.setContenttype(file.getContentType());
			userInfo.setImagesize(file.getSize());
		}
		userInfo.setUsername(userLogin.getUsername());
		userInfo.setUserLogin(userLogin);
		
		userService.saveUser(userLogin);
		userService.saveUserInfo(userInfo);
		
		return "redirect:/administrator/view-users";
	}
	@RequestMapping("/user-profile/{userId}/edit")
	public String editUser(@PathVariable("userId") Long userId,
						   Model model) {
		
		UserLogin userLogin = userService.findUser(userId);
		
		model.addAttribute("userObject", userLogin);
		model.addAttribute("userInfoObject", userLogin.getUserInfo());
		
		return "administrator/addUser";
				
	}
	@RequestMapping("/user-profile/{userId}/remove")
	public String removeUser(@PathVariable("userId") Long userId,
						   Model model) {
		
		UserLogin userLogin = userService.findUser(userId);
		UserInfo userInfo = userLogin.getUserInfo();
		
		userService.deleteUserInfo(userInfo.getId());
		userService.deleteUser(userId);
		
		return "redirect:/administrator/view-users";
				
	}

}
