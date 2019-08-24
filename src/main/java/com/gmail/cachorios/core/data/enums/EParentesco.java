package com.gmail.cachorios.core.data.enums;

/**
 * ETipoParametro es una enumeracion que contiene los distintas tipos de parametros que pueden existir
 * y persistir dentro de un sistema hecho par el framework. Esta enumeracion deberia ser utilizada dentro
 * de la clase Parametro para establecer su tipo (por ejemplo: RECURSO, MENU, etc.).
 *
 * @author jmfragueiro
 * @version 20161011
 */
public enum EParentesco {
    NINGUNO,    //0
    CONYUGE,    //1
    HERMANO,    //2
    HIJO,       //3
    PADRE       //4
}
