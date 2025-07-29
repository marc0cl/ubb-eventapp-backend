package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.SupportTicket;
import com.ubb.eventapp.model.TicketState;
import com.ubb.eventapp.repository.SupportTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupportTicketServiceImplTest {

    private SupportTicketRepository supportTicketRepository;
    private SupportTicketServiceImpl service;

    @BeforeEach
    void setup() {
        supportTicketRepository = mock(SupportTicketRepository.class);
        service = new SupportTicketServiceImpl(supportTicketRepository);
    }

    @Test
    void closeTicket_setsStateToClosedAndSaves() {
        SupportTicket ticket = SupportTicket.builder().id(1L).estado(TicketState.ABIERTO).build();
        when(supportTicketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(supportTicketRepository.save(any(SupportTicket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SupportTicket result = service.closeTicket(1L);

        assertEquals(TicketState.CERRADO, result.getEstado());
        ArgumentCaptor<SupportTicket> captor = ArgumentCaptor.forClass(SupportTicket.class);
        verify(supportTicketRepository).save(captor.capture());
        assertEquals(TicketState.CERRADO, captor.getValue().getEstado());
    }
}
