package com.gmail.cachorios.core.ui.data;

import javax.persistence.EntityNotFoundException;

import com.gmail.cachorios.backend.data.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AbmService<T extends EntidadInterface> {

	JpaRepository<T, Long> getRepository();


	default T save(Usuario currentUsuario, T entity) {
		return getRepository().saveAndFlush(entity);
	}

	default void delete(Usuario currentUsuario, T entity) {
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		getRepository().delete(entity);
	}

	default void delete(Usuario currentUsuario, long id) {
		delete(currentUsuario, load(id));
	}

	default long count() {
		return getRepository().count();
	}

	default T load(long id) {
		T entity = getRepository().findById(id).orElse(null);
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		return entity;
	}

	T createNew(Usuario currentUsuario);
}
