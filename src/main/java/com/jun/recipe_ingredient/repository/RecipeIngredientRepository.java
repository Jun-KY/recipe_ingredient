package com.jun.recipe_ingredient.repository;

import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
}
