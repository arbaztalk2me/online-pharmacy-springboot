package com.arbaz.demo.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arbaz.demo.models.Orders;
import com.arbaz.demo.models.User;
import com.arbaz.demo.repositories.UserRepository;
import com.arbaz.demo.services.MedicineService;
import com.arbaz.demo.services.OrdersService;
import com.arbaz.demo.services.UserService;

@Controller
@RequestMapping("/distributor")
public class DistributorController {
	@Autowired
	OrdersService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MedicineService medicineService;
	
	@GetMapping("/home")
	public String distHome(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		return "distributor/Distributor";
	}
	
	@GetMapping("/undeliverOrder")
	public String UndeliveryOrders(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		List<Orders> olist=orderService.findAllUndeliveryOrder(user.getId());
		m.addAttribute("olist", olist);
		return "distributor/pendingDelivery";
	}
	
	@GetMapping("/confirmDelivery/{id}")
	public String confirmDelivery(@PathVariable String id,Principal principal) {
		Orders ord=orderService.findById(Integer.parseInt(id));
		ord.setDelivery_status("Delivered");
		orderService.setOrders(ord);
		return "redirect:/distributor/undeliverOrder";
	}
	
	@GetMapping("/deliverOrder")
	public String deliveryOrders(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		List<Orders> ollist=orderService.findAllDeliveryOrder(user.getId());
		m.addAttribute("ollist", ollist);
		return "distributor/DeliveredItems";
	}
	
}
