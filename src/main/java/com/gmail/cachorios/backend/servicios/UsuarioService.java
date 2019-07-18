package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.UserFriendlyDataException;
import com.gmail.cachorios.backend.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UsuarioService implements FilterableAbmService<Usuario> {

    public static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "EL usuario ha sido bloqueado y no se puede modifica o borrar" ;
    private static final String DELETING_SELF_NOT_PERMITTED = "No puedes borrar tu propia cuenta";

    private final UsuarioRepositorio userRepository;

    @Autowired
    public UsuarioService(UsuarioRepositorio userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page findAnyMatching(Optional filter, Pageable pageable) {
        if(filter.isPresent()){
            String filtro = makeForLike((String) filter.get());
            return getRepository().findByEmailLikeIgnoreCaseOrNombreLikeIgnoreCaseOrApellidoLikeIgnoreCaseOrRoleLikeIgnoreCase(
                    filtro, filtro, filtro, filtro, pageable);
        }else{
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional filter) {
        if(filter.isPresent()){
            String filtro = makeForLike((String) filter.get());
            return userRepository.countByEmailLikeIgnoreCaseOrNombreLikeIgnoreCaseOrApellidoLikeIgnoreCaseOrRoleLikeIgnoreCase(filtro, filtro, filtro, filtro);
        }
        return count();
    }

    @Override
    public UsuarioRepositorio getRepository() {
        return userRepository;
    }

    public Page<Usuario> find(Pageable pageable){
        return getRepository().findBy(pageable);
    }

    @Override
    public Usuario createNew(Usuario currentUsuario) {
        return new Usuario();
    }

    @Override
    public Usuario save(Usuario currentUsuario, Usuario entity) {
        throwIfUserLocked(entity);
        return getRepository().saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void delete(Usuario currentUsuario, Usuario entity) {
        throwIfDeletingSelf(currentUsuario, entity);
        throwIfUserLocked(entity);
        FilterableAbmService.super.delete(currentUsuario, entity);
    }

    private void throwIfUserLocked(Usuario entity) {
        if (entity != null && entity.isLocked()) {
            throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
        }
    }

    private void throwIfDeletingSelf(Usuario currentUsuario, Usuario usuario) {
        if (currentUsuario.equals(usuario)) {
            throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
        }
    }

    @Override
    public Class<Usuario> getBeanType() {
        return Usuario.class;
    }
}
