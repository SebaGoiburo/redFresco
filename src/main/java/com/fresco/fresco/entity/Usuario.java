package com.fresco.fresco.entity;

import com.fresco.fresco.enums.Rol;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;

@Entity
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String nombre_usuario;
    @Column(nullable = false)
    private String contrasena;

    @JdbcTypeCode(SqlTypes.JSON)
    private ArrayList<Ticket> tickets;

    private String direccion;

    @OneToOne
    private Foto image;

    private Boolean alta;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario(String correo, String nombre_usuario, String contrasena) {
        this.correo = correo;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Foto getImage() {
        return image;
    }

    public void setImage(Foto image) {
        this.image = image;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String   toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", contraseña='" + contrasena + '\'' +
                ", dirección='" + direccion + '\'' +
//                ", creación='" + creacion + '\'' +
//                ", modificación='" + modificacion + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
