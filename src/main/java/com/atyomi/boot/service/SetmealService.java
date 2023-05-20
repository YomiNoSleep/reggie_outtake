package com.atyomi.boot.service;

import com.atyomi.boot.domain.Setmeal;
import com.atyomi.boot.dto.SetmealDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SetmealService extends IService<Setmeal> {
    void addSetmeal(SetmealDto setmealDto);

    SetmealDto getOneSetmeal(Long setmealId);
}
