package com.gmail.cachorios.core.ui.abm.eventos;

import com.gmail.cachorios.core.data.EntidadInterface;
import com.gmail.cachorios.core.ui.abm.Abm;
import com.vaadin.flow.component.ComponentEvent;

public class SaveEvent extends ComponentEvent<Abm> {
	private EntidadInterface registroActivo;
	
	public SaveEvent(Abm source, boolean fromClient, EntidadInterface registroActivo) {
		super(source, fromClient);
		this.registroActivo = registroActivo;
	}
	
	public EntidadInterface getRegistroActivo() {
		return registroActivo;
	}
	public void setRegistroActivo(EntidadInterface registroActivo) {
		this.registroActivo = registroActivo;
	}
}

