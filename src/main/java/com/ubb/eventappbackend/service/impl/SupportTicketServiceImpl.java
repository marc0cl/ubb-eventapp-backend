package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.SupportTicket;
import com.ubb.eventappbackend.model.TicketState;
import com.ubb.eventappbackend.repository.SupportTicketRepository;
import com.ubb.eventappbackend.service.SupportTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;

    @Override
    public SupportTicket openTicket(SupportTicket ticket) {
        return supportTicketRepository.save(ticket);
    }

    @Override
    public SupportTicket closeTicket(String ticketId) {
        SupportTicket ticket = supportTicketRepository.findById(ticketId).orElseThrow();
        ticket.setEstado(TicketState.CERRADO);
        return supportTicketRepository.save(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportTicket> findById(String id) {
        return supportTicketRepository.findById(id);
    }
}
