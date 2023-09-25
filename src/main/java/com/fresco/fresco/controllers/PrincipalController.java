package com.fresco.fresco.controllers;

import com.fresco.fresco.entity.Producto;
import com.fresco.fresco.entity.Usuario;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.service.ProductoService;
import com.fresco.fresco.service.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;

@Controller
public class PrincipalController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/home")
    public ModelAndView home(ModelMap modelo, HttpSession session) {
        List<Producto> productos = productoService.buscarTodos();
        modelo.addAttribute("productos", productos);
        modelo.addAttribute("usuariosession", session.getAttribute("usuariosession"));
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error,
            @RequestParam(required = false) String logout) {
        ModelAndView mav = new ModelAndView("login");

        if (error != null) {
            mav.addObject("error", "Correo o contraseña inválida");
        }

        if (logout != null) {
            mav.addObject("logout", "Ha salido correctamente de la plataforma");
        }

        return mav;
    }

    @PostMapping("/registro")
    public RedirectView registro(@RequestParam MultipartFile foto, @ModelAttribute Usuario usuario,
            HttpServletRequest request, RedirectAttributes attributes) {
        RedirectView redirectView = new RedirectView("/login");

        try {
            usuarioService.crear(usuario, foto);
            request.login(usuario.getCorreo(), usuario.getContrasena());
        } catch (SpringException e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/login");
        } catch (ServletException e) {
            attributes.addFlashAttribute("error", "Error al realizar auto-login");
        }

        return redirectView;
    }
}
