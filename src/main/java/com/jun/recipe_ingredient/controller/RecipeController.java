package com.jun.recipe_ingredient.controller;

import com.jun.recipe_ingredient.dto.IngredientDto;
import com.jun.recipe_ingredient.dto.RecipeDto;
import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.model.Recipe;
import com.jun.recipe_ingredient.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller @RequiredArgsConstructor @RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "list";
    }

    @GetMapping ("/{id}")
    public String detail(@PathVariable Long id, Model model){
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredient", new Ingredient());
        return "detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("recipeDto", new RecipeDto());
        return "form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute RecipeDto recipeDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "form";
        Recipe recipe = Recipe.builder()
                .title(recipeDto.getTitle())
                .description(recipeDto.getDescription())
                .build();
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id,
                         @Valid @ModelAttribute("recipeDto")
                         RecipeDto recipeDto,
                         BindingResult bindingResult,
                         Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipeDto", recipeDto);
            return "form";
        }

//        Recipe recipe = recipeService.findById(id);
//        recipe.setTitle(recipeDto.getTitle());
//        recipe.setDescription(recipeDto.getDescription());
//        recipeService.saveRecipe(recipe);
        recipeService.updateRecipe(id, recipeDto);
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        recipeService.deleteRecipe(id);
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/ingredients/add")
    public String addIngredient(@PathVariable Long id,
                                @ModelAttribute Ingredient ingredient){

        recipeService.addIngredient(id, ingredient.getName());
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/ingredients/{ingId}/remove")
    public String removeIngredient(@PathVariable Long id,
                                   @PathVariable("ingID") Long ingredientId){
        recipeService.removeIngredient(id, ingredientId);
        return "redirect:/recipes/" + id;
    }
}
