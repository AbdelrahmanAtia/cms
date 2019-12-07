package org.javaworld.cmsbackend.dao;

import org.javaworld.cmsbackend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Page<Category> findAll(Pageable pageable);

	Page<Category> findByNameIgnoreCaseContaining(String name, Pageable pageable);

	@Modifying
	@Query("UPDATE Category c SET c.image = null WHERE c.id = :categoryId")
	public int deleteCategoryImage(@Param("categoryId") int categoryId);

}
