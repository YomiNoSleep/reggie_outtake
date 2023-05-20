package com.atyomi.boot.service;

import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.dto.DishDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);
    public DishDto getOneDish(Long dishId);
    public void deleteDishes(List<Long> ids);
}
