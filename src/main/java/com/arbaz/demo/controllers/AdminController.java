package com.arbaz.demo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	OrdersService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MedicineService medicineService;
	
	@GetMapping("/dashboard")
	public String adminHome(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		
		m.addAttribute("user",user);
		return "admin/AdminHome";
	}
	
	@GetMapping("/medicine")
	public String viewMedicine(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		m.addAttribute("medList",medicineService.getAllMedicine());
		
		return "admin/viewMedicine";
	}
	
	@GetMapping("/addMedicine")
	public String addMedicine(@RequestParam String m_qty,@RequestParam String med_name,
			@RequestParam String m_price,Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		Medicine med=new Medicine();
		med.setM_price(Double.parseDouble(m_price));
		med.setMed_name(med_name);
		med.setM_qty(Integer.parseInt(m_qty));
		medicineService.addMedicine(med);
		
		return "redirect:medicine";
	}
	
	@GetMapping("/deleteMedicine/{m_id}")
	public String deleteMedicine(@PathVariable String m_id) {
		int id=Integer.parseInt(m_id);
		medicineService.deleteMedicineById(id);
		return "redirect:/admin/medicine";
	}
	
	@GetMapping("/editMedicine/{m_id}")
	public String editMedicineForm(@PathVariable String m_id,Model m,Principal principal,HttpSession session) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		Medicine med=medicineService.findMedicineById(Integer.parseInt(m_id));
		m.addAttribute("editMed",med);
		return "admin/editMedicineForm";
	}
	
	@GetMapping("/updateMedicine")
	public String updateMedicine(@ModelAttribute("editMed") Medicine med) {
		medicineService.addMedicine(med);
		return "redirect:/admin/medicine";
	}
	
	@GetMapping("/customer")
	public String viewCustomer(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		m.addAttribute("user",new User());
		List<User> resultUser=userRepository.findByRoles("ROLE_CUSTOMER");
		m.addAttribute("listCustomer",resultUser);
		return "admin/viewCustomer";
	}
	
	@PostMapping("/addCustomer")
	public String addCustomer(@ModelAttribute("user") User user) {
		user.setActive(true);
		user.setRoles("ROLE_CUSTOMER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.addUser(user);
		
		return "redirect:customer";
	}
	
	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable String id) {
		userService.deleteUserById(Integer.parseInt(id));
		return "redirect:/admin/customer";
	}
	
	@GetMapping("/editCustomer/{id}")
	public String editCustomerForm(@PathVariable String id,Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		User u=userService.getCustomerById(Integer.parseInt(id));
		m.addAttribute("editUser",u);
		
		return "admin/editCustomerForm";
	}
	
	@PostMapping("/updateCustomer")
	public String updateCustomer(@ModelAttribute("editUser") User u,Model m) {
		User user=userService.getCustomerById(u.getId());
		user.setUsername(u.getUsername());
		user.setPassword(passwordEncoder.encode(u.getPassword()));
		userService.addUser(user);
		return "redirect:/admin/customer";
	}
	
	@GetMapping("/distributor")
	public String viewDistributor(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		List<User> dlist=userService.getAllCustomer("ROLE_DISTRIBUTOR");
		m.addAttribute("listDist",dlist);
		m.addAttribute("distUser",new User());
		return "admin/viewDistributor";
	}
	
	@PostMapping("/addDistributor")
	public String addDistributor(@ModelAttribute("distUser") User user) {
		user.setActive(true);
		user.setRoles("ROLE_DISTRIBUTOR");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.addUser(user);
		return "redirect:distributor";
	}
	
	@GetMapping("/deleteDistributor/{id}")
	public String deleteDistributor(@PathVariable String id) {
		userService.deleteUserById(Integer.parseInt(id));
		return "redirect:/admin/distributor";
	}
	
	@GetMapping("/editDistributor/{id}")
	public String editDistributorForm(@PathVariable String id,Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		User u=userService.getCustomerById(Integer.parseInt(id));
		m.addAttribute("disuser",u);
		
		return "admin/editDistributor";
	}
	
	@PostMapping("/updateDistributor")
	public String updateDistributor(@ModelAttribute("disuser") User u,Model m) {
		User user=userService.getCustomerById(u.getId());
		user.setUsername(u.getUsername());
		user.setPassword(passwordEncoder.encode(u.getPassword()));
		userService.addUser(user);
		return "redirect:/admin/distributor";
	}
	
	@GetMapping("/viewPending")
	public String viewPendingOrders(Model m,Principal principal) {
		String email=principal.getName();
		User user=userRepository.getUserByUserName(email);
		m.addAttribute("user",user);
		List<Orders> list=orderService.findByOrderStatus();
		m.addAttribute("orList", list);
		return "admin/pendingOrder";
	}
	
	@GetMapping("/confirm/{id}")
	public String confirmOrder(@PathVariable String id) {
		Orders op=orderService.findById(Integer.parseInt(id));
		op.setOrder_status("confirm");
		orderService.setOrders(op);
		return "redirect:/admin/viewPending";
	}
	
	@GetMapping("/cancel/{id}")
	public String cancelmOrder(@PathVariable String id) {
		Orders op=orderService.findById(Integer.parseInt(id));
		
		
		op.setOrder_status("cancel");
		orderService.setOrders(op);
		return "redirect:/admin/viewPending";
	}
	
}
