package com.pup.cea.facs.service.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pup.cea.facs.model.UserInfo;
import com.pup.cea.facs.model.security.UserLogin;



public class CurrentUser implements UserDetails {
	
		private UserLogin userLogin;
		private UserInfo userInfo;
		private final String ROLE_PREFIX = "ROLE_";
		
		
	    public CurrentUser(UserLogin userLogin, UserInfo userInfo) {
	        this.userLogin = userLogin;
	        this.userInfo = userInfo;
	    }
	    
	    //NOT OVERIDDEN METHOD
	    public String getFullname() {
	    	return userInfo.getFullname();
	    }
	    public String getOffice() {
	    	return userInfo.getOffice();
	    }
	    
	    public String getIconImage() {
			return "data:" + userInfo.getContenttype() + ";base64," + Base64.getEncoder().encodeToString(getImagedata());
		}
		public byte[] getImagedata() {
			return userInfo.getImagedata();
		}
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			List<GrantedAuthority> roleList = new ArrayList<GrantedAuthority>();
	        roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + userLogin.getRole().toUpperCase())); 
	        return roleList;
		}

		@Override
		public String getPassword() {
			return userLogin.getPassword();
		}
		@Override
		public String getUsername() {
			return userLogin.getUsername();
		}
		
		//Modify these methods later for more specific security configuration
		@Override
		public boolean isAccountNonExpired() {
			return true;
		}
		@Override
		public boolean isAccountNonLocked() {
			return true;
		}
		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}
		@Override
		public boolean isEnabled() {
			return true;
		}

}
