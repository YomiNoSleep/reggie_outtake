package com.atyomi.boot.service.impl;

import com.atyomi.boot.domain.OrderDetail;
import com.atyomi.boot.mapper.OrderDetailMapper;
import com.atyomi.boot.service.OrderDetailService;
import com.atyomi.boot.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
