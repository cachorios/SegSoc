package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.data.entity.User;
import com.gmail.cachorios.backend.repositorios.ParametroRepositorio;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParametroService implements FilterableAbmService<Parametro> {
    private final ParametroRepositorio parametroRepositorio;

    @Autowired
    public ParametroService(ParametroRepositorio parametroRepositorio) {
        this.parametroRepositorio = parametroRepositorio;
    }

    @Override
    public Page<Parametro> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(filter.isPresent()){
            String filtro = makeForLike(filter.get());
            return getRepository().findByNombreLikeIgnoreCase(filtro, pageable);
        }
        return find(pageable);
    }

    private Page<Parametro> find(Pageable pageable) {
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
    public Class<Parametro> getBeanType() {
        return Parametro.class;
    }

    @Override
    public ParametroRepositorio getRepository() {
        return parametroRepositorio;
    }

    @Override
    public Parametro createNew(User currentUser) {
        return new Parametro();
    }
}
