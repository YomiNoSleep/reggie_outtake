package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.ShoppingCart;
import com.atyomi.boot.mapper.ShoppingCartMapper;
import com.atyomi.boot.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
