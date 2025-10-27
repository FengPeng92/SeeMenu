package com.seemenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishInfo {
    private String name;
    private String description;
    private String price;
    private List<String> ingredients;
    private List<String> allergens;
    private List<String> dietaryInfo; // e.g., "vegetarian", "vegan", "gluten-free"
    private String nutritionalInfo;
}
