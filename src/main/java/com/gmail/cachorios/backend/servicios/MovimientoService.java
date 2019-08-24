package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Movimiento;
import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.MovimientoRepositorio;
import com.gmail.cachorios.core.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovimientoService implements FilterableAbmService<Movimiento> {
    private final MovimientoRepositorio movimientoaRepositorio;
    private Persona persona;

    @Autowired
    public MovimientoService(MovimientoRepositorio movimientoaRepositorio) {
        this.movimientoaRepositorio = movimientoaRepositorio;
    }

    @Override
    public Page<Movimiento> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(persona != null){
            return getRepository().findByPersona(persona, pageable);
        }
        return find(pageable);
    }

    private Page<Movimiento> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if(persona != null){
            return getRepository().countByPersona(persona);
        }
        return count();
    }

    @Override
    public Class<Movimiento> getBeanType() {
        return Movimiento.class;
    }

    @Override
    public MovimientoRepositorio getRepository() {
        return movimientoaRepositorio;
    }

    @Override
    public Movimiento createNew(Usuario currentUsuario) {
        return new Movimiento();
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
