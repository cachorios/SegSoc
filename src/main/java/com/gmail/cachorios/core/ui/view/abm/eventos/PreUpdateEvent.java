package com.gmail.cachorios.core.ui.view.abm.eventos;

import com.gmail.cachorios.core.ui.data.EntidadInterface;
import com.gmail.cachorios.core.ui.view.abm.Abm;
import com.vaadin.flow.component.ComponentEvent;

public class PreUpdateEvent extends ComponentEvent<Abm> {
	private EntidadInterface registroActivo;
	
	public PreUpdateEvent(Abm source, boolean fromClient, EntidadInterface registroActivo) {
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

