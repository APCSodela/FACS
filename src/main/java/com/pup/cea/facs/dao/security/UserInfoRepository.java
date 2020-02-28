package com.pup.cea.facs.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.facs.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByUsername (String username);
}
