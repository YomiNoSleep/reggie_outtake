package com.atyomi.boot.service.impl;

import com.atyomi.boot.common.BaseContext;
import com.atyomi.boot.domain.AddressBook;
import com.atyomi.boot.domain.OrderDetail;
import com.atyomi.boot.domain.Orders;
import com.atyomi.boot.domain.ShoppingCart;
import com.atyomi.boot.mapper.OrderMapper;
import com.atyomi.boot.service.AddressBookService;
import com.atyomi.boot.service.OrderDetailService;
import com.atyomi.boot.service.OrderService;
import com.atyomi.boot.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Override
    public void saveOrder(Orders orders) {
        //设置用户ID
        Long userId = BaseContext.threadLocal.get();
        orders.setUserId(userId);


        //获取具体地址信息
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getId,orders.getAddressBookId());
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        //设置相关信息
        orders.setAddress(addressBook.getDetail());
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setStatus(2);
        //设置下单时间
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        this.save(orders);
        orders.setNumber(orders.getId().toString());

        //保存相关OrderDetail信息
        LambdaQueryWrapper<ShoppingCart> shoppingCartListQuery = new LambdaQueryWrapper<>();
        shoppingCartListQuery.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> list = shoppingCartService.list(shoppingCartListQuery);
        //每一条购物车信息对应一个orderDetail
        list.forEach(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orders.getId());
            BeanUtils.copyProperties(item, orderDetail, "id");
            orderDetailService.save(orderDetail);
        });

        //清空购物车
        shoppingCartService.remove(shoppingCartListQuery);
    }
}
