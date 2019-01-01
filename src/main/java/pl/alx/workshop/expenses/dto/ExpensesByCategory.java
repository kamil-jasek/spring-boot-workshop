package pl.alx.workshop.expenses.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesByCategory {
	private String category;
	private BigDecimal amount;
}
