package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}
