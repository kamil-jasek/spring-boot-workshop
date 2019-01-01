package pl.alx.workshop.expenses.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ExpenseForm {
	@NotNull
	private LocalDateTime date;
	@NotNull
	private BigDecimal amount;
	@NotBlank @Size(min = 3, max = 3)
	private String currency;
	@NotBlank
	private String category;
	private String name;
}
