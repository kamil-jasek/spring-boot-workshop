package pl.alx.workshop.expenses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import pl.alx.workshop.expenses.repository.ExpenseRepository;

@Controller
@RequiredArgsConstructor
public class ExpensesController {
	
	private final ExpenseRepository expensesRepo;

	@GetMapping({"/", "/home"})
	String home(Model model) {
		model.addAttribute("expenses", expensesRepo.findAllByUser("kamil@test.com"));
		return "expenses";
	}
}
