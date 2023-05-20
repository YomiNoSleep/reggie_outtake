package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.DishFlavor;
import com.atyomi.boot.mapper.DishFlavorMapper;
import com.atyomi.boot.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
