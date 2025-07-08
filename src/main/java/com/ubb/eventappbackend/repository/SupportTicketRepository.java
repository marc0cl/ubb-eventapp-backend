package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, String> {
}
