package com.gmail.cachorios.app.seguridad;

import com.gmail.cachorios.core.ui.component.OfflineBanner;
import com.gmail.cachorios.ui.exceptions.AccessDeniedException;
import com.gmail.cachorios.ui.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent ->{
			final UI ui = uiEvent.getUI();
			ui.add(new OfflineBanner());
		});
		
	}
	/**
	 * Reroutes the user if she is not authorized to access the view.
	 *
	 * @param event
	 *            before navigation event with event details
	 */
	private void beforeEnter(BeforeEnterEvent event) {
		final boolean accessGranted = SecurityUtils.isAccessGranted(event.getNavigationTarget());
		if (!accessGranted) {
			if (SecurityUtils.isUserLoggedIn()) {
				event.rerouteToError(AccessDeniedException.class);
			} else {
				event.rerouteTo(LoginView.class);
			}
		}
	}
	
	
}
