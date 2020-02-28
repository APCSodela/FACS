package com.pup.cea.facs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.facs.dao.security.UserInfoRepository;
import com.pup.cea.facs.dao.security.UserLoginRepository;
import com.pup.cea.facs.model.UserInfo;
import com.pup.cea.facs.model.security.UserLogin;

@Service
public class AppUserService {
	@Autowired
	private UserInfoRepository userInfoRepo;
	@Autowired
	private UserLoginRepository userLoginRepo;
	
	
	public List<UserInfo> findAll(){
		return userInfoRepo.findAll();
	}
	public UserInfo findByUsername(String username) {
		return userInfoRepo.findByUsername(username);
	}
	public void saveUserInfo(UserInfo userInfo) {
		userInfoRepo.save(userInfo);
	}
	public void deleteUserInfo(Long id) {
		userInfoRepo.deleteById(id);
	}
	
	/*FOR USER LOGIN */
	public UserLogin findUser(Long id) {
		return userLoginRepo.findById(id).get();
	}
	public void saveUser(UserLogin userLogin) {
		userLoginRepo.save(userLogin);
	}
	public void deleteUser(Long id) {
		userLoginRepo.deleteById(id);
	}
	public List<UserLogin> findUsers() {
		return userLoginRepo.findAll();
	}
}
