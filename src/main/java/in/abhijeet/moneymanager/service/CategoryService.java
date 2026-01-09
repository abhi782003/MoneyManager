package in.abhijeet.moneymanager.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.abhijeet.moneymanager.dto.CategoryDTO;
import in.abhijeet.moneymanager.entity.CategoryEntity;
import in.abhijeet.moneymanager.entity.ProfileEntity;
import in.abhijeet.moneymanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final ProfileService profileService;
	private  CategoryRepository categoryRepository;
	
	public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
		ProfileEntity profile = profileService.getCurrentProfile();
		if(categoryRepository.existsByNameAndProfileId(categoryDTO.getName(), profile.getId())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with name already exist");
		}
		
		CategoryEntity newCategory = toEntity(categoryDTO, profile);
		newCategory = categoryRepository.save(newCategory);
		return toDTO(newCategory);
	}
	
	//helper methods
    private CategoryEntity toEntity(CategoryDTO categoryDTO, ProfileEntity profile) {
        return CategoryEntity.builder()
                .name(categoryDTO.getName())
                .icon(categoryDTO.getIcon())
                .profile(profile)
                .type(categoryDTO.getType())
                .build();
    }

    private CategoryDTO toDTO(CategoryEntity entity) {
        return CategoryDTO.builder()
                .id(entity.getId())
                .profileId(entity.getProfile() != null ?  entity.getProfile().getId(): null)
                .name(entity.getName())
                .icon(entity.getIcon())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .type(entity.getType())
                .build();

    }

}
