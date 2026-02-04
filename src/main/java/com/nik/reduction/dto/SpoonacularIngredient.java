package com.nik.reduction.dto;

public class SpoonacularIngredient {
    private int id;
    private String name;
    private double amount;
    private String unit;
    private String original;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getOriginal() { return original; }
    public void setOriginal(String original) { this.original = original; }
}
