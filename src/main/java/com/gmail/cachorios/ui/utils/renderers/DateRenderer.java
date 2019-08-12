package com.gmail.cachorios.ui.utils.renderers;

import com.vaadin.flow.data.renderer.BasicRenderer;
import com.vaadin.flow.function.ValueProvider;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateRenderer<SOURCE> extends BasicRenderer<SOURCE, Date> {
    private DateTimeFormatter formatter;
    private String nullRepresentation;

    public DateRenderer(ValueProvider<SOURCE, Date> valueProvider, String formatPattern) {
        super(valueProvider);

        if (formatPattern == null) {
            throw new IllegalArgumentException("format pattern may not be null");
        } else {
            this.formatter = DateTimeFormatter.ofPattern(formatPattern, Locale.getDefault());
        }
    }

    public DateRenderer<SOURCE> setNullRepresentation(String nullRepresentation) {
        this.nullRepresentation = nullRepresentation;
        return this;
    }

    public String getFormattedValue(Date date) {
        try {
            return date == null ? this.nullRepresentation : this.formatter.format(Fecha.toLocalDate(date));
        } catch (Exception var3) {
            throw new IllegalStateException(
                    "Could not format input date '" + date + "' using formatter '" + this.formatter + "'", var3);
        }
    }
}

