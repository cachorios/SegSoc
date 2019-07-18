package com.gmail.cachorios.core.ui.data.util.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class BooleanConverter implements Converter<String, Boolean> {
    @Override
    public Result<Boolean> convertToModel(String s, ValueContext valueContext) {
        try {
            return Result.ok( Boolean.parseBoolean(s)   );
        } catch (Exception e) {
            return Result.error("Valor Invalido");
        }

    }

    @Override
    public String convertToPresentation(Boolean bol, ValueContext valueContext) {
        return bol == null ? "" : bol.toString();

    }
}

