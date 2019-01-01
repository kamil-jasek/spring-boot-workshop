package pl.alx.workshop.expenses.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
	@Id
	private String email;
	private String name;
}
