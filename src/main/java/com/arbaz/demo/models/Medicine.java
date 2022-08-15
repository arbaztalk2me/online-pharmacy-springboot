package com.arbaz.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Medicine {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int m_id;
	private String med_name;
	private int m_qty;
	private double m_price;
	
}
