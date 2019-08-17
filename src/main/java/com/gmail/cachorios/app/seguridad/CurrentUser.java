package com.gmail.cachorios.app.seguridad;

import com.gmail.cachorios.backend.data.entity.Usuario;

@FunctionalInterface
public interface CurrentUser {

	Usuario getUser();
}
