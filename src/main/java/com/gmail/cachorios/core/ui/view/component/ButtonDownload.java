package com.gmail.cachorios.core.ui.view.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ButtonDownload extends VerticalLayout {

    private InputStream ts;
    private Anchor downloadLink;

    public ButtonDownload() {
        setPadding(false);

        downloadLink = new Anchor("", "");
        downloadLink.getElement().setAttribute("download", true);
        downloadLink.getElement().setAttribute("margin", "0px");
        Button btn = new Button(new Icon(VaadinIcon.DOWNLOAD_ALT));
        btn.getElement().setAttribute("style", "width:40px;margin-left:1px;margin-bottom:1px");
        downloadLink.add(btn);
        add(downloadLink);
    }

    public void setFile(String directorio, String nombreArchivo){
        File file = new File(directorio);

        try {
            ts = new FileInputStream(file);
        }
        catch (Exception ex){}

        StreamResource streamResource = new StreamResource(nombreArchivo, () -> ts);

        downloadLink.setHref(streamResource);
        add(downloadLink);
    }
}
