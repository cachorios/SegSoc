package com.gmail.cachorios.backend.data;

public class Role {
	public static final String USUARIO = "usuario";
	public static final String SUPERVISOR = "SUPERVISOR";
	// This role implicitly allows access to all views.
	public static final String ADMIN = "admin";

	private Role() {
		// Static methods and fields only
	}

	public static String[] getAllRoles() {
		return new String[] { USUARIO, SUPERVISOR, ADMIN };
	}

}
