package com.fresco.fresco.controllers;

import com.fresco.fresco.entity.Producto;
import com.fresco.fresco.entity.Usuario;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.service.ProductoService;
import com.fresco.fresco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/usuario/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable Integer id) {

        try {
            Usuario u = usuarioService.buscarPorId(id);
            if (u.getImage() == null) {
                throw new SpringException("El usuario no tiene foto");
            }
            byte[] foto = u.getImage().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<byte[]> fotoProducto(@PathVariable Integer id) {

        try {
            Producto p = productoService.buscarPorId(id);
            if (p.getImage() == null) {
                throw new SpringException("El producto no tiene foto");
            }
            byte[] foto = p.getImage().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
