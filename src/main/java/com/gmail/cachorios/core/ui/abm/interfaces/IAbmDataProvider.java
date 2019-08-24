package com.gmail.cachorios.core.ui.abm.interfaces;


import com.gmail.cachorios.core.data.EntidadInterface;

public interface IAbmDataProvider {
	default void setPadre(EntidadInterface padre){};
	default EntidadInterface getPadre() {
		return null;
	}

}
