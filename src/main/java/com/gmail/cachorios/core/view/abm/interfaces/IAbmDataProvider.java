package com.gmail.cachorios.core.ui.view.abm.interfaces;


import com.gmail.cachorios.core.ui.data.EntidadInterface;

public interface IAbmDataProvider {
	default void setPadre(EntidadInterface padre){};
	default EntidadInterface getPadre() {
		return null;
	}

}
