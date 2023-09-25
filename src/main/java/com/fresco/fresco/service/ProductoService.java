package com.fresco.fresco.service;

import com.fresco.fresco.entity.Producto;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FotoService fotoService;

    private String mensaje = "No existe ningún producto asociado con el ID %s";

    @Transactional
    public void crear(Producto dto, MultipartFile foto) throws SpringException {
        if (productoRepository.existsByNombre(dto.getNombre())) {
            throw new SpringException("Ya existe un producto registrado con ese nombre");
        }
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        if (!foto.isEmpty()) {
            producto.setImage(fotoService.guardar(foto));
        }
        producto.setAlta(true);
        productoRepository.save(producto);
    }

    @Transactional
    public void modificar(Producto dto, MultipartFile foto) throws SpringException {
        Producto producto = productoRepository.findById(dto.getId()).orElseThrow(() -> new SpringException(String.format(mensaje, dto.getId())));
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        if (!foto.isEmpty()) {
            producto.setImage(fotoService.guardar(foto));
        }
        productoRepository.save(producto);
    }

    @Transactional
    public List<Producto> buscarTodos() {
        return productoRepository.findAll();
    }

    @Transactional
    public void habilitar(Integer id) {
        productoRepository.habilitar(id);
    }

    @Transactional
    public void eliminar(Integer id) {
        productoRepository.deleteById(id);
    }

    @Transactional
    public Producto buscarPorId(Integer id) throws SpringException {
        return productoRepository.findById(id).orElseThrow(() -> new SpringException(String.format(mensaje, id)));
    }



}
