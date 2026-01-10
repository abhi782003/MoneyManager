package in.abhijeet.moneymanager.service;

import org.springframework.stereotype.Service;

import in.abhijeet.moneymanager.dto.ExpenseDTO;
import in.abhijeet.moneymanager.entity.CategoryEntity;
import in.abhijeet.moneymanager.entity.ExpenseEntity;
import in.abhijeet.moneymanager.entity.ProfileEntity;
import in.abhijeet.moneymanager.repository.CategoryRepository;
import in.abhijeet.moneymanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
	
	private final CategoryRepository categoryRepository ;
	private final ExpenseRepository expenseRepository;
	private final ProfileService profileService;
	
	// add new expense to database
	public ExpenseDTO addExpense(ExpenseDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity category = categoryRepository.findById(dto.getCategoryId())
			.orElseThrow(() -> new RuntimeException("Category not found"));
		ExpenseEntity newExpense = toEntity(dto, profile, category);
		 newExpense = expenseRepository.save(newExpense);
		 return toDTO(newExpense);
	}
	
	
	//helper methods
	private ExpenseEntity toEntity(ExpenseDTO dto, ProfileEntity profile, CategoryEntity category) {
		return ExpenseEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.amount(dto.getAmount())
				.date(dto.getDate())
				.profile(profile)
				.category(category)
				.build();
	}
	
	private ExpenseDTO toDTO(ExpenseEntity entity) {
		return ExpenseDTO.builder()
		.id(entity.getId())
		.name(entity.getName())
		.icon(entity.getIcon())
		.categoryId(entity.getId() != null ? entity.getCategory().getId(): null)
		.categoryName(entity.getCategory() != null ? entity.getCategory().getName(): "N/A")
		.amount(entity.getAmount())
		.date(entity.getDate())
		.createdAt(entity.getCreatedAt())
		.updatedAt(entity.getUpdatedAt())
		.build();
		
	}
	
}
