package com.gmail.cachorios.backend.servicios;

import com.gmail.cachorios.core.ui.data.FilterableAbmService;
import com.gmail.cachorios.core.ui.data.UserFriendlyDataException;
import com.gmail.cachorios.backend.data.entity.User;
import com.gmail.cachorios.backend.repositorios.UserRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class UserService implements FilterableAbmService<User> {

    public static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "EL usuario ha sido bloqueado y no se puede modifica o borrar" ;
    private static final String DELETING_SELF_NOT_PERMITTED = "No puedes borrar tu propia cuenta";

    private final UserRepositorio userRepository;

    @Autowired
    public UserService(UserRepositorio userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page findAnyMatching(Optional filter, Pageable pageable) {
        if(filter.isPresent()){
            String filtro = makeForLike((String) filter.get());
            return getRepository().findByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
                    filtro, filtro, filtro, filtro, pageable);
        }else{
            return find(pageable);
        }
    }

    @Override
    public long countAnyMatching(Optional filter) {
        if(filter.isPresent()){
            String filtro = makeForLike((String) filter.get());
            return userRepository.countByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(filtro, filtro, filtro, filtro);
        }
        return count();
    }

    @Override
    public UserRepositorio getRepository() {
        return userRepository;
    }

    public Page<User> find(Pageable pageable){
        return getRepository().findBy(pageable);
    }

    @Override
    public User createNew(User currentUser) {
        return new User();
    }

    @Override
    public User save(User currentUser, User entity) {
        throwIfUserLocked(entity);
        return getRepository().saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void delete(User currentUser, User entity) {
        throwIfDeletingSelf(currentUser, entity);
        throwIfUserLocked(entity);
        FilterableAbmService.super.delete(currentUser, entity);
    }

    private void throwIfUserLocked(User entity) {
        if (entity != null && entity.isLocked()) {
            throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
        }
    }

    private void throwIfDeletingSelf(User currentUser, User user) {
        if (currentUser.equals(user)) {
            throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
        }
    }

    @Override
    public Class<User> getBeanType() {
        return User.class;
    }
}
