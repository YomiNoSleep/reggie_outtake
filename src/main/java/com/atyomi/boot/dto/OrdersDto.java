package com.atyomi.boot.dto;


import com.atyomi.boot.domain.OrderDetail;
import com.atyomi.boot.domain.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
