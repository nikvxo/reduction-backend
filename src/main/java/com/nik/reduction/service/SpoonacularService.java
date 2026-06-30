package com.nik.reduction.service;
import com.nik.reduction.dto.SpoonacularRecipe;
import com.nik.reduction.exception.SpoonacularApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Arrays;
import java.util.List;
@Service
public class SpoonacularService {
    private final RestTemplate restTemplate;
    @Value("${spoonacular.api.key}")
    private String apiKey;
    @Value("${spoonacular.api.base-url}")
    private String baseUrl;
    public SpoonacularService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<SpoonacularRecipe> searchRecipesByIngredients(String ingredients, int number) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/recipes/findByIngredients")
                .queryParam("ingredients", ingredients)
                .queryParam("number", number)
                .queryParam("apiKey", apiKey)
                .toUriString();
        try {
            SpoonacularRecipe[] recipes = restTemplate.getForObject(url, SpoonacularRecipe[].class);
            return recipes == null ? List.of() : Arrays.asList(recipes);
        } catch (RestClientException ex) {
            throw new SpoonacularApiException("Failed to fetch recipes from Spoonacular", ex);
        }
    }
}
