package com.pup.cea.facs.tests.dynamicInput;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService{
	
	
	@Autowired
	BookRepository repo;
	
	public List<Book> findAll() {
		return repo.findAll();
	}
	
	public void saveAll(List<Book> list) {
		repo.saveAll(list);
	}

}
