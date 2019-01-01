package pl.alx.workshop.expenses.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.alx.workshop.expenses.dto.ExpensesByCategory;
import pl.alx.workshop.expenses.entity.Expense;
import pl.alx.workshop.expenses.entity.ExpenseId;

public interface ExpenseRepository extends JpaRepository<Expense, ExpenseId> {
	
	@Query("from Expense e where e.id.email = ?1")
	List<Expense> findAllByUser(String email);
	
	@Query("select new pl.alx.workshop.expenses.dto.ExpensesByCategory(e.category, sum(e.amount)) "
			+ "from Expense e where e.id.email = :user and e.id.date between :from and :to "
			+ "group by e.category")
	List<ExpensesByCategory> groupByCategory(String user, LocalDateTime from, LocalDateTime to);
}
