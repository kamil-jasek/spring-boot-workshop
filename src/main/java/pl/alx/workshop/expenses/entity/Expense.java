package pl.alx.workshop.expenses.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expenses")
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Expense {
	@EmbeddedId
	private ExpenseId id;
	private BigDecimal amount;
	private String currency;
	private String category;
	private String name;
}
