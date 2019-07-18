package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Movimiento;
import com.gmail.cachorios.backend.data.entity.MovimientoDetalle;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.MovimientoDetalleRepositorio;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovimientoDetalleService implements FilterableAbmService<MovimientoDetalle> {
    private final MovimientoDetalleRepositorio movimientoDetalleaRepositorio;
    private Movimiento movimiento;

    @Autowired
    public MovimientoDetalleService(MovimientoDetalleRepositorio movimientoDetalleaRepositorio) {
        this.movimientoDetalleaRepositorio = movimientoDetalleaRepositorio;
    }

    @Override
    public Page<MovimientoDetalle> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(movimiento != null){
            return getRepository().findByMovimiento(movimiento, pageable);
        }
        return find(pageable);
    }

    private Page<MovimientoDetalle> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if(movimiento != null){
            return getRepository().countByMovimiento(movimiento);
        }
        return count();
    }

    @Override
    public Class<MovimientoDetalle> getBeanType() {
        return MovimientoDetalle.class;
    }

    @Override
    public MovimientoDetalleRepositorio getRepository() {
        return movimientoDetalleaRepositorio;
    }

    @Override
    public MovimientoDetalle createNew(Usuario currentUsuario) {
        return new MovimientoDetalle();
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }
}
