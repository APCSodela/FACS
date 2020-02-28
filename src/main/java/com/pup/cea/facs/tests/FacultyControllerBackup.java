package com.pup.cea.facs.tests;

/*import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pup.cea.facs.dao.load.LoadRepository;
import com.pup.cea.facs.model.Daily;
import com.pup.cea.facs.model.Faculty;
import com.pup.cea.facs.model.load.Load;
import com.pup.cea.facs.model.load.LoadDetail;
import com.pup.cea.facs.service.DailyService;
import com.pup.cea.facs.service.FacultyService;*/

/*@Controller
@RequestMapping("/faculty")*/
public class FacultyControllerBackup {
	/*
	 * private final String ADD = "faculty/addFaculty"; private final String VIEW =
	 * "faculty/viewFaculty"; private final String REDIR_VIEW = "redirect:view";
	 * private final String FAC_PROFILE = "faculty/viewFacultyProfile"; private
	 * final String ADD_SCHEDULE = "faculty/addSchedule";
	 * 
	 * @Autowired private FacultyService service;
	 * 
	 * @Autowired private DailyService dailyService;
	 * 
	 * @Autowired LoadRepository asdepo;
	 * 
	 * 
	 * @RequestMapping(value="/view") public String viewFaculty(Model model) {
	 * List<Faculty> list = service.getAll(); model.addAttribute("facultyList",
	 * list); return VIEW; }
	 * 
	 * @RequestMapping(value="/add") public String viewAddForm(Model model) {
	 * Faculty faculty = new Faculty(); model.addAttribute("facultydata",faculty);
	 * return ADD; }
	 * 
	 * @RequestMapping(value="/save", method=RequestMethod.POST) public String
	 * saveFaculty( @ModelAttribute("facultydata") Faculty faculty,
	 * 
	 * @RequestParam("imagefile") MultipartFile file) throws IOException {
	 * 
	 * System.out.println(file.getContentType());
	 * System.out.println(file.getOriginalFilename());
	 * System.out.println(file.getBytes());
	 * 
	 * faculty.setImagedata(file.getBytes());
	 * faculty.setContenttype(file.getContentType());
	 * faculty.setImagesize(file.getSize());
	 * 
	 * System.out.println(faculty.getIconImage());
	 * 
	 * service.save(faculty);
	 * 
	 * 
	 * return REDIR_VIEW; }
	 * 
	 * @RequestMapping("/profile/{facid}") public String
	 * showFacultyProfile(@PathVariable(name="facid") long facId, Model model) {
	 * Faculty faculty = new Faculty(); List<Schedule> scheduleList = new
	 * ArrayList<Schedule>();
	 * 
	 * faculty = service.getById(facId); scheduleList =
	 * service.findFacultySchedulebyId(facId);
	 * 
	 * model.addAttribute("profiledata", faculty);
	 * model.addAttribute("profilesched", scheduleList); return FAC_PROFILE; }
	 * 
	 * @RequestMapping("/profile/{facid}/remove") public String
	 * deleteFaculty(@PathVariable(name="facid") Long facId) {
	 * service.delete(facId); return "redirect:/faculty/view"; }
	 * 
	 * @RequestMapping(value="/profile/{facid}/update", method=RequestMethod.POST)
	 * public String updateFacultyProfile(@PathVariable(name="facid") long
	 * facId, @ModelAttribute("profiledata") Faculty faculty) {
	 * service.save(faculty); return "redirect:/faculty/profile/{facid}"; }
	 * 
	 * @RequestMapping("/profile/{facid}/schedule/add") public String
	 * addSchedule(@PathVariable(name="facid") long facId, Model model) { Schedule
	 * schedule = new Schedule(); model.addAttribute("scheduleobject", schedule);
	 * 
	 * Load load = new Load(); model.addAttribute("loadobject",load);
	 * 
	 * model.addAttribute("facid", facId); return ADD_SCHEDULE; }
	 * 
	 * @RequestMapping(value="/profile/{facid}/schedule/save",
	 * method=RequestMethod.POST) public String
	 * saveSchedule(@PathVariable(name="facid") long facId,
	 * 
	 * @ModelAttribute("loadobject") Load load, HttpServletRequest req) throws
	 * Exception {
	 * 
	 * 
	 * schedule.setFacid(facId); service.saveSchedule(schedule);
	 * 
	 * Faculty faculty = service.getById(facId); List<LoadDetail> loadDtl = new
	 * LinkedList<LoadDetail>();
	 * 
	 * load-dtl-id List<String> detailIdList = new LinkedList<String>();
	 * detailIdList = Arrays.asList(req.getParameterValues("load-dtl-id"));
	 * classCourse List<String> courseList = new LinkedList<String>(); courseList =
	 * Arrays.asList(req.getParameterValues("classCourse")); classYear List<String>
	 * yearList = new LinkedList<String>(); yearList =
	 * Arrays.asList(req.getParameterValues("classYear")); classSection List<String>
	 * sectionList = new LinkedList<String>(); sectionList =
	 * Arrays.asList(req.getParameterValues("classSection")); building List<String>
	 * buildingList = new LinkedList<String>(); buildingList =
	 * Arrays.asList(req.getParameterValues("building")); room List<String> roomList
	 * = new LinkedList<String>(); roomList =
	 * Arrays.asList(req.getParameterValues("room")); day List<String> dayList = new
	 * LinkedList<String>(); dayList = Arrays.asList(req.getParameterValues("day"));
	 * timeStart List<String> startTimeList = new LinkedList<String>();
	 * startTimeList = Arrays.asList(req.getParameterValues("timeStart")); timeEnd
	 * List<String> endTimeList = new LinkedList<String>(); endTimeList =
	 * Arrays.asList(req.getParameterValues("timeEnd"));
	 * 
	 * if(detailIdList.size() != courseList.size() || detailIdList.size() !=
	 * yearList.size() || detailIdList.size() != sectionList.size() ||
	 * detailIdList.size() != buildingList.size() || detailIdList.size() !=
	 * roomList.size() || detailIdList.size() != dayList.size() ||
	 * detailIdList.size() != startTimeList.size() || detailIdList.size() !=
	 * endTimeList.size() false) {
	 * 
	 * throw new Exception("SCHEDULE IS INVALID");
	 * 
	 * }else { for(int i=0; i<courseList.size();i++) { LoadDetail detail = new
	 * LoadDetail();
	 * 
	 * if(detailIdList.get(i)=="") { detail.setId(0); }else {
	 * detail.setId(Long.parseLong(detailIdList.get(i))); }
	 * 
	 * detail.setClassCourse(courseList.get(i));
	 * detail.setClassYear(yearList.get(i));
	 * detail.setClassSection(sectionList.get(i));
	 * 
	 * detail.setBuilding(buildingList.get(i));
	 * detail.setRoomNumber(roomList.get(i)); detail.setDay(dayList.get(i));
	 * detail.setTimeStart(startTimeList.get(i));
	 * detail.setTimeEnd(endTimeList.get(i));
	 * 
	 * //Referencing the CURRENT LOAD to the LOAD DETAILS detail.setLoad(load);
	 * loadDtl.add(detail); } //saving all the accumulated details to the LOAD
	 * model's Details load.setLoadDetail(loadDtl); }
	 * faculty.setLoad(Arrays.asList(load)); service.save(faculty);
	 * 
	 * 
	 * 
	 * // Add schedule details in DAILY TABLE Faculty faculty =
	 * service.getById(facId); Daily daily = new Daily();
	 * daily.setDepartment(faculty.getDepartment());
	 * daily.setFacultyname(faculty.getFullname());
	 * daily.setSubjectname(schedule.getSubjectname());
	 * daily.setSchedid(schedule.getId()); daily.setRoom(schedule.getRoom());
	 * daily.setDay(schedule.getDay()); daily.setTimestart(schedule.getTimestart());
	 * daily.setTimeend(schedule.getTimeend()); dailyService.save(daily);
	 * 
	 * return "redirect:/faculty/profile/"+facId; }
	 * 
	 * @RequestMapping(value="/profile/{facid}/schedule/remove/{schedid}",
	 * method=RequestMethod.GET) public String
	 * removeSchedule(@PathVariable(name="facid") long facId,
	 * 
	 * @PathVariable(name="schedid") long schedId) {
	 * 
	 * service.deleteSchedule(schedId); return "redirect:/faculty/profile/"+facId; }
	 * 
	 * @RequestMapping(value="/profile/{facid}/schedule/edit/{schedid}",
	 * method=RequestMethod.GET) public String
	 * editSchedule(@PathVariable(name="facid") long facId,
	 * 
	 * @PathVariable(name="schedid") long schedId, Model model) {
	 * 
	 * Schedule schedule = service.getScheduleById(schedId);
	 * model.addAttribute("scheduleobject", schedule); model.addAttribute("facid",
	 * facId); return ADD_SCHEDULE; }
	 */
}
