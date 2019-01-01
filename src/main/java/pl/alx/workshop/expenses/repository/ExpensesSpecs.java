package pl.alx.workshop.expenses.repository;

import static pl.alx.workshop.expenses.entity.ExpenseId_.date;
import static pl.alx.workshop.expenses.entity.ExpenseId_.email;
import static pl.alx.workshop.expenses.entity.Expense_.amount;
import static pl.alx.workshop.expenses.entity.Expense_.category;
import static pl.alx.workshop.expenses.entity.Expense_.id;

import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Preconditions;

import pl.alx.workshop.expenses.dto.AmountFilter;
import pl.alx.workshop.expenses.dto.DateFilter;
import pl.alx.workshop.expenses.dto.ExpensesFilter;
import pl.alx.workshop.expenses.entity.Expense;

public class ExpensesSpecs {
	
	public static Specification<Expense> withFilter(ExpensesFilter filter) {
		return (root, query, builder) -> {
			return Stream.of(
					filterUser(filter.getUser(), root, builder),
					filterAmount(filter.getAmountFilter(), root, builder),
					filterDate(filter.getDateFilter(), root, builder),
					filterCategory(filter.getCategory(), root, builder))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.reduce(builder.conjunction(), (acc, el) -> builder.and(acc, el));
		};
	}
	
	private static Optional<Predicate> filterUser(String user, Root<Expense> root, CriteriaBuilder builder) {
		Preconditions.checkNotNull(user);
		return Optional.of(builder.equal(root.get(id).get(email), user));
	}

	private static Optional<Predicate> filterAmount(AmountFilter filter, Root<Expense> root, CriteriaBuilder builder) {
		if (filter != null) {
			return Optional.of(builder.between(root.get(amount), filter.getFrom(), filter.getTo()));
		}
		return Optional.empty();
	}
	
	private static Optional<Predicate> filterDate(DateFilter filter, Root<Expense> root, CriteriaBuilder builder) {
		if (filter != null) {
			return Optional.of(builder.between(root.get(id).get(date), filter.getFrom(), filter.getTo()));
		}
		return Optional.empty();
	}
	
	private static Optional<Predicate> filterCategory(String categoryFilter, Root<Expense> root, CriteriaBuilder builder) {
		if (categoryFilter != null) {
			return Optional.of(builder.like(root.get(category), categoryFilter + "%"));
		}
		return Optional.empty();
	}
}
