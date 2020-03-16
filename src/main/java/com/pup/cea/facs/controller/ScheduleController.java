package com.pup.cea.facs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pup.cea.facs.model.Schedule;
import com.pup.cea.facs.model.Ticket;
import com.pup.cea.facs.service.EmailService;
import com.pup.cea.facs.service.ScheduleService;
import com.pup.cea.facs.service.TicketService;

@RequestMapping("/daily")
@Controller
public class ScheduleController {

	@Autowired
	private ScheduleService service;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private EmailService emailService;
    
    // Action to show the Daily Schedules
	@RequestMapping(value="/schedule")
	public String viewDailySchedulesToday() {
		return "redirect:/daily/schedule/" + getCurrentDay();
	}
	// Show the Daily Schedules
	@RequestMapping(value="/schedule/{day}")
	public String viewDailySchedulesOfTheDay(@PathVariable(name="day") String day, Model model) {
		
		List<Schedule> list = service.findByDaySortedByRoom(day);
		model.addAttribute("scheduleList", list);

		return "daily/viewSchedule" ;
	}

	//RELATED TO CHECKLIST

	// Action to show the Checklist of the Day
	@RequestMapping(value="/checklist")
	public String viewChecklistToday() {
		return "redirect:/daily/checklist/" + getCurrentDay();
	}
	// Show the Checklist of the Day
	@RequestMapping(value="/checklist/{day}")
	public String viewChecklistOfTheDay(@PathVariable(name="day") String day, Model model) {
		
		List<Schedule> list = service.findByDaySortedByRoom(day);
		model.addAttribute("day", day);
		model.addAttribute("checklist", list);
		
		Ticket ticket = new Ticket();
		model.addAttribute("ticketdata", ticket);

		return "daily/viewChecklist";
	}
	// Action to Mark as PRESENT the Faculty
	@RequestMapping(value="/checklist/{id}/present")
	public String presentFaculty(@PathVariable(name="id") Long id) {
		
		Schedule schedule = service.getById(id);
		Ticket ticket = new Ticket();
		
		schedule.setCheckstatus("PRESENT");
		
		ticket.setDate(getCurrentDate());
		ticket.setEmail(schedule.getEmail());
		ticket.setFirstname(schedule.getFirstname());
		ticket.setLastname(schedule.getLastname());
		ticket.setFacultyname(schedule.getFacultyname());
		ticket.setRoom(schedule.getRoom());
		ticket.setDay(schedule.getDay());
		ticket.setTimestart(schedule.getTimestart());
		ticket.setTimeend(schedule.getTimeend());
		ticket.setRemark("Present");
		ticket.setTimearrival(schedule.getTimestart());
		ticket.setTimediff(0);
		ticket.setStatus("No");
		
		service.save(schedule);
		ticketService.save(ticket);

		return "redirect:/daily/checklist";
	}
	// Action to Mark as ABSENT the Faculty
	@RequestMapping(value="/checklist/{id}/absent")
	public String absentFaculty(@PathVariable(name="id") Long id) {
		
		Schedule schedule = service.getById(id);
		Ticket ticket = new Ticket();
		
		schedule.setCheckstatus("ABSENT");
		
		ticket.setDate(getCurrentDate());
		ticket.setEmail(schedule.getEmail());
		ticket.setFirstname(schedule.getFirstname());
		ticket.setLastname(schedule.getLastname());
		ticket.setFacultyname(schedule.getFacultyname());
		ticket.setRoom(schedule.getRoom());
		ticket.setDay(schedule.getDay());
		ticket.setTimestart(schedule.getTimestart());
		ticket.setTimeend(schedule.getTimeend());
		ticket.setRemark("Absent");
		ticket.setTimearrival("N/A");
		ticket.setTimediff(timeDifference(schedule.getTimestart(), schedule.getTimeend()));
		ticket.setStatus("No");
		
		service.save(schedule);
		ticketService.save(ticket);

		//send email
		emailService.sendEmail(ticket);
		
		return "redirect:/daily/checklist";
	}
	// Action to Mark as LATE the Faculty
	@RequestMapping(value="/checklist/{id}/late", method=RequestMethod.POST)
	public String lateFaculty(@PathVariable(name="id") Long id,
								@ModelAttribute("ticketdata") Ticket ticket) {
		
		Schedule schedule = service.getById(id);
		
		ticket.setDate(getCurrentDate());
		ticket.setEmail(schedule.getEmail());
		ticket.setFirstname(schedule.getFirstname());
		ticket.setLastname(schedule.getLastname());
		ticket.setFacultyname(schedule.getFacultyname());
		ticket.setRoom(schedule.getRoom());
		ticket.setDay(schedule.getDay());
		ticket.setTimestart(schedule.getTimestart());
		ticket.setTimeend(schedule.getTimeend());
		ticket.setRemark("Late");
		ticket.setTimediff(timeDifference(schedule.getTimestart(), ticket.getTimearrival()));
		ticket.setStatus("No");
		
		schedule.setCheckstatus("LATE");
		
		service.save(schedule);
		ticketService.save(ticket);
		
		//send email
		emailService.sendEmail(ticket);

		return "redirect:/daily/checklist";
	}
	// Action to Save All Remarks
	@RequestMapping(value="/checklist/{day}/saveRemarks")
	public String saveRemarks(@PathVariable(name="day") String day) {
		
		List<Schedule> list = service.findByDay(day);
		Iterator<Schedule> listIterator = list.iterator();
		
		while(listIterator.hasNext()) {
			Schedule schedule = listIterator.next();
			schedule.setCheckstatus("");
		    service.save(schedule);
		}
		return "redirect:/daily/checklist";
	}
	
	
    public String getCurrentDay() {
    	LocalDate currentDay = LocalDate.now();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE");
        return dateFormat.format(currentDay);
    }
    public String getCurrentDate() {
    	LocalDate currentDate = LocalDate.now();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return dateFormat.format(currentDate);
    }
    public String getCurrentTime() {
    	LocalTime currentTime = LocalTime.now();
    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("h:mm a");
        return timeFormat.format(currentTime);
    }
    
    public float timeDifference(String time1, String time2) {
				
    	DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MMM dd yyyy h:mm a");
		
		LocalDateTime timeofFirst = LocalDateTime.parse(getCurrentDate() + " " + time1, dateTimeFormat);
    	LocalDateTime timeofSecond = LocalDateTime.parse(getCurrentDate() + " " + time2, dateTimeFormat);


		// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
		long diff = timeofSecond.atZone(ZoneOffset.ofTotalSeconds(0)).toInstant().toEpochMilli()
				    - timeofFirst.atZone(ZoneOffset.ofTotalSeconds(0)).toInstant().toEpochMilli();
		
		float diffSeconds = (float) diff/1000;
		
		float diffMinutes = diffSeconds/60;
		
		float diffHours = diffMinutes/60;
		
		//float diffDays = diffHours/60;
		
		return diffHours;
    }
}
