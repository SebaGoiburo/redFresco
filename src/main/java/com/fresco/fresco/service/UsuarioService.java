package com.fresco.fresco.service;

import com.fresco.fresco.entity.Foto;
import com.fresco.fresco.entity.Usuario;
import com.fresco.fresco.enums.Rol;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.repository.FotoRepository;
import com.fresco.fresco.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private FotoService fotoService;

    @Autowired
    private FotoRepository fotoRepository;

    private String mensaje = "No existe ningún usuario asociado con el ID %s";

    @Transactional
    public void crear(Usuario dto, MultipartFile foto) throws SpringException {
        if (usuarioRepository.existsByCorreo(dto.getCorreo())) {
            throw new SpringException("Ya existe un usuario asociado al correo ingresado");
        }
        Usuario usuario = new Usuario();
        usuario.setCorreo(dto.getCorreo());
        usuario.setNombre_usuario(dto.getNombre_usuario());
        usuario.setContrasena(encoder.encode(dto.getContrasena()));
        usuario.setDireccion(dto.getDireccion());
        if (foto.isEmpty()) {
            usuario.setImage(fotoRepository.findByNombre("user"));
        } else {
            usuario.setImage(fotoService.guardar(foto));
        }
        usuario.setAlta(true);

        if (usuarioRepository.findAll().isEmpty()) {
            usuario.setRol(Rol.ADMIN);
        } else if (dto.getRol() == null) {
            usuario.setRol(Rol.USER);
        } else {
            usuario.setRol(dto.getRol());
        }
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void modificar(Usuario dto, MultipartFile foto) throws SpringException {
        Usuario usuario = usuarioRepository.findById(dto.getId())
                .orElseThrow(() -> new SpringException(String.format(mensaje, dto.getId())));
        usuario.setNombre_usuario(dto.getNombre_usuario());
        usuario.setContrasena(encoder.encode(dto.getContrasena()));
        usuario.setDireccion(dto.getDireccion());

        Foto fot = new Foto();
        fot = fotoService.guardar((MultipartFile) dto.getImage());
        usuario.setImage(fot);

        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Integer id) throws SpringException {
        return usuarioRepository.findById(id).orElseThrow(() -> new SpringException(String.format(mensaje, id)));
    }

    @Transactional
    public void habilitar(Integer id) {
        usuarioRepository.habilitar(id);
    }

    @Transactional
    public void eliminar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario asociado al correo ingresado"));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("usuariosession", usuario);

        return new User(usuario.getCorreo(), usuario.getContrasena(), Collections.singletonList(authority));
    }

}
