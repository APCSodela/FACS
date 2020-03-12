package com.pup.cea.facs.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.pup.cea.facs.dao.TicketRepository;
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
	public List<Ticket> getAllSortedTicketsByLastname() {
		return repo.getAllSortedTicketsByLastname();
	}
	
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
			
			data.add(new String[] {"Faculty Name", "Date", "Remark", "Time of Absence (in hours)"});
			
			List<Float> absences = new ArrayList<>();
			String facultyName = "";
			float total = 0;
			boolean start = true;
			for(Ticket ticket: tickets) {
				if(ticket.getFacultyname().equals(facultyName)) {
					data.add(new String[] {"", ticket.getDate(), ticket.getRemark(), String.valueOf(ticket.getTimediff())});
					absences.add(ticket.getTimediff());
					
				} else {
					if(start) {
						start = false;
					} else {
						for(Float absent: absences) {
							total+=absent;
						}
						data.add(new String[] {"", "", "TOTAL:", String.valueOf(total)});
						data.add(new String[] {"", "", "", ""});
						total = 0;
						absences.clear();
					}
					data.add(new String[] {ticket.getLastname() + ", " + ticket.getFirstname(), ticket.getDate(), ticket.getRemark(), String.valueOf(ticket.getTimediff())});
					facultyName = ticket.getFacultyname();
					absences.add(ticket.getTimediff());
				}
			}
			for(Float absent: absences) {
				total+=absent;
			}
			data.add(new String[] {"", "", "TOTAL:", String.valueOf(total)});
			data.add(new String[] {"", "", "", ""});
			absences.clear();
			
			writer.writeAll(data);
			writer.close();
			fileWriter.close();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}
