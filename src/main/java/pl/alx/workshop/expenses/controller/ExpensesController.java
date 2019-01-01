package pl.alx.workshop.expenses.controller;

import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import pl.alx.workshop.expenses.dto.ExpenseForm;
import pl.alx.workshop.expenses.dto.ExpensesByCategory;
import pl.alx.workshop.expenses.dto.ExpensesByDate;
import pl.alx.workshop.expenses.repository.ExpenseRepository;
import pl.alx.workshop.expenses.service.ExpensesService;

@Controller
@RequiredArgsConstructor
public class ExpensesController {
	
	private final ExpenseRepository expensesRepo;
	private final ExpensesService expensesService;
	private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
	
	@GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping({"/", "/home"})
	String home(Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("expenses", expensesRepo.findAllByUser(user.getUsername()));
		return "expenses";
	}
	
	@GetMapping("/reports")
	String reports(@AuthenticationPrincipal User user, Model model) throws JsonProcessingException {
		final int year = LocalDate.now().getYear();
		final int month = LocalDate.now().getMonth().getValue();
		final List<ExpensesByCategory> expenses = expensesService.getExpensesByCategory(user.getUsername(), year, month);
		model.addAttribute("groupedByCategories", mapper.writeValueAsString(expenses));
		
		final List<ExpensesByDate> groupedByDate = expensesService.getExpensesByDate(user.getUsername(), year, month);
		model.addAttribute("groupedByDate", mapper.writeValueAsString(groupedByDate));
		return "reports";
	}
	
	@ModelAttribute("currencies")
	List<String> currencies() {
		return asList("PLN", "EUR", "DOL");
	}
	
	@ModelAttribute("categories")
	List<String> categories() {
		return asList("Mieszkanie", "Zakupy", "Samochód", "Rozrywka");
	}
	
	@GetMapping("/new-expense")
	String newExpense(@ModelAttribute("expense") ExpenseForm expense) {
		return "new-expense";
	}
	
	@PostMapping("/new-expense")
	String createExpense(@AuthenticationPrincipal User user, @Valid @ModelAttribute("expense") ExpenseForm expense, BindingResult result) {
		if (result.hasErrors()) {
			return "new-expense";
		}
		expensesService.save(user.getUsername(), expense);
		return "redirect:/home";
	}
}
