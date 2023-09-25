package com.fresco.fresco.controllers;

import com.fresco.fresco.entity.Producto;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.service.ProductoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;


import java.util.Map;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ModelAndView mostrar(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("productos");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito"));
            mav.addObject("error", flashMap.get("error"));
        }

        mav.addObject("productos", productoService.buscarTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crear(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("autor", flashMap.get("autor"));
        } else {
            mav.addObject("producto", new Producto());
        }

        mav.addObject("title", "Crear Producto");
        mav.addObject("action", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id, HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        try {
            if (flashMap != null) {
                mav.addObject("error", flashMap.get("error"));
                mav.addObject("producto", flashMap.get("producto"));
            } else {
                mav.addObject("producto", productoService.buscarPorId(id));
            }

            mav.addObject("title", "Editar Autor");
            mav.addObject("action", "modificar");
        } catch (SpringException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/autor");
        }

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView guardar(@RequestParam MultipartFile foto, @ModelAttribute Producto producto, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/home");

        try {
            productoService.crear(producto, foto);
            attributes.addFlashAttribute("exito", "Se ha creado un nuevo producto");
        } catch (SpringException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/productos/crear");
        }

        return redirectView;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam MultipartFile foto, @ModelAttribute Producto producto, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/productos");

        try {
            productoService.modificar(producto, foto);
            attributes.addFlashAttribute("exito", "La actualización ha sido realizada satisfactoriamente");
        } catch (SpringException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/productos/editar/" + producto.getId());
        }

        return redirectView;
    }


    @PostMapping("/habilitar/{id}")
    public RedirectView habilitar(@PathVariable Integer id) {
        productoService.habilitar(id);
        return new RedirectView("/productos");
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return new RedirectView("/productos");
    }
}
