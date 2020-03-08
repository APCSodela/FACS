package com.pup.cea.facs.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.pup.cea.facs.dao.ticket.TicketRepository;
import com.pup.cea.facs.model.Ticket;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository repo;
	
	public List<Ticket> getAllPendingTickets() {
		return repo.getAllPendingTickets();
	}
	
	public List<Ticket> getAll() {
		return repo.findAll();
	}
	public void save(Ticket ticket) {
		repo.save(ticket);
	}
	public Ticket getById(Long id) {
		return repo.findById(id).get();
	}
	public void delete(Long ticketId) {
		repo.deleteById(ticketId);
	}
	
	// RELATED TO WAIVED TICKETS
	public List<Ticket> getAllWaivedTickets() {
		return repo.getAllWaivedTickets();
	}
	
	// RELATED TO RECORDS
	public List<Ticket> getAllRecordedTickets() {
		return repo.getAllRecordedTickets();
	}
	
	// RELATED TO GENERATING CSV REPORT
	public boolean generateReport(List<Ticket> tickets, ServletContext context) {
		
		String filePath = context.getRealPath("/resources/reports");
		boolean exists = new File(filePath).exists();
		
		if(exists == false) {
			new File(filePath).mkdirs();
		}
		
		File file = new File(filePath+"/"+File.separator+"tickets.csv");
		try {
			FileWriter fileWriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fileWriter);
			
			List<String[]> data = new ArrayList<String[]>();
			
			data.add(new String[] {"Faculty Name", "Date", "Time of Absence (in hours)", "Remark"});
			
			for(Ticket ticket: tickets) {
				data.add(new String[] {ticket.getFacultyname(), ticket.getDate(), String.valueOf(ticket.getTimediff()), ticket.getRemark()});
			}
			
			writer.writeAll(data);
			writer.close();
			fileWriter.close();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}
