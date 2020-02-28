package com.pup.cea.facs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.facs.dao.load.LoadDetailRepository;
import com.pup.cea.facs.dao.load.LoadRepository;
import com.pup.cea.facs.model.load.Load;
import com.pup.cea.facs.model.load.LoadDetail;

@Service
public class LoadService {
	@Autowired
	LoadRepository loadRepo;
	@Autowired
	LoadDetailRepository detailRepo;
	
	public void saveLoad(Load load) {
		loadRepo.save(load);
	}
	public Load findLoad(Long id) {
		return loadRepo.findById(id).get();
	}
	public void deleteLoad(Long id) {
		loadRepo.deleteById(id);
	}
	public void deleteDetail(Long id) {
		detailRepo.deleteById(id);
	}
	public LoadDetail findDetail(Long id) {
		return detailRepo.findById(id).get();
		
	}

}
