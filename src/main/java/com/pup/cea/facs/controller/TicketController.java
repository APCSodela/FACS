package com.pup.cea.facs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pup.cea.facs.model.Ticket;
import com.pup.cea.facs.service.TicketService;

@RequestMapping("/ticket")
@Controller
public class TicketController {
	
	@Autowired
	private TicketService service;
	@Autowired
	private ServletContext context;
	
	// Show List of Tickets
	@RequestMapping(value="/view")
	public String viewTickets(Model model) {
		
		List<Ticket> list = service.getAllPendingTickets();
		
		model.addAttribute("ticketList", list);
		return "/ticket/viewTicket";
	}
	
	// Show List of Waived Tickets
	@RequestMapping(value="/waive")
	public String viewWaives(Model model) {
		
		List<Ticket> list = service.getAllWaivedTickets();
		
		model.addAttribute("waiveList", list);
		return "/ticket/viewWaive";
	}
	
	// RELATED TO GENERATION OF REPORT
	
	// Action to Generate CSV from Ticket table
	@RequestMapping(value="/report")
	public String generateReport(HttpServletRequest request, HttpServletResponse response) {
		
		List<Ticket> tickets = service.getAllSortedTicketsByLastname();
		boolean isFlag = service.generateReport(tickets, context); // Method to Generate Report
		
		if(isFlag) {
			String fullPath = request.getServletContext().getRealPath("resources/reports/"+"tickets.csv");
			fileDownload(fullPath, response, "tickets.csv");
			
			for(Ticket ticket: tickets) {
				ticket.setStatus("Recorded");
				service.save(ticket);
			}
		}
		
		return "redirect:/view";
	}
	
	// Action to Download the Report
	private void fileDownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		
		if(file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename=" + fileName);
				
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
				
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// RELATED TO VIEW TICKETS
	
	// Action to Waive a Ticket
	@RequestMapping("/view/{id}/waive")
	public String waiveTicket(@PathVariable(name="id") Long id) {
		Ticket ticket = service.getById(id);
		ticket.setStatus("Yes");
		
		service.save(ticket);

		return "redirect:/ticket/view";
	}
	
	// RELATED TO VIEW WAIVED TICKETS
	
	// Action to Accept Waived Ticket
	@RequestMapping("/waive/{id}/accept")
	public String acceptWaive(@PathVariable(name="id") Long id) {
		Ticket ticket = service.getById(id);
		ticket.setStatus("Accepted");
		
		service.save(ticket);
		
		return "redirect:/ticket/waive";
	}
	
	// Action to Decline Waived Ticket
	@RequestMapping("/waive/{id}/decline")
	public String declineWaive(@PathVariable(name="id") Long id) {
		Ticket ticket = service.getById(id);
		ticket.setStatus("Declined");
		
		service.save(ticket);
		return "redirect:/ticket/waive";
	}
	
}