package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Persona;
import com.gmail.cachorios.backend.data.entity.Plan;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.PlanRepositorio;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanService implements FilterableAbmService<Plan> {
    private final PlanRepositorio planRepositorio;
    private Persona persona;

    @Autowired
    public PlanService(PlanRepositorio planaRepositorio) {
        this.planRepositorio = planaRepositorio;
    }

    @Override
    public Page<Plan> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(persona != null){
            return getRepository().findByPersona(persona, pageable);
        }
        return find(pageable);
    }

    private Page<Plan> find(Pageable pageable) {
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
    public Class<Plan> getBeanType() {
        return Plan.class;
    }

    @Override
    public PlanRepositorio getRepository() {
        return planRepositorio;
    }

    @Override
    public Plan createNew(Usuario currentUsuario) {
        return new Plan();
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}