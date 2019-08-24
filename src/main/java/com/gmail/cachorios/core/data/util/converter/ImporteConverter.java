package com.gmail.cachorios.core.data.util.converter;

import static com.gmail.cachorios.core.data.util.DataProviderUtil.convertIfNotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import com.gmail.cachorios.core.data.util.FormattingUtils;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;




public class ImporteConverter implements Converter<String, Double> {

	private final DecimalFormat df = FormattingUtils.getUiPriceFormatter();

	@Override
	public Result<Double> convertToModel(String presentationValue, ValueContext valueContext) {
		try {
			return Result.ok( df.parse(presentationValue).doubleValue());
			//return Result.ok((Double) Math.round(df.parse(presentationValue).doubleValue() ));
		} catch (ParseException e) {
			return Result.error("Invalid value");
		}
	}

	@Override
	public String convertToPresentation(Double modelValue, ValueContext valueContext) {
		//return convertIfNotNull(modelValue, i -> df.format(BigDecimal.valueOf(i, 2)), () -> "");
		return convertIfNotNull(modelValue, i -> df.format(new BigDecimal(i)), () -> "0.00");
	}
}