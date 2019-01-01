package pl.alx.workshop.expenses.config.mvc.formatters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class AmountFormatter implements Formatter<BigDecimal> {

	@Override
	public String print(BigDecimal object, Locale locale) {
		NumberFormat format = NumberFormat.getInstance(locale);
		format.setMinimumFractionDigits(2);
		return format.format(object);
	}

	@Override
	public BigDecimal parse(String text, Locale locale) throws ParseException {
		DecimalFormatSymbols formatSymbols = DecimalFormatSymbols.getInstance(locale);
		DecimalFormat format = new DecimalFormat("#.#", formatSymbols);
		format.setParseBigDecimal(true);
		return (BigDecimal) format.parse(text);
	}

}
