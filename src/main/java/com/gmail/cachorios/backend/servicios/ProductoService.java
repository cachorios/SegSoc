package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Producto;
import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.ProductoRepositorio;
import com.gmail.cachorios.core.data.FilterableAbmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService implements FilterableAbmService<Producto> {
    private final ProductoRepositorio productoRepositorio;

    @Autowired
    public ProductoService(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public Page<Producto> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if(filter.isPresent()){
            String filtro = makeForLike(filter.get());
            return getRepository().findByDescripcion(filtro, pageable);
        }
        return find(pageable);
    }

    private Page<Producto> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        if(filter.isPresent()){
            String filtro = makeForLike(filter.get());
            return getRepository().countByDescripcion(filtro);
        }
        return count();
    }

    @Override
    public Class<Producto> getBeanType() {
        return Producto.class;
    }

    @Override
    public ProductoRepositorio getRepository() {
        return productoRepositorio;
    }

    @Override
    public Producto createNew(Usuario currentUsuario) {
        return new Producto();
    }
}
