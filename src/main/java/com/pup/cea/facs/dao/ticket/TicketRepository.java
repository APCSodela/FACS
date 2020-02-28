package com.pup.cea.facs.dao.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pup.cea.facs.model.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	@Query(value = "SELECT * FROM ticket t WHERE (t.remark = 'Absent' OR t.remark = 'Late') AND (t.status = 'No' OR t.status = 'Declined')", nativeQuery=true)
	public List<Ticket> getAllPendingTickets();
	
	@Query(value = "SELECT * FROM ticket t WHERE (t.remark = 'Absent' OR t.remark = 'Late') AND (t.status = 'Yes' OR t.status = 'Accepted')", nativeQuery=true)
	public List<Ticket> getAllWaivedTickets();
	
} 
