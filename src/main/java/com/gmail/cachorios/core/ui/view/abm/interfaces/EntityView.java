package com.gmail.cachorios.core.ui.view.abm.interfaces;

import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.vaadin.flow.data.binder.ValidationException;

/**
 * Una visata master/detalle de <T>
 * La visata tiene una lista de entidades (Master) y un Dialogo
 * para mostrar una simmple entidad (detalle)
 * La vista tambien muestro notificaciones, mensajes de error y solucitud de notificaciones
 */

public interface EntityView<T> extends HasNotifications, HasConfirmation {

    default void showError(String message, boolean isPersistent) {
        showNotification(message, isPersistent);
    }

    boolean isDirty();


    void clear();


    void write(T entity) throws ValidationException;

    String getEntityName();

    default void showCreatedNotification() {
        showNotification(getEntityName() + " fue creado!");
    }

    default void showUpdatedNotification() {
        showNotification(getEntityName() + " fue actuazlizado!");
    }

    default void showDeletedNotification() {
        showNotification(getEntityName() + " fue eliminado!");
    }
    
    
    EntidadInterface getPadre();
}
