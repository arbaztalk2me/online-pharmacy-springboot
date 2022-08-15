package com.arbaz.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arbaz.demo.models.Orders;
import com.arbaz.demo.models.User;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
	public List<Orders> findByUser_id(int id);
	
	@Query("SELECT u FROM Orders u WHERE u.order_status = 'pending' ")
	public List<Orders> findByPending();
	
	@Query("SELECT u FROM Orders u WHERE u.order_status = 'confirm' and u.delivery_status='Not Delivered' and u.did= :did ")
	public List<Orders> findByDelivery(@Param("did") int did);
	
	@Query("SELECT u FROM Orders u WHERE u.order_status = 'confirm' and u.delivery_status='Delivered' and u.did= :did ")
	public List<Orders> findByDeliveryConfirm(@Param("did") int did);
}
