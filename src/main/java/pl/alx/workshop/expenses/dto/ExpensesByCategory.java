package pl.alx.workshop.expenses.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesByCategory {
	@JsonProperty("label")
	private String category;
	@JsonProperty("value")
	private BigDecimal amount;
}
