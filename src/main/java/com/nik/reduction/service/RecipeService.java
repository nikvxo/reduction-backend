package com.nik.reduction.service;

import com.nik.reduction.dto.RecipeRequest;
import com.nik.reduction.model.Ingredient;
import com.nik.reduction.model.Recipe;
import com.nik.reduction.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional
    public Recipe createRecipe(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setDescription(request.getDescription());
        recipe.setPrepTime(request.getPrepTime());
        recipe.setCookTime(request.getCookTime());
        recipe.setInstructions(request.getInstructions());

        // Add ingredients using helper method
        if (request.getIngredients() != null) {
            request.getIngredients().forEach(ingredientReq -> {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(ingredientReq.getName());
                ingredient.setQuantity(ingredientReq.getQuantity());
                recipe.addIngredient(ingredient);
            });
        }

        return recipeRepository.save(recipe);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
