package com.arbaz.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int order_id;
	private String name;
	private String mobile;
	private String Address;
	private String img_url;
	private String order_status;
	private String delivery_status;
	private double amount;
	private int did;
	
	@ManyToOne
	private User user;
	
	@OneToOne
	private Medicine medicine;
	
}
