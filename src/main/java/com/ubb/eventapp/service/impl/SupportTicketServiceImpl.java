package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.SupportTicket;
import com.ubb.eventapp.model.TicketState;
import com.ubb.eventapp.repository.SupportTicketRepository;
import com.ubb.eventapp.service.SupportTicketService;
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
