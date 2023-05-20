package com.atyomi.boot.service.impl;

import com.atyomi.boot.common.R;
import com.atyomi.boot.common.exceptions.CustomException;
import com.atyomi.boot.domain.Category;
import com.atyomi.boot.domain.Dish;
import com.atyomi.boot.domain.Setmeal;
import com.atyomi.boot.mapper.CategoryMapper;
import com.atyomi.boot.service.CategoryService;
import com.atyomi.boot.service.DishService;
import com.atyomi.boot.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryMapper mapper;
    @Override
    public void removeById(Long id){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,id);

        LambdaQueryWrapper<Setmeal> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Setmeal::getCategoryId,id);


        List<Dish> d = dishService.list(queryWrapper);
        List<Setmeal> s = setmealService.list(queryWrapper2);
        if(s.size() > 0 ){
            throw new CustomException("有关联套餐，无法删除");
        }
        if(d.size() > 0){
            throw new CustomException("有关联菜品，无法删除");
        }
        super.removeById(id);
    }
}
