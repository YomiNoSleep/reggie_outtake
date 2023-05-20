package com.atyomi.boot.service;

import com.atyomi.boot.domain.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.core.annotation.Order;

public interface OrderService extends IService<Orders> {
    void saveOrder(Orders orders);
}
