package com.fresco.fresco.controllers;

import com.fresco.fresco.entity.Ticket;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.service.ProductoService;
import com.fresco.fresco.service.TicketService;
import com.fresco.fresco.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/crear")
    public ModelAndView crear(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("ticket-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("ticket", flashMap.get("ticket"));
        } else {
            mav.addObject("ticket", new Ticket());
        }

        mav.addObject("productos", productoService.buscarTodos());
        mav.addObject("title", "Crear Ticket");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@ModelAttribute Ticket ticket, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/ticket");

        try {
            ticketService.crearTicket(ticket);
            attributes.addFlashAttribute("exito", "Se ha creado su ticket satisfactoriamente");
        } catch (SpringException e) {
            attributes.addFlashAttribute("ticket", ticket);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/ticket/crear");
        }

        return redirectView;
    }



}

