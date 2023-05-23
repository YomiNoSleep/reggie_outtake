package com.atyomi.boot.service.impl;

import com.atyomi.boot.common.R;
import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.domain.DishFlavor;
import com.atyomi.boot.dto.DishDto;
import com.atyomi.boot.mapper.DishMapper;
import com.atyomi.boot.service.DishFlavorService;
import com.atyomi.boot.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    public void saveWithFlavor(DishDto dishDto){
        this.saveOrUpdate(dishDto);
        for (DishFlavor flavor : dishDto.getFlavors()) {
            flavor.setDishId(dishDto.getId());
            dishFlavorService.saveOrUpdate(flavor);
        }
    }

    @Override
    public DishDto getOneDish(Long dishId) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getId,dishId);

        Dish one = this.getOne(queryWrapper);

        LambdaQueryWrapper<DishFlavor> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(DishFlavor::getDishId,dishId);

        List<DishFlavor> list = dishFlavorService.list(queryWrapper2);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(one,dishDto);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    public void deleteDishes(List<Long> ids) {
        dishFlavorService.removeByIds(ids);

        this.removeByIds(ids);
    }

}
