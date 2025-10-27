package com.seemenu.dto;

import com.seemenu.model.DishInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuAnalysisResponse {
    private boolean success;
    private String message;
    private List<DishInfo> dishes;
}
