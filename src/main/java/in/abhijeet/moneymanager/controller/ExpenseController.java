package in.abhijeet.moneymanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.abhijeet.moneymanager.dto.ExpenseDTO;
import in.abhijeet.moneymanager.service.ExpenseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

	private final ExpenseService expenseService;
	
	@PostMapping
	public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO dto){
		ExpenseDTO saved = expenseService.addExpense(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		
	}
}
