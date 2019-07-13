package com.gmail.cachorios.ui.views.errors;


import com.gmail.cachorios.ui.MainAppLayout;
import com.gmail.cachorios.ui.exceptions.AccessDeniedException;
import com.gmail.cachorios.ui.utils.LarConst;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;

import javax.servlet.http.HttpServletResponse;

@Tag("access-denied-view")
@HtmlImport("src/views/errors/access-denied-view.html")
@ParentLayout(MainAppLayout.class)
@PageTitle(LarConst.TITLE_ACCESS_DENIED)
public class AccessDeniedView extends PolymerTemplate<TemplateModel> implements HasErrorParameter<AccessDeniedException> {

	@Override
	public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<AccessDeniedException> errorParameter) {
		return HttpServletResponse.SC_FORBIDDEN;
	}
}
