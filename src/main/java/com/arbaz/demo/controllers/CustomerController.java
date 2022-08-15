package com.arbaz.demo.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.arbaz.demo.models.Medicine;
import com.arbaz.demo.models.Orders;
import com.arbaz.demo.models.User;
import com.arbaz.demo.repositories.UserRepository;
import com.arbaz.demo.services.MedicineService;
import com.arbaz.demo.services.OrdersService;
import com.arbaz.demo.services.UserService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

		@Autowired
		OrdersService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MedicineService medicineService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/dashboard")
	public String customerDashboard(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		
		m.addAttribute("user",user);
		return "customer/customerHome";
	}
	
	@GetMapping("/viewMedicine")
	public String viewMedicine(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		m.addAttribute("medList",medicineService.getAllMedicine());
		
		return "customer/viewMedicine";
	}
	
	@GetMapping("/addToCart/{id}")
	public String addToCartForm(@PathVariable String id,Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		Medicine med=medicineService.findMedicineById(Integer.parseInt(id));
		m.addAttribute("med",med);
		return"customer/addToCartForm";
	}
	
	@GetMapping("/addToCartData")
	public String addToCart(@RequestParam String medid,@RequestParam String med_name,@RequestParam String medprice,
			@RequestParam String medQty,Model m,Principal principal,HttpSession session) {
			String email=principal.getName();
			User user=userRepository.getUserByUserName(email);
			m.addAttribute("user",user);
			Medicine md=new Medicine();
			md.setM_id(Integer.parseInt(medid));
			md.setMed_name(med_name);
			md.setM_qty(Integer.parseInt(medQty));
			md.setM_price(Integer.parseInt(medQty)*Double.parseDouble(medprice));
			List<Medicine> medList;
			String cart="carts";
			if(session.getAttribute(cart)==null) {
				medList=new ArrayList<>();
				medList.add(md);
				session.setAttribute(cart, medList);
			}else {
				medList=(List<Medicine>)session.getAttribute(cart);
				medList.add(md);
				session.setAttribute(cart,medList);
			}
			m.addAttribute("mm",session.getAttribute(cart));
			
			double Amount=0;
			for(Medicine ms:medList) {
				Amount+=ms.getM_price();
			}
			m.addAttribute("amount",Amount);
		return "customer/carts";
	}
	
	@GetMapping("/viewCart")
	public String viewCart(Model m,Principal principal,HttpSession session) {
		String email=principal.getName();
		String cart="carts";
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		m.addAttribute("mm",session.getAttribute(cart));
		
		double Amount=0;
		List<Medicine> medList=(List<Medicine>) session.getAttribute(cart);
		if(medList!=null) {
			for(Medicine ms:medList) {
				Amount+=ms.getM_price();
			}
		}
		
		m.addAttribute("amount",Amount);
		return "customer/carts";
	}
	
	@GetMapping("/orderForm")
	public String orderForm(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		List<User> u=userService.getAllCustomer("ROLE_DISTRIBUTOR");
		m.addAttribute("u",u);
		return "customer/orderForm";
	}
	
	@GetMapping("/getOrderDetails")
	public String getOrderDetails(@RequestParam String name,@RequestParam String imgurl,
			@RequestParam String address,@RequestParam String number,
			@RequestParam String did,Model m,Principal principal,HttpSession session) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		
		List<Medicine> medList=(List<Medicine>) session.getAttribute("carts");
		if(medList!=null) {
			for(Medicine md:medList) {
				Orders ord=new Orders();
				ord.setAddress(address);
				ord.setDelivery_status("Not Delivered");
				ord.setName(name);
				ord.setOrder_status("pending");
				ord.setImg_url(imgurl);
				ord.setUser(user);
				ord.setMedicine(md);
				ord.setAmount(md.getM_price());
				ord.setDid(Integer.parseInt(did));
				ord.setMobile(number);
				user.getOrder().add(ord);
				orderService.setOrders(ord);
				Medicine med=medicineService.findMedicineById(md.getM_id());
				med.setM_qty(med.getM_qty()-md.getM_qty());
				medicineService.addMedicine(med);
			}
		}
		session.removeAttribute("carts");
		return "customer/orderForm";
	}
	
	@GetMapping("/viewOrders")
	public String viewCustomerOrders(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		List<Orders> l=orderService.findByUser(user.getId());
		
		m.addAttribute("ordList",l);
		return "customer/orderDetails";
	}
	
	@GetMapping("/editOrder/{id}")
	public String editOrder(@PathVariable String id,Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		Orders ord=orderService.findById(Integer.parseInt(id));
		m.addAttribute("ordOne", ord);
		
		
		return "customer/editOrderFrom";
	}
	
	@GetMapping("/updateOrder")
	public String updateOrder(@RequestParam String id,@RequestParam String name,@RequestParam String address,@RequestParam String number) {
		
		Orders ord=orderService.findById(Integer.parseInt(id));
		ord.setName(name);
		ord.setAddress(address);
		ord.setMobile(number);
		orderService.setOrders(ord);
		
		return "redirect:/customer/viewOrders";
	}
	
	
}
