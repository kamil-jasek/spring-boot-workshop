package pl.alx.workshop.expenses.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Value;

@Value
public class ExpensesByDate {
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private BigDecimal amount;
}
