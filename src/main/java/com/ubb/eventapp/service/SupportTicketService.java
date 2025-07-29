package com.ubb.eventapp.service;

import com.ubb.eventapp.model.SupportTicket;

import java.util.Optional;

public interface SupportTicketService {
    SupportTicket openTicket(SupportTicket ticket);
    SupportTicket closeTicket(Long ticketId);
    Optional<SupportTicket> findById(Long id);
}
