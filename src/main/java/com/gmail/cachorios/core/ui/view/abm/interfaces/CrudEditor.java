package com.gmail.cachorios.core.ui.view.abm.interfaces;

/*
 * #%L
 * Vaadin Crud for Vaadin 10
 * %%
 * Copyright (C) 2018 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 * 
 * See the file license.html distributed with this software for more
 * information about licensing.
 * 
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 * #L%
 */

import com.vaadin.flow.component.Component;

import java.io.Serializable;

/**
 * Interface representing a crud editor.
 *
 * @param <E> the bean type
 */
public interface CrudEditor<E> extends Serializable {

    /**
     * Sets an item to be edited.
     * This could be a newly instantiated item or an existing item from the grid.
     * Initial validation will be skipped.
     *
     * @param item the item to edit
     * @see #setItem(Object, boolean)
     */
    default void setItem(E item) {
        setItem(item, false);
    }

    /**
     * Sets an item to be edited.
     * This could be a newly instantiated item or an existing item from the grid.
     *
     * @param item the item to edit
     * @param validate if true the item will be validated immediately
     */
    void setItem(E item, boolean validate);

    /**
     * Returns the item being edited.
     *
     * @return the item being edited
     */
    E getItem();

    /**
     * Clears the editor.
     */
    void clear();

    /**
     * Checks whether the data entered into an editor is valid.
     *
     * @return true if valid or false if otherwise
     */
    boolean isValid();

    /**
     * Writes any pending input update (if any) to the item.
     */
    void writeItemChanges();

    /**
     * Returns the user interface of an editor.
     *
     * @return the user interface
     */
    Component getView();
}
