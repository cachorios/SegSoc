package com.gmail.cachorios.core.ui.data;

import java.util.Optional;


import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.servicios.SegSocServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterableAbmService<T extends EntidadInterface> extends AbmService<T> {

	Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

	long countAnyMatching(Optional<String> filter);


	Class<T> getBeanType();

	default String makeForLike(String filtro) {
		return "%" + filtro.toUpperCase() + "%";
	}

	default Optional<T> findById(Long id){
		return getRepository().findById(id);
	}

	default public Usuario getUsuario(){
		return SegSocServices.getUsuarioActivo();
	}
	
	
	
	
}