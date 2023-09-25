package com.fresco.fresco.service;

import com.fresco.fresco.entity.Producto;
import com.fresco.fresco.entity.Ticket;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    private final String MENSAJE = "No existe ningún ticket asociado con el ID %s";

    @Transactional
    public void crearTicket(Ticket dto) throws SpringException {

        Ticket ticket = new Ticket();
        ticket.setUsuario(dto.getUsuario());
        ticket.setPedido((ArrayList<Producto>) dto.getPedido());
        ticket.setDireccion(dto.getDireccion());
        ticket.setFecha(dto.getFecha());
        ticket.setEstado("Confirmado y en proceso");
        ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<Ticket> buscarTodos() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ticket buscarPorId(String id) throws SpringException {
        return ticketRepository.findById(id).orElseThrow(() -> new SpringException(String.format(MENSAJE, id)));
    }


}
