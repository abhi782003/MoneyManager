package in.abhijeet.moneymanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.abhijeet.moneymanager.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
	
	//select * from tbl_categories where profile_id = ?
    List<CategoryEntity> findByProfileId(Long profileId);
    
    //select * from tbl_categories where id = ?1 and profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);  
    
    //select * from tbl_categories where type = ?1 and profile_id = ?2
    List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);
    
    
    //one user cannot create multiple category. DO NOT ADD/USE @Unique in CategoryEntity . cause multiple user can make same category eg. bills but single user
    //should not make same categories
    Boolean existsByNameAndProfileId(String name, Long profileId);

}
