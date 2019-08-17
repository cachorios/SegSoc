package com.gmail.cachorios.app.seguridad;

import java.util.Collections;

import com.gmail.cachorios.backend.data.entity.Usuario;
import com.gmail.cachorios.backend.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Implements the {@link UserDetailsService}.
 * 
 * This implementation searches for {@link Usuario} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsuarioRepositorio userRepository;

	@Autowired
	public UserDetailsServiceImpl(UsuarioRepositorio userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 *
	 * Recovers the {@link Usuario} from the database using the e-mail address supplied
	 * in the login screen. If the user is found, returns a
	 * {@link org.springframework.security.core.userdetails.User}.
	 *
	 * @param username Usuario's e-mail address
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userRepository.findByEmailIgnoreCase(username);
		if (null == usuario) {
			throw new UsernameNotFoundException("No hay usuario con ese correo: " + username);
		} else {
			return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPasswordHash(),
					Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole())));
		}
	}
}