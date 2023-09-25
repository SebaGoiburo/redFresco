package com.fresco.fresco.repository;

import com.fresco.fresco.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByNombre(String nombre);

    Optional<Producto> findById(Integer id);

    boolean existsByNombre(String nombre);

    @Modifying
    @Query("UPDATE Producto p SET p.alta = true WHERE p.id = :id")
    void habilitar(@Param("id") Integer id);

}
