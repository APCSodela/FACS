package com.pup.cea.facs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pup.cea.facs.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

	public List<Schedule> findByDay(String day);
	
	@Query(value = "SELECT * FROM schedule s WHERE s.day = ?1  ORDER BY s.room", nativeQuery=true)
	public List<Schedule> findByDaySortedByRoom(String day);
}
