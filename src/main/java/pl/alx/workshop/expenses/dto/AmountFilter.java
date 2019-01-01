package pl.alx.workshop.expenses.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class AmountFilter {
	@NotNull
	private BigDecimal from;
	@NotNull
	private BigDecimal to;
}
