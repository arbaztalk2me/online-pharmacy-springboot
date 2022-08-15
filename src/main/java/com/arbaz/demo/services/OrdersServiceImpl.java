package com.arbaz.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arbaz.demo.models.Orders;
import com.arbaz.demo.repositories.OrdersRepository;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	OrdersRepository orderRepository;
	
	@Override
	public Orders setOrders(Orders ord) {
		Orders ords=orderRepository.save(ord);
		return ords;
	}

	@Override
	public List<Orders> findByUser(int id) {
		List<Orders> list=orderRepository.findByUser_id(id);
		return list;
	}

	@Override
	public List<Orders> findByOrderStatus() {
		List<Orders> list=orderRepository.findByPending();
		return list;
	}

	@Override
	public Orders findById(int id) {
		
		return orderRepository.findById(id).get();
	}

	@Override
	public List<Orders> findAllUndeliveryOrder(int did) {
		
		return orderRepository.findByDelivery(did);
	}

	@Override
	public List<Orders> findAllDeliveryOrder(int did) {
		// TODO Auto-generated method stub
		return orderRepository.findByDeliveryConfirm(did);
	}

}
