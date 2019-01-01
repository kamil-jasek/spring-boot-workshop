package pl.alx.workshop.expenses.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pl.alx.workshop.expenses.config.mvc.formatters.AmountFormatter;
import pl.alx.workshop.expenses.config.mvc.formatters.LocalDateTimeFormatter;

@Configuration
class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new LocalDateTimeFormatter());
		registry.addFormatter(new AmountFormatter());
	}
}
