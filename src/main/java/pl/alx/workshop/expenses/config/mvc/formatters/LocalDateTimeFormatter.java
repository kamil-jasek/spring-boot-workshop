package pl.alx.workshop.expenses.config.mvc.formatters;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Override
	public String print(LocalDateTime object, Locale locale) {
		return object.format(FORMATTER);
	}

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		return LocalDateTime.parse(text, FORMATTER);
	}

}
