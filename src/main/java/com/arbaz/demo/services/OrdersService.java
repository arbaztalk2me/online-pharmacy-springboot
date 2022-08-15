package com.arbaz.demo.services;

import java.util.List;

import com.arbaz.demo.models.Orders;

public interface OrdersService {
	Orders setOrders(Orders ord);
	List<Orders> findByUser(int id);
	List<Orders> findByOrderStatus();
	Orders findById(int id);
	List<Orders> findAllUndeliveryOrder(int did);
	List<Orders> findAllDeliveryOrder(int did);
	
}
