package com.jun.recipe_ingredient.repository;

import com.jun.recipe_ingredient.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {



}
