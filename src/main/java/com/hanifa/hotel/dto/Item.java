package com.hanifa.hotel.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int i_id;
	@Column(unique = true)
	private String i_name;
	private  double i_price;
	
	@ManyToOne
	@JoinColumn
	private Menu menu;
	
	public int getI_id() {
		return i_id;
	}
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}
	public String getI_name() {
		return i_name;
	}
	public void setI_name(String i_name) {
		this.i_name = i_name;
	}
	public double getI_price() {
		return i_price;
	}
	public void setI_price(double i_price) {
		this.i_price = i_price;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	@Override
	public String toString() {
		return i_name+" price-"+i_price;
	}
	
	
}
