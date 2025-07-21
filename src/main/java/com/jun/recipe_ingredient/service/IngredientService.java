package com.jun.recipe_ingredient.service;

import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service @Transactional @RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAll(){
        return ingredientRepository.findAll(Sort.by("name").ascending());
    }

    public List<Ingredient> search(String query){
        return ingredientRepository.findByNameContainingIgnoreCase(query);
    }

    public Ingredient findById(Long id){
        return ingredientRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No ingredient found"));
    }

    public boolean existsByName(String name){
        return ingredientRepository.existsByNameIgnoreCase(name);
    }

    public boolean existsByName(String name, Long excludeId){
        return ingredientRepository.existsByNameIgnoreCaseAndIdNot(name, excludeId);
    }

    public Ingredient saveIngredient(Ingredient ingredient){
        return ingredientRepository.save(ingredient);
    }


    public void update(Long id, String newName){
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingredient not found"));
        ingredient.setName(newName);
        ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id){
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingredient not found"));
        if (ingredient.getRecipeIngredients() !=null && !ingredient.getRecipeIngredients().isEmpty()){
            throw new IllegalStateException("Cannot delete: Ingredient is used in Recipes");
        }
        ingredientRepository.deleteById(id);
    }
}
