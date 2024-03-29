package com.gmail.cachorios.core.ui.view.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class WinUploadView extends Dialog {
    private Button cancelar;

    public WinUploadView(String filedir) {
        super();
        armarVentana(filedir);
    }

    private void armarVentana(String filedir) {
        setWidth("100%");
        setHeight("100%");
        setCloseOnEsc(true);

        final Image imagen = new Image();
        imagen.setSrc(filedir);
        //setSource(new FileResource(new File(Sistema.getSistema().getImagePath().concat(filename))));
        //imagen.setWidth("95%");
        //imagen.setHeight("90%");
        imagen.setAlt(filedir);
        imagen.setSizeFull();
        //imagen.setSizeUndefined();

        cancelar = new Button("OK", clickEvent -> close());
        cancelar.setWidth("120px");

        HorizontalLayout botonera = new HorizontalLayout();
        //botonera.setWidth("100%");
        //botonera.setHeight("30px");
        botonera.add(cancelar);
        botonera.setAlignSelf(FlexComponent.Alignment.CENTER, cancelar);

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.add(imagen, botonera);
        layout.setFlexGrow(1, botonera);
        layout.setFlexGrow(0, imagen);

        add(layout);
    }
}
