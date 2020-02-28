package com.pup.cea.facs.model;

import java.util.Base64;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.pup.cea.facs.model.load.Load;


@Entity
//@Table(name="faculty")
public class Faculty {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String empid;
	private String title;
	private String firstname;
	private String lastname;
	private String fullname;		// combination of title, firstname, and lastname
	private String department;
	private String empstatus;
	private String email;
	private String username;
	private String password;
//For the LOADS
	@OneToMany(targetEntity=Load.class, mappedBy="faculty",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private List<Load> load;
	
//FOR PROFILE PICTURE
	@Column(name = "image_data",columnDefinition="longblob")
	private byte[] imagedata;
	@Column(name = "image_extension")
	private String contenttype;
	@Column(name = "image_size")
	private Long imagesize;

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
	
//FOR LOAD
	public List<Load> getLoad() {
		return load;
	}

	public void setLoad(List<Load> load) {
		this.load = load;
	}
	
//FOR USERS
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//FOR USERS
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	// fullname has NO SETTER
	public String getFullname() {
		this.fullname = title + " " + firstname + " " + lastname;
		return fullname;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmpstatus() {
		return empstatus;
	}
	public void setEmpstatus(String empstatus) {
		this.empstatus = empstatus;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
//	public String getUsername() {
//		return username;
//	}
//	public void setUsername(String username) {
//		this.username = username;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
}
