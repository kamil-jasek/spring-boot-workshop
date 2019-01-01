package pl.alx.workshop.expenses.service;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static pl.alx.workshop.expenses.repository.ExpensesSpecs.withFilter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.alx.workshop.expenses.dto.DateFilter;
import pl.alx.workshop.expenses.dto.ExpenseForm;
import pl.alx.workshop.expenses.dto.ExpensesByCategory;
import pl.alx.workshop.expenses.dto.ExpensesByDate;
import pl.alx.workshop.expenses.dto.ExpensesFilter;
import pl.alx.workshop.expenses.entity.Expense;
import pl.alx.workshop.expenses.entity.ExpenseId;
import pl.alx.workshop.expenses.repository.ExpenseRepository;

@Service
@RequiredArgsConstructor
public class ExpensesService {

	private final ExpenseRepository expenseRepository;
	
	public List<ExpensesByCategory> getExpensesByCategory(String user, int year, int month) {
		final LocalDateTime initial = LocalDateTime.now()
				.withYear(year)
				.withMonth(month);
		final LocalDateTime start = initial.with(firstDayOfMonth());
		final LocalDateTime end = initial.with(lastDayOfMonth());
		return expenseRepository.groupByCategory(user, start, end);
	}
	
	public List<ExpensesByDate> getExpensesByDate(String user, int year, int month) {
		final LocalDateTime initial = LocalDateTime.now()
				.withYear(year)
				.withMonth(month);
		final LocalDateTime start = initial.with(firstDayOfMonth());
		final LocalDateTime end = initial.with(lastDayOfMonth());
		return expenseRepository.findAll(withFilter(ExpensesFilter.builder()
					.user(user)
					.dateFilter(new DateFilter(start, end))
					.build()))
				.stream()
				.collect(groupingBy(el -> el.getId().getDate().toLocalDate(), toList()))
				.entrySet()
				.stream()
				.map(entry -> new ExpensesByDate(entry.getKey(), entry.getValue()
						.stream()
						.map(el -> el.getAmount())
						.reduce(BigDecimal.ZERO, BigDecimal::add)))
				.collect(toList());
	}
	
	@Transactional
	public void save(String user, ExpenseForm expense) {
		expenseRepository.save(Expense.builder()
				.id(new ExpenseId(user, expense.getDate()))
				.amount(expense.getAmount())
				.category(expense.getCategory())
				.currency(expense.getCurrency())
				.name(expense.getName())
				.build());
	}
}
