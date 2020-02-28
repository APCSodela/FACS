package com.pup.cea.facs.service;

//import java.util.HashSet;
//import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.facs.dao.faculty.FacultyRepository;
import com.pup.cea.facs.model.Faculty;


@Service
public class FacultyService {
	
	@Autowired
	private FacultyRepository profileRepo;
	
	public List<Faculty> getAll(){
		return profileRepo.findAll();
	}
	public void save(Faculty faculty) {
		profileRepo.save(faculty);
	}
	public Faculty getById(Long id) {
		return profileRepo.findById(id).get();
	}
	public void delete(Long id) {
		profileRepo.deleteById(id);
	}
	
}
