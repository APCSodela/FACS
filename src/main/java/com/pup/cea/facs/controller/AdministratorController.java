package com.pup.cea.facs.controller;

import java.io.IOException;
import java.util.List;

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

import com.pup.cea.facs.model.Ticket;
import com.pup.cea.facs.model.UserInfo;
import com.pup.cea.facs.model.security.UserLogin;
import com.pup.cea.facs.service.AppUserService;
import com.pup.cea.facs.service.TicketService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	AppUserService userService;
	@Autowired
	private TicketService ticketService;
	
	// Action to show View Accounts Page
	@RequestMapping(value="/account/view")
	public String viewAccounts(Model model) {
		List<UserLogin> users = userService.findUsers();
		
		model.addAttribute("userList", users);
		return "administrator/viewAccount";
	}
	
	// Action to show Add Account Page
	@RequestMapping(value="/account/add")
	public String addAccount(Model model) {
		
		UserLogin userLogin = new UserLogin();
		UserInfo userInfo = new UserInfo();
		model.addAttribute("userObject", userLogin);
		model.addAttribute("userInfoObject", userInfo);
		
		return "administrator/addAccount";
	}
	// Action to Save an Account
	@RequestMapping(value="/account/save", method=RequestMethod.POST)
	public String saveAccount(@ModelAttribute("userObject") UserLogin userLogin,
						   		@ModelAttribute("userInfoObject") UserInfo userInfo,
						   		@RequestParam("imagefile") MultipartFile file) throws IOException  {
		// For UserLogin
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		String encodedPassword = bCrypt.encode(userLogin.getPassword());
		userLogin.setPassword(encodedPassword);
		
		// For UserInfo
		if(file.getSize()==0) {
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
		
		return "redirect:/administrator/account/view";
	}
	// Action to show Edit Account Page
	@RequestMapping("/account/{userId}/edit")
	public String editUser(@PathVariable("userId") Long userId,
						   	Model model) {
		
		UserLogin userLogin = userService.findUser(userId);
		
		model.addAttribute("userObject", userLogin);
		model.addAttribute("userInfoObject", userLogin.getUserInfo());
		
		return "administrator/editAccount";
	}
	// Action to Edit an Account
	@RequestMapping(value="/account/edit")
	public String updateAccount(@ModelAttribute("userObject") UserLogin userLogin,
						   			@ModelAttribute("userInfoObject") UserInfo userInfo,
						   			@RequestParam("imagefile") MultipartFile file) throws IOException  {
		// For UserLogin
			// For Password (If Updating)
		userLogin.setId(userLogin.getId());
		userLogin.setUsername(userLogin.getUsername());
		userLogin.setRole(userLogin.getRole());
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		if (userLogin.getPassword()==null) {
			userLogin.setPassword(userService.findUser(userLogin.getId()).getPassword());
		} else {
			String encodedPassword = bCrypt.encode(userLogin.getPassword());
			userLogin.setPassword(encodedPassword);
		}
		
		// For UserInfo
		userInfo.setId(userLogin.getId());
		if(file.getSize()==0) {
			if (userService.findUser(userLogin.getId()).getUserInfo().getImagedata()!=null) {
				userInfo.setImagedata(userService.findUser(userLogin.getId()).getUserInfo().getImagedata());
				userInfo.setImagesize(userService.findUser(userLogin.getId()).getUserInfo().getImagesize());
				userInfo.setContenttype(userService.findUser(userLogin.getId()).getUserInfo().getContenttype());
			} else {
				userInfo.setImagedata(null);
				userInfo.setContenttype(null);
				userInfo.setImagesize(null);
			}
		} else {
			userInfo.setImagedata(file.getBytes());
			userInfo.setContenttype(file.getContentType());
			userInfo.setImagesize(file.getSize());
		}
		userInfo.setUsername(userLogin.getUsername());
		userInfo.setUserLogin(userLogin);
		
		userService.saveUserInfo(userInfo);
		userService.saveUser(userLogin);
		
		return "redirect:/administrator/account/view";
	}
	// Action to Remove an Account
	@RequestMapping("/account/{userId}/remove")
	public String removeUser(@PathVariable("userId") Long userId,
						   Model model) {
		
		UserLogin userLogin = userService.findUser(userId);
		UserInfo userInfo = userLogin.getUserInfo();
		
		userService.deleteUserInfo(userInfo.getId());
		userService.deleteUser(userId);
		
		return "redirect:/administrator/account/view";
	}
	
	// RELATED TO RECORDS
	
	@RequestMapping(value="/record")
	public String viewRecords(Model model) {
		List<Ticket> list = ticketService.getAllRecordedTickets();
		
		model.addAttribute("recordList", list);
		return "administrator/viewRecord";
	}

}
