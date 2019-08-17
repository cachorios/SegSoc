package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Documento;
import com.gmail.cachorios.backend.data.entity.MovimientoDetalle;
import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.DocumentoRepositorio;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentoService implements FilterableAbmService<Documento> {

    private final DocumentoRepositorio documentoRepositorio;
    private Persona persona;
    private MovimientoDetalle movimientoDet;

    @Autowired
    public DocumentoService(DocumentoRepositorio documentoRepositorio) {
        this.documentoRepositorio = documentoRepositorio;
    }

    @Override
    public Page<Documento> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(movimientoDet != null){
            return getRepository().findByMovimientoDetalle(movimientoDet, pageable);
        }

        if(persona != null){
            return getRepository().findByPersona(persona, pageable);
        }

        return find(pageable);
    }

    private Page<Documento> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if(movimientoDet != null){
            return getRepository().countByMovimientoDetalle(movimientoDet);
        }

        if(persona != null){
            return getRepository().countByPersona(persona);
        }

        return count();
    }

    @Override
    public Class<Documento> getBeanType() {
        return Documento.class;
    }

    @Override
    public DocumentoRepositorio getRepository() {
        return documentoRepositorio;
    }

    @Override
    public Documento createNew(Usuario currentUsuario) {
        return new Documento();
    }

    public void setMovimientoDetalle(MovimientoDetalle movimientoDetalle) {
        this.movimientoDet = movimientoDetalle;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
