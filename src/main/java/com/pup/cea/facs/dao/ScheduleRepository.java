package com.pup.cea.facs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.facs.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

	public List<Schedule> findByDay(String day);
	
	
}
