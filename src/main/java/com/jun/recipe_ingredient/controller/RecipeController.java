package com.jun.recipe_ingredient.controller;

import com.jun.recipe_ingredient.dto.IngredientDto;
import com.jun.recipe_ingredient.dto.RecipeDto;
import com.jun.recipe_ingredient.dto.RecipeIngredientDto;
import com.jun.recipe_ingredient.model.Ingredient;
import com.jun.recipe_ingredient.model.Recipe;
import com.jun.recipe_ingredient.service.IngredientService;
import com.jun.recipe_ingredient.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller @RequiredArgsConstructor @RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    @GetMapping
    public String list(Model model) {
        model.addAttribute("recipes", recipeService.findAll());
        return "list";
    }

    @GetMapping ("/{id}")
    public String detail(@PathVariable Long id, Model model){
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredientForm", new RecipeIngredientDto());

        List<Ingredient> allIngredients = ingredientService.findAll();
        model.addAttribute("allIngredients", allIngredients);

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

    @GetMapping("/{id}/edit")
    public String editRecipe(@PathVariable Long id, Model model){
        Recipe recipe = recipeService.findById(id);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setTitle(recipe.getTitle());
        recipeDto.setDescription(recipe.getDescription());
        model.addAttribute("recipeDto", recipeDto);
        return "form";
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
                                @ModelAttribute("ingredientForm")
                                @Valid RecipeIngredientDto ingredientForm,
                                BindingResult bindingResult,
                                Model model){
        if(bindingResult.hasErrors()){
            Recipe recipe = recipeService.findById(id);
            model.addAttribute("recipe", recipe);
            model.addAttribute("allIngredients", ingredientService.findAll());
            return "detail";
        }
        try{
            recipeService.addIngredient(id, ingredientForm.getIngredientId(), ingredientForm.getAmount(), ingredientForm.getUnit());
        }catch (IllegalArgumentException e){
            bindingResult.reject("duplicate", e.getMessage());
            Recipe recipe = recipeService.findById(id);
            model.addAttribute("recipe", recipe);
            return "detail";
        }
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/{id}/ingredients/{recipeIngredientId}/remove")
    public String removeIngredient(@PathVariable Long id,
                                   @PathVariable Long recipeIngredientId){
        recipeService.removeIngredient(recipeIngredientId);
        return "redirect:/recipes/" + id;
    }
}
