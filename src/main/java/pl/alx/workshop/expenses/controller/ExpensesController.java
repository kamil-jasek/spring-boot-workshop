package pl.alx.workshop.expenses.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
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

	@GetMapping({"/", "/home"})
	String home(Model model) {
		model.addAttribute("expenses", expensesRepo.findAllByUser("kamil@test.com"));
		return "expenses";
	}
	
	@GetMapping("/reports")
	String reports(Model model) throws JsonProcessingException {
		final int year = LocalDate.now().getYear();
		final int month = LocalDate.now().getMonth().getValue();
		String user = "kamil@test.com";
		final List<ExpensesByCategory> expenses = expensesService.getExpensesByCategory(user, year, month);
		model.addAttribute("groupedByCategories", mapper.writeValueAsString(expenses));
		
		final List<ExpensesByDate> groupedByDate = expensesService.getExpensesByDate(user, year, month);
		model.addAttribute("groupedByDate", mapper.writeValueAsString(groupedByDate));
		return "reports";
	}
}
