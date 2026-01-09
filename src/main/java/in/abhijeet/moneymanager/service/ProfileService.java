package in.abhijeet.moneymanager.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import in.abhijeet.moneymanager.dto.ProfileDTO;
import in.abhijeet.moneymanager.entity.ProfileEntity;
import in.abhijeet.moneymanager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final EmailService emailService;
	
	public ProfileDTO registerProfile(ProfileDTO profileDTO) {
		ProfileEntity newProfile = toEntity(profileDTO);
		newProfile.setActivationToken(UUID.randomUUID().toString());
		newProfile = profileRepository.save(newProfile);
		//send activation mail
		String activationLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfile.getActivationToken();
		String subject = "Activate your Money Manager account";
		String body = "Click on following link to activate your account: " + activationLink;
		emailService.sendEmail(newProfile.getEmail(), subject, body);
		
		return toDTO(newProfile);
	}
	
	
	public ProfileEntity toEntity(ProfileDTO profileDTO) {
		return ProfileEntity.builder()
				.id(profileDTO.getId())
				.fullName(profileDTO.getFullName())
				.email(profileDTO.getEmail())
				.password(profileDTO.getPassword())
				.profileImageUrl(profileDTO.getProfileImageUrl())
				.createdAt(profileDTO.getCreatedAt())
				.updatedAt(profileDTO.getUpdatedAt())
				.build();
		
	}
	
	
	public ProfileDTO toDTO(ProfileEntity profileEntity) {
	    return ProfileDTO.builder()
	            .id(profileEntity.getId())
	            .fullName(profileEntity.getFullName())
	            .email(profileEntity.getEmail()) 
	            .profileImageUrl(profileEntity.getProfileImageUrl())
	            .createdAt(profileEntity.getCreatedAt())
	            .updatedAt(profileEntity.getUpdatedAt())
	            .build();
	}
	
	public boolean activateProfile(String activationToken) {

	    return profileRepository.findByActivationToken(activationToken)
	            .map(profile -> {
	                profile.setIsActive(true);         // activate profile
	                profileRepository.save(profile);
	                return true;
	            })
	            .orElse(false);
	}

	
}
