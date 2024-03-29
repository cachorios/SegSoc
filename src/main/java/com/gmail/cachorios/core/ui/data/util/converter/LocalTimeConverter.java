package com.gmail.cachorios.core.ui.data.util.converter;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.templatemodel.ModelEncoder;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static com.gmail.cachorios.core.ui.data.util.DataProviderUtil.convertIfNotNull;
import static com.gmail.cachorios.core.ui.data.util.FormattingUtils.HOUR_FORMATTER;


public class LocalTimeConverter implements ModelEncoder<LocalTime, String>, Converter<String, LocalTime> {

	@Override
	public String encode(LocalTime modelValue) {
		return convertIfNotNull(modelValue, HOUR_FORMATTER::format);
	}

	@Override
	public LocalTime decode(String presentationValue) {
		return convertIfNotNull(presentationValue, p -> LocalTime.parse(p, HOUR_FORMATTER));
	}

	@Override
	public Result<LocalTime> convertToModel(String value, ValueContext context) {
		try {
			return Result.ok(decode(value));
		} catch (DateTimeParseException e) {
			return Result.error("Invalid time");
		}
	}

	@Override
	public String convertToPresentation(LocalTime value, ValueContext context) {
		return encode(value);
	}

}
