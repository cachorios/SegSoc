package com.gmail.cachorios.core.ui.view.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.shared.Registration;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.function.Function;

public class CustomUpload extends Upload implements Receiver {
    private Consumer<SucceededEvent> onOK;
    private Function<String, String> adapter;
    private String mimeType;

    public CustomUpload(@NotNull Consumer<SucceededEvent> onOK, @NotNull Function<String, String> adapter, Button boton,
                        String... aceptables) {
        super();

        if (boton != null) {
            setUploadButton(boton);
        }

        setDropAllowed(false);
        setDropLabelIcon(VaadinIcon.ERASER.create());
        setOnOK(onOK);
        setAutoUpload(true);
        setReceiver(this);
        addSucceededListener(this::uploadSucceeded);

        if (aceptables != null && aceptables.length > 0) {
            setAcceptedFileTypes(aceptables);
        }

        this.adapter = adapter;
    }

    private void setOnOK(Consumer<SucceededEvent> onOK) {
        this.onOK = onOK;
    }

    protected void uploadSucceeded(SucceededEvent event) {
        Notification.show("Documento cargado: " + event.getFileName());
        onOK.accept(event);
    }

    public OutputStream receiveUpload(String filename, String mimeType) {
        if (filename == null || filename.isEmpty()) {
            Notification.show("Documento vacio");
        }

        String nombre = adapter.apply(filename);
        this.mimeType = mimeType;

        try {
            File file = new File(nombre);
            return new FileOutputStream(file);
        } catch (FileNotFoundException fnfex) {
            Notification.show("No se encontro el document");
        }

        return null;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Consumer<SucceededEvent> getOnOK() {
        return onOK;
    }

    public Function<String, String> getAdapter() {
        return adapter;
    }

    public Registration addFileRemoveListener(ComponentEventListener<FileRemoveEvent> listener) {
        return super.addListener(FileRemoveEvent.class, listener);
    }

    @DomEvent("file-remove")
    public static class FileRemoveEvent extends ComponentEvent<Upload> {
        public FileRemoveEvent(Upload source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}
