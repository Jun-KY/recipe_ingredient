package com.jun.recipe_ingredient.repository;

import com.jun.recipe_ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    List<Ingredient> findByNameContainingIgnoreCase(String name);

    Optional<Ingredient> findByNameIgnoreCase(String name);

    Optional<Ingredient> findByName(String name);

    Optional<Ingredient> findById(Long id);
}
