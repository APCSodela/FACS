package com.pup.cea.facs.model.load;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pup.cea.facs.model.Schedule;

@Entity
@Table(name = "load_detail")
public class LoadDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String building;
	@Column(name = "room_number")
	private String roomNumber;
	private String day;
	@Column(name = "time_start")
	private String timeStart;
	@Column(name = "time_end")
	private String timeEnd;
	
	@ManyToOne
	@JoinColumn(name="load_id", referencedColumnName = "id")    
	private Load load;
	
	@OneToOne(targetEntity=Schedule.class, mappedBy="loadDetail",cascade=CascadeType.ALL, fetch = FetchType.EAGER)    
	private Schedule schedule;
	

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}
	 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

}
