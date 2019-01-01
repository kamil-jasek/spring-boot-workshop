package pl.alx.workshop.expenses.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ExpensesFilter {
	private String user;
	private DateFilter dateFilter;
	private AmountFilter amountFilter;
	
	@NotBlank
	private String category;
}
