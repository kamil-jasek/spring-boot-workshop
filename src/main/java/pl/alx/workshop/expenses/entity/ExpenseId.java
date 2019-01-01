package pl.alx.workshop.expenses.entity;

import static lombok.AccessLevel.PRIVATE;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor(access = PRIVATE)
public class ExpenseId implements Serializable {
	private String email;
	private LocalDateTime date;
}
