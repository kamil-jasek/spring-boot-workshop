package pl.alx.workshop.expenses.util;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.BaseProducer;

import lombok.RequiredArgsConstructor;
import pl.alx.workshop.expenses.entity.Expense;
import pl.alx.workshop.expenses.entity.ExpenseId;
import pl.alx.workshop.expenses.entity.User;

@Component
@RequiredArgsConstructor
class ExampleDataLoader implements ApplicationListener<ApplicationReadyEvent> {
	
	private final EntityManager entityManager;

	@Transactional
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		final User user = new User("kamil@test.com", "kamil");
		entityManager.persist(user);
		
		final LocalDateTime now = LocalDateTime.now();
		final LocalDate from = now.with(firstDayOfMonth()).toLocalDate();
		final LocalDate to = now.with(lastDayOfMonth()).toLocalDate();
		
		final Fairy fairy = Fairy.create();
		final BaseProducer producer = fairy.baseProducer();
		final List<Pair<String, List<String>>> categories = asList(
				Pair.of("Mieszkanie", asList("Czynsz", "Kredyt", "Remont")), 
				Pair.of("Samochód", asList("Naprawa", "Paliwo")), 
				Pair.of("Zakupy", asList("Jedzenie", "Chemia domowa", "Inne")), 
				Pair.of("Rozrywka", asList("Kino", "Teatr", "Restauracje")));
		
		IntStream.range(0, 50).forEach(i -> {
			Pair<String, List<String>> category = producer.randomElement(categories);
			entityManager.persist(Expense.builder()
					.id(new ExpenseId(user.getEmail(), fairy.dateProducer()
							.randomDateBetweenTwoDates(from, to)
							.atTime(producer.randomBetween(0, 23), 
									producer.randomBetween(0, 59))))
					.amount(BigDecimal.valueOf(producer.randomBetween(1, 500)))
					.currency("PLN")
					.category(category.getFirst())
					.name(producer.randomElement(category.getSecond()))
					.build());
		});
	}
}
