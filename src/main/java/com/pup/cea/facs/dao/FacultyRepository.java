package com.pup.cea.facs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.facs.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
	
}
