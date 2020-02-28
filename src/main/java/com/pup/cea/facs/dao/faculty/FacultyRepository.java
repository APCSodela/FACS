package com.pup.cea.facs.dao.faculty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.facs.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
	
}
