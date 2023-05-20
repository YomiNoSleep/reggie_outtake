package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.Setmeal;
import com.atyomi.boot.domain.SetmealDish;
import com.atyomi.boot.dto.SetmealDto;
import com.atyomi.boot.mapper.SetmealMapper;
import com.atyomi.boot.service.SetmealDishService;
import com.atyomi.boot.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void addSetmeal(SetmealDto setmealDto) {
        this.saveOrUpdate(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDto getOneSetmeal(Long setmealId) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Setmeal::getId,setmealId);
        Setmeal one = this.getOne(queryWrapper);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(one,setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(SetmealDish::getSetmealId,setmealId);
        List<SetmealDish> list = setmealDishService.list(queryWrapper2);

        setmealDto.setSetmealDishes(list);
        return setmealDto;

    }

}
