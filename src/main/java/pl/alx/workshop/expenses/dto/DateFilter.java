package pl.alx.workshop.expenses.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class DateFilter {
	@NotNull
	private LocalDateTime from;
	@NotNull
	private LocalDateTime to;
}
