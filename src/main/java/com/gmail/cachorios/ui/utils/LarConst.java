package com.gmail.cachorios.ui.utils;

import org.springframework.data.domain.Sort;

import java.util.Locale;

public class LarConst {
    public static final Locale APP_LOCALE = Locale.US;

    public static final String PAGE_ROOT = "";
    public static final String PAGE_PERSONA = "persona";
    public static final String PAGE_PRODUCTO = "producto";
    public static final String PAGE_PARAMETRO = "parametro";
    public static final String PAGE_PLAN = "plan";
    public static final String PAGE_MOVIMIENTO = "movimiento";
    public static final String PAGE_MOVIMIENTODET = "detmovimiento";
    public static final String PAGE_DOCUMENTOS = "documentos";
    public static final String PAGE_USERS = "usuarios";
    
    public static final String APP_TITLE = "Seguimientos Acción Social";
    public static final String TITULO_USUARIOS = "Administració de Usuarios";
    public static final String TITULO_PERSONA = "Administración de Personas";
    public static final String TITULO_PRODUCTO = "Administración de Productos";
    public static final String TITULO_PARAMETRO = "Administración de Parametro";
    public static final String TITULO_PLAN = "Administración de Planes";
    public static final String TITULO_MOVIMIENTO = "Movimientos";
    public static final String TITULO_MOVIMIENTODET = "Detalles de movimientos";
    public static final String TITULO_DOCUMENTO = "Administración de Documentos";
    
    public static final String TITLE_LOGOUT = "Salir";
    public static final String TITLE_ACCESS_DENIED = "Acceso denegado";
    public static final String TITLE_NOT_FOUND = "No existe esta página";

    public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover";
    
    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    
    public static final int NOTIFICATION_DURATION = 4000;
}
