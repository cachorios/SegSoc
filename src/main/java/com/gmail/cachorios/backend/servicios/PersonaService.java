package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.PersonaRepositorio;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService implements FilterableAbmService<Persona> {
    private final PersonaRepositorio personaRepositorio;

    @Autowired
    public PersonaService(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    @Override
    public Page<Persona> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(filter.isPresent()){
            String filtro = makeForLike(filter.get());
            return getRepository().findByNombreLikeIgnoreCase(filtro, pageable);
        }
        return find(pageable);
    }

    private Page<Persona> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if(filter.isPresent()){
            String filtro = makeForLike(filter.get());
            return getRepository().countByNombreLikeIgnoreCase(filtro);
        }
        return count();
    }

    @Override
    public Class<Persona> getBeanType() {
        return Persona.class;
    }

    @Override
    public PersonaRepositorio getRepository() {
        return personaRepositorio;
    }

    @Override
    public Persona createNew(Usuario currentUsuario) {
        return new Persona();
    }
}
