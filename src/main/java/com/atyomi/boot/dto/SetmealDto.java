package com.atyomi.boot.dto;


import com.atyomi.boot.domain.Setmeal;
import com.atyomi.boot.domain.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
