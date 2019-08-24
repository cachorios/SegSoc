package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.PersonaRepositorio;
import com.gmail.cachorios.core.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonaService implements FilterableAbmService<Persona> {
    private final PersonaRepositorio personaRepositorio;
    private Persona pariente;

    @Autowired
    public PersonaService(PersonaRepositorio personaRepositorio) {
        this.personaRepositorio = personaRepositorio;
    }

    @Override
    public Page<Persona> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(filter.isPresent()){
            String filtro = makeForLike(filter.get());
            return getRepository().findByNombreLikeIgnoreCase(filtro, pageable);
        } else if (pariente != null && !pariente.isNew()) {
            return getRepository().findDistinctOf(pariente.getId(), pageable);
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
        } else if (pariente != null && !pariente.isNew()) {
            return getRepository().countDistinctOf(pariente.getId());
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

    public void setPariente(Persona pariente) {
        this.pariente = pariente;
    }
}
