package com.pup.cea.facs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import com.pup.cea.facs.model.Faculty;
import com.pup.cea.facs.model.Schedule;
import com.pup.cea.facs.model.load.Load;
import com.pup.cea.facs.model.load.LoadCreationDto;
import com.pup.cea.facs.model.load.LoadDetail;
import com.pup.cea.facs.service.FacultyService;
import com.pup.cea.facs.service.LoadService;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	private final String ADD = "faculty/addFaculty"; 
	private final String VIEW = "faculty/viewFaculty";
	private final String REDIR_VIEW = "redirect:view";
	private final String FAC_PROFILE = "faculty/viewFacultyProfile";
	private final String ADD_SCHEDULE = "faculty/addSchedule";
	
	@Autowired
	private FacultyService service;
	@Autowired 
	private LoadService loadService;
	
	@RequestMapping(value="/view")
	public String viewFaculty(Model model) {
		List<Faculty> list = service.getAll();
		model.addAttribute("facultyList", list);
		return VIEW;
	}
	@RequestMapping(value="/add")
	public String viewAddForm(Model model) {
		Faculty faculty = new Faculty();
		model.addAttribute("facultydata",faculty);
		return ADD;
	}
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveFaculty(@ModelAttribute("facultydata") Faculty faculty, 
							  @RequestParam("imagefile") MultipartFile file) throws IOException {
		
		if(file.getSize()==0) {
			faculty.setImagedata(null);
			faculty.setContenttype(null);
			faculty.setImagesize(null);
		} else {
			faculty.setImagedata(file.getBytes());
			faculty.setContenttype(file.getContentType());
			faculty.setImagesize(file.getSize());
		}
		service.save(faculty);
		
		return REDIR_VIEW;
	}
	@RequestMapping(value="/update")
	public String updateFaculty(@ModelAttribute("facultydata") Faculty faculty,
								@RequestParam("imagefile") MultipartFile file) throws IOException {
		
		if(file.getSize()==0) {
			if (service.getById(faculty.getId()).getImagedata()!=null) {
				faculty.setImagedata(service.getById(faculty.getId()).getImagedata());
				faculty.setImagesize(service.getById(faculty.getId()).getImagesize());
				faculty.setContenttype(service.getById(faculty.getId()).getContenttype());
			} else {
				faculty.setImagedata(null);
				faculty.setContenttype(null);
				faculty.setImagesize(null);
			}
		} else {
			faculty.setImagedata(file.getBytes());
			faculty.setContenttype(file.getContentType());
			faculty.setImagesize(file.getSize());
		}
		service.save(faculty);
		
		return "redirect:profile/" + faculty.getId();
	}
	@RequestMapping("/profile/{facid}")
	public String showFacultyProfile(@PathVariable(name="facid") long facId, Model model) {
		
		final String PART_TIME = "PART TIME";
		final String REGULAR = "REGULAR";
		String temp;
		Faculty faculty = new Faculty();
		faculty = service.getById(facId);
		long totLoad = 0;
		long totPart = 0;
		long totFull = 0;
		
		for(int i = 0;i<faculty.getLoad().size();i++) {
			temp = faculty.getLoad().get(i).getLoadType().toUpperCase();
			if(temp.toUpperCase().equals(PART_TIME)) {
				totPart+=faculty.getLoad().get(i).getUnit();
			}
			else if(temp.toUpperCase().equals(REGULAR)){
				totFull+=faculty.getLoad().get(i).getUnit();
			}
			totLoad+=faculty.getLoad().get(i).getUnit();
		}
		
		model.addAttribute("profiledata", faculty);
		model.addAttribute("loads",faculty.getLoad());
		
		model.addAttribute("totalLoad",totLoad);
		model.addAttribute("totalPart",totPart);
		model.addAttribute("totalFull",totFull);
		
		return FAC_PROFILE;
	}
	@RequestMapping("/profile/{facid}/remove")
	public String deleteFaculty(@PathVariable(name="facid") Long facId) {
		service.delete(facId);
		return "redirect:/faculty/view";
	}
	@RequestMapping(value="/profile/{facid}/edit")
	public String updateFacultyProfile(@PathVariable(name="facid") long facId, Model model) {
		
		Faculty faculty = service.getById(facId);
		
		model.addAttribute("facultydata", faculty);
		model.addAttribute("facId", facId);
		
		return "faculty/editFaculty";
	}
	
	/*******************************************************LOADS****************************************************************/
	
	@RequestMapping("/profile/{facid}/schedule/add")
	public String addLoad(@PathVariable(name="facid") long facId, Model model) {
		
		LoadCreationDto loadForm = new LoadCreationDto();
		loadForm.addDetail(new LoadDetail());
		
		model.addAttribute("loadForm",loadForm);
		model.addAttribute("detailCount",loadForm.getLoadDetails().size());
		model.addAttribute("facid", facId);
		return ADD_SCHEDULE;
	}
	@RequestMapping(value="/profile/{facid}/schedule/save", method=RequestMethod.POST)
	public String saveLoad(@PathVariable(name="facid") long facId, 
							   @ModelAttribute("loadForm") LoadCreationDto loadForm,
							   HttpServletRequest req){
		Load load = new Load();
		Faculty faculty = service.getById(facId);
		
		load = loadForm.getLoad();
		load.setFaculty(faculty);
		
		//setting the load object for each of the detail
		for(int i = 0; i<loadForm.getLoadDetails().size();i++) {
			loadForm.getLoadDetails().get(i).setLoad(load);
		}
		
		//setting up schedule object
		for(int i = 0;i<loadForm.getLoadDetails().size();i++) {
			
			Schedule schedule = new Schedule();
			int roomNumber;
			schedule.setEmail(faculty.getEmail());
			schedule.setDepartment(faculty.getDepartment());
			schedule.setFirstname(faculty.getFirstname());
			schedule.setLastname(faculty.getLastname());
			schedule.setFacultyname(faculty.getFullname());
			schedule.setSubjectname(loadForm.getLoad().getSubjectName());
			if(loadForm.getLoadDetails().get(i).getRoomNumber()=="") {
				roomNumber=0;
			}else {
				roomNumber = Integer.parseInt(loadForm.getLoadDetails().get(i).getRoomNumber());
			}
			schedule.setRoom(roomNumber);
			schedule.setDay(loadForm.getLoadDetails().get(i).getDay());
			schedule.setTimestart(loadForm.getLoadDetails().get(i).getTimeStart());
			schedule.setTimeend(loadForm.getLoadDetails().get(i).getTimeEnd());
			schedule.setCheckstatus(null);
			
			schedule.setId(loadForm.getLoadDetails().get(i).getSchedule().getId());
			schedule.setLoadDetail(loadForm.getLoadDetails().get(i));
			loadForm.getLoadDetails().get(i).setSchedule(schedule);
		}
		
		load.setLoadDetail(loadForm.getLoadDetails());
	
		loadService.saveLoad(load);	
		
		return "redirect:/faculty/profile/"+facId;
	}
	@RequestMapping(value="/profile/{facid}/schedule/remove/{dtlId}", method=RequestMethod.GET)
	public String removeSchedule(@PathVariable(name="facid") long facId,
								 @PathVariable(name="dtlId") long dtlId) {	
		long loadId = loadService.findDetail(dtlId).getLoad().getId();
		loadService.deleteDetail(dtlId);
		
		if(loadService.findLoad(loadId).getLoadDetail().size()<=0) {
			loadService.deleteLoad(loadId);
		}
		return "redirect:/faculty/profile/"+facId;
	}
	@RequestMapping(value="/profile/{facid}/schedule/edit/{loadId}", method=RequestMethod.GET)
	public String editSchedule(@PathVariable(name="facid") long facId,
								 @PathVariable(name="loadId") long loadId, Model model) {
		
		LoadCreationDto loadForm = new LoadCreationDto();
		loadForm.setLoad(loadService.findLoad(loadId));
		loadForm.setLoadDetails(loadService.findLoad(loadId).getLoadDetail());
		
		model.addAttribute("loadForm",loadForm);
		model.addAttribute("detailCount",loadForm.getLoadDetails().size());
		model.addAttribute("facid", facId);
		
		return ADD_SCHEDULE;
	}
}
