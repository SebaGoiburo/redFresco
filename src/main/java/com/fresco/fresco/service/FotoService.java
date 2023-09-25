package com.fresco.fresco.service;

import com.fresco.fresco.entity.Foto;
import com.fresco.fresco.excepction.SpringException;
import com.fresco.fresco.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FotoService  {

//    @Autowired
//    private Foto foto;

    @Autowired
    private FotoRepository fotoRepository;

    public Foto guardar(MultipartFile foto) throws SpringException{
        if(foto!=null){
            try {
                Foto fot = new Foto();
                fot.setMime(foto.getContentType());
                fot.setNombre(foto.getName());
                fot.setContenido(foto.getBytes());
                return fotoRepository.save(fot);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Foto actualizar(String id, MultipartFile foto) throws SpringException{
        if(foto!=null){
            try {
                Foto fot = new Foto();

                if (id != null){
                    Optional<Foto> respuesta = fotoRepository.findById(id);
                    if(respuesta.isPresent()){
                        fot = respuesta.get();
                    }
                }

                fot.setMime(foto.getContentType());
                fot.setNombre(foto.getName());
                fot.setContenido(foto.getBytes());
                return fotoRepository.save(fot);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

}
