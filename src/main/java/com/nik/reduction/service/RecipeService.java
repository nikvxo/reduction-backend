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
        validateRequest(request);
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

    private void validateRequest(RecipeRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required");
        }
        if (isBlank(request.getName())) {
            throw new IllegalArgumentException("Name is required");
        }
        if (isBlank(request.getInstructions())) {
            throw new IllegalArgumentException("Instructions are required");
        }
        if (request.getPrepTime() != null && request.getPrepTime() < 0) {
            throw new IllegalArgumentException("Prep time must be zero or greater");
        }
        if (request.getCookTime() != null && request.getCookTime() < 0) {
            throw new IllegalArgumentException("Cook time must be zero or greater");
        }
        if (request.getServings() == null) {
            throw new IllegalArgumentException("Servings are required");
        }
        if (request.getServings() < 1) {
            throw new IllegalArgumentException("Servings must be at least 1");
        }
        if (request.getIngredients() == null || request.getIngredients().isEmpty()) {
            throw new IllegalArgumentException("At least one ingredient is required");
        }
        for (RecipeRequest.IngredientRequest ingredient : request.getIngredients()) {
            if (ingredient == null) {
                throw new IllegalArgumentException("Ingredient is required");
            }
            if (isBlank(ingredient.getName())) {
                throw new IllegalArgumentException("Ingredient name is required");
            }
            if (isBlank(ingredient.getQuantity())) {
                throw new IllegalArgumentException("Ingredient quantity is required");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
