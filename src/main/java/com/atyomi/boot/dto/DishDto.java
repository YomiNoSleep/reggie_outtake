package com.atyomi.boot.dto;


import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.domain.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
