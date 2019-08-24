package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Parametro;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.ParametroRepositorio;
import com.gmail.cachorios.core.data.FilterableAbmService;
import com.gmail.cachorios.core.data.enums.ETipoParametro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParametroTipoService implements FilterableAbmService<Parametro> {
    private final ParametroRepositorio parametroRepositorio;
    private ETipoParametro tipo;

    @Autowired
    public ParametroTipoService(ParametroRepositorio parametroRepositorio) {
        this.parametroRepositorio = parametroRepositorio;
    }

    @Override
    public Page<Parametro> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(tipo != null){
            return getRepository().findByTipo(tipo, pageable);
        }

        return find(pageable);
    }

    private Page<Parametro> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if(tipo != null){
            return getRepository().countByTipo(tipo);
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
    public Parametro createNew(Usuario currentUsuario) {
        return new Parametro();
    }

    public void setTipo(ETipoParametro tipo) {
        this.tipo = tipo;
    }
}
