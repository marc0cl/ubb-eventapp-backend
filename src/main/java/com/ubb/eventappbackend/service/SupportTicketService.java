package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.SupportTicket;

import java.util.Optional;

public interface SupportTicketService {
    SupportTicket openTicket(SupportTicket ticket);
    SupportTicket closeTicket(String ticketId);
    Optional<SupportTicket> findById(String id);
}
