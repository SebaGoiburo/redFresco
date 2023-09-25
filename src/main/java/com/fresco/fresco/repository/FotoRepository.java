package com.fresco.fresco.repository;

import com.fresco.fresco.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends JpaRepository<Foto, String> {

    Foto findByNombre(String nombre);

}
