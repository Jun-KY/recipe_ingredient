package com.jun.recipe_ingredient.service;
import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.model.Recipe;
import com.jun.recipe_ingredient.repository.IngredientRepository;
import com.jun.recipe_ingredient.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service @RequiredArgsConstructor @Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe findById(Long id){
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not exist"));
    }

    public Recipe saveRecipe (Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe (Long id){
        recipeRepository.deleteById(id);
    }

    public Recipe updateRecipe (Long id, Recipe recipe){
        Recipe existRecipe = findById(id);

        existRecipe.setTitle(recipe.getTitle());

        existRecipe.setDescription(recipe.getDescription());

        return recipeRepository.save(existRecipe);
    }

    @Transactional
    public void addIngredient(Long recipeId, String ingredientName){
        Recipe recipe = findById(recipeId);

        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                .orElseGet(() -> ingredientRepository.save(Ingredient.builder().name(ingredientName).build()));

        recipe.getIngredients().add(ingredient);
//        recipeRepository.save(recipe);
    }

    @Transactional
    public void removeIngredient(Long recipeId, Long ingredientId){
        Recipe recipe = findById(recipeId);

        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new NoSuchElementException("No ingredient found"));
        recipe.getIngredients().remove(ingredient);
//        recipeRepository.save(recipe);
    }

}
