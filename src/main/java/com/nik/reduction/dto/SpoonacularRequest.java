package com.nik.reduction.dto;

import java.util.List;

public class SpoonacularRequest {
    private List<SpoonacularRecipe> results;
    private int totalResults;

    // Getters and setters
    public List<SpoonacularRecipe> getResults() { return results; }
    public void setResults(List<SpoonacularRecipe> results) { this.results = results; }
    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }
}
