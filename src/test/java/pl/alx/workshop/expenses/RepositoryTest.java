package pl.alx.workshop.expenses;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.alx.workshop.expenses.dto.ExpensesByCategory;
import pl.alx.workshop.expenses.entity.Expense;
import pl.alx.workshop.expenses.entity.User;
import pl.alx.workshop.expenses.repository.ExpenseRepository;
import pl.alx.workshop.expenses.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
	
	private static final String TEST_USER = "kamil@test.com";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Test
	public void findUserByName() {
		List<User> users = userRepository.findByName("kamil");
		assertThat(users, is(not(empty())));
		assertThat(users.get(0).getName(), is(equalTo("kamil")));
	}
	
	@Test
	public void findUserExpenses() {
		List<Expense> expenses = expenseRepository.findAllByUser(TEST_USER);
		assertThat(expenses, is(not(empty())));
	}
	
	@Test
	public void testExpensesRepository() {
		
		// list all
		assertThat(expenseRepository.findAll(), is(not(empty())));
		
		// list all - paging
		Page<Expense> expenses = expenseRepository.findAll(PageRequest.of(0, 25));
		assertThat(expenses.getTotalPages(), is(equalTo(2)));
		
		// group by category, sum(amount) - HQL
		List<ExpensesByCategory> categoryExpenses = expenseRepository.groupByCategory(
				TEST_USER, LocalDateTime.now().minusDays(5), LocalDateTime.now());
		assertThat(categoryExpenses, is(not(empty())));
	}
}

