package com.ubb.eventapp.service;

import com.ubb.eventapp.model.SupportTicket;

import java.util.Optional;

public interface SupportTicketService {
    SupportTicket openTicket(SupportTicket ticket);
    SupportTicket closeTicket(String ticketId);
    Optional<SupportTicket> findById(String id);
}
