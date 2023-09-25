package com.fresco.fresco.repository;

import com.fresco.fresco.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    // List<Ticket> findTicketByNombre_Usuario(String nombre_usuario);

    // List<Ticket> findTicketByCorreo(String correo);

    Optional<Ticket> findTicketById(String id);

}
