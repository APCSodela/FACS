package com.pup.cea.facs.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.facs.model.security.UserLogin;


public interface UserLoginRepository extends JpaRepository<UserLogin,Long>{
	UserLogin findByUsername (String username);
}
