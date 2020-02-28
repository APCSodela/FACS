package com.pup.cea.facs.model.load;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pup.cea.facs.model.Faculty;

@Entity
@Table(name="loads")
public class Load {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="subject_code")
	private String subjectCode;
	@Column(name="subject_name")
	private String subjectName;
	private long unit; 
	@Column(name="subject_reference")
	private String subjectRef;
	private String effectivity;
	@Column(name="load_type")
	private String loadType;
	
	//class info
	@Column(name = "class_course")
	private String classCourse;
	@Column(name = "class_year")
	private String classYear;
	@Column(name = "class_section")
	private String classSection;
	
	//FOR CLASS INFO
	public String getClassCourse() {
		return classCourse;
	}

	public void setClassCourse(String classCourse) {
		this.classCourse = classCourse;
	}

	public String getClassYear() {
		return classYear;
	}

	public void setClassYear(String classYear) {
		this.classYear = classYear;
	}

	public String getClassSection() {
		return classSection;
	}

	public void setClassSection(String classSection) {
		this.classSection = classSection;
	}
	
	//For the LOAD DETAILS
	@OneToMany(targetEntity=LoadDetail.class, mappedBy="load",cascade=CascadeType.ALL, fetch = FetchType.LAZY)    
	private List<LoadDetail> loadDetail;
	
	//For the FACULTY
	@ManyToOne
	@JoinColumn(name="fac_id", referencedColumnName = "id")    
	private Faculty faculty;

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public List<LoadDetail> getLoadDetail() {
		return loadDetail;
	}

	public void setLoadDetail(List<LoadDetail> loadDetail) {
		this.loadDetail = loadDetail;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public long getUnit() {
		return unit;
	}
	public void setUnit(long unit) {
		this.unit = unit;
	}
	public String getSubjectRef() {
		return subjectRef;
	}
	public void setSubjectRef(String subjectRef) {
		this.subjectRef = subjectRef;
	}
	public String getEffectivity() {
		return effectivity;
	}
	public void setEffectivity(String effectivity) {
		this.effectivity = effectivity;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	
	

}
