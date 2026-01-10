package in.abhijeet.moneymanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.abhijeet.moneymanager.dto.IncomeDTO;
import in.abhijeet.moneymanager.service.IncomeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

private final IncomeService incomeService;
	
	@PostMapping
	public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO dto){
		IncomeDTO saved = incomeService.addIncome(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}
