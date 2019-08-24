package com.gmail.cachorios.core.data;

import javax.persistence.EntityNotFoundException;

import com.gmail.cachorios.backend.data.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AbmService<T extends EntidadInterface> {

	JpaRepository<T, Long> getRepository();


	default T save(Usuario currentUsuario, T entity) {
		if(getPadre() == null) {
			return getRepository().saveAndFlush(entity);
		}
		if(entity.isNew()) {
			getList().add(entity);
		}
		return entity;
	}

	default void delete(Usuario currentUsuario, T entity) {
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		if(getPadre() == null) {
			getRepository().delete(entity);
		}
		getList().remove(entity);
	}

	default void delete(Usuario currentUsuario, long id) {
		delete(currentUsuario, load(id));
	}

	default long count() {
		if(getPadre() == null) {
			return getRepository().count();
		}
		return getList().size();
	}

	default T load(long id) {
		T entity = getRepository().findById(id).orElse(null);
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		return entity;
	}

	T createNew(Usuario currentUsuario);
	
	default void setPadre(EntidadInterface padre){}
	
	default EntidadInterface getPadre(){
		return null;
	}
	
	default <T extends EntidadInterface> List<T> getList(){return null; }
}
