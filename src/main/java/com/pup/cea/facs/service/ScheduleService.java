package com.pup.cea.facs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.facs.dao.ScheduleRepository;
import com.pup.cea.facs.model.Schedule;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository repo;
	
	public List<Schedule> getAll() {
		return repo.findAll();
	}
	public void save(Schedule daily) {
		repo.save(daily);
	}
	public Schedule getById(Long id) {
		return repo.findById(id).get();
	}
	public void delete(Long id) {
		repo.deleteById(id);
	}
	public void deleteAll() {
		repo.deleteAll();
	}
	
	public List<Schedule> findByDay(String day) {
		return repo.findByDay(day);
	}
	
	public List<Schedule> findByDaySortedByRoom(String day) {
		return repo.findByDaySortedByRoom(day);
	}
	
}
