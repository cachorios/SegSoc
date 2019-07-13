package com.gmail.cachorios.core.ui.data.util.converter;

import com.vaadin.flow.templatemodel.ModelEncoder;
import java.time.LocalDateTime;

import static com.gmail.cachorios.core.ui.data.util.DataProviderUtil.convertIfNotNull;
import static com.gmail.cachorios.core.ui.data.util.FormattingUtils.FULL_DATE_FORMATTER;

public class LocalDateTimeConverter implements ModelEncoder<LocalDateTime, String> {


	private static final LocalTimeConverter TIME_FORMATTER = new LocalTimeConverter();

	@Override
	public String encode(LocalDateTime modelValue) {
		return convertIfNotNull(modelValue,
				v -> FULL_DATE_FORMATTER.format(v) + " " + TIME_FORMATTER.encode(v.toLocalTime()));
	}

	@Override
	public LocalDateTime decode(String presentationValue) {
		throw new UnsupportedOperationException();
	}
}
