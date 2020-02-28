package com.pup.cea.facs.model;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pup.cea.facs.model.security.UserLogin;

@Entity
@Table(name="user_info")
public class UserInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String office;
	private String fullname;
	
	@Column(name = "image_data",columnDefinition="longblob")
	private byte[] imagedata;
	@Column(name = "image_extension")
	private String contenttype;
	@Column(name = "image_size")
	private Long imagesize;
	
	@OneToOne
	@JoinColumn(name = "user_login_id", referencedColumnName = "id") 
	private UserLogin userLogin;
	 
	public UserInfo() {
		
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	@Transient
	public String getIconImage() {
		return "data:" + getContenttype() + ";base64," + Base64.getEncoder().encodeToString(getImagedata());
	}

	public byte[] getImagedata() {
		return imagedata;
	}
	public void setImagedata(byte[] imagedata) {
		this.imagedata = imagedata;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public Long getImagesize() {
		return imagesize;
	}
	public void setImagesize(Long imagesize) {
		this.imagesize = imagesize;
	}
	
	
	public UserLogin getUserLogin() { 
		return userLogin;
	}
	public void setUserLogin(UserLogin userLogin) { 
		this.userLogin = userLogin; 
	}
	 

}
