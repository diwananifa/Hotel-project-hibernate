package com.hanifa.hotel.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.hanifa.hotel.dao.CustomerDao;
import com.hanifa.hotel.dao.ItemDao;
import com.hanifa.hotel.dao.MenuDao;
import com.hanifa.hotel.dao.ProductDao;
import com.hanifa.hotel.dto.Customer;
import com.hanifa.hotel.dto.Item;
import com.hanifa.hotel.dto.Menu;
import com.hanifa.hotel.dto.Product;

public class staff {
	static Scanner scanner=new Scanner(System.in);
	static MenuDao menuDao=new MenuDao();
	static ItemDao itemDao=new ItemDao();
	static CustomerDao customerDao=new CustomerDao();
	public static void main(String[] args) {
		boolean b1=true;
		while(b1) {
			Customer customer = new Customer();
			System.out.println("Enter the customer details");
			System.out.println("Enter the name");
			customer.setC_name(scanner.next().toUpperCase());
			System.out.println("Enter the phone number");
			customer.setC_phone(scanner.nextLong());
			customer.setTotal_price(0);
			customerDao.saveCustomer(customer);
			System.out.println("select the choice");
			boolean b2=true;
			while(b2) {
				Menu menu= getMenu("order");
				ProductDao productDao=new ProductDao();
				if(menu!=null) {
					Item item= getItem(menu, "order");
					if(item!=null) {
						Product product=new Product();
						product.setP_name(item.getI_name());
						product.setP_price(item.getI_price());
						System.out.println("Enter the quantity");
						int quantity=scanner.nextInt();
						product.setP_quantity(quantity);
						product.setP_total_price(quantity*item.getI_price());
						productDao.saveProduct(product);
						List<Product>products= customer.getProducts();
						if(products==null) {
							products=new ArrayList<Product>();
						}
						products.add(product);
						customer.setProducts(products);
						product.setCustomer(customer);
						productDao.saveProduct(product);
					}
				}
				else {
					b2=false;
				}
			}
			System.out.println("your total order is:");
			ProductDao productDao=new ProductDao();
			List<Product>products= productDao.getProductDetailsBYCustomerId(customer);
			int i=1;
			double total=0;
			for (Product product : products) {
				System.out.println("\t"+i+"."+product);
				total+=product.getP_total_price();
			}
			customer.setTotal_price(total);
			System.out.println("total product price is "+total);
		}	
	}
	public static Menu getMenu(String operation) {
		List<Menu> menus = menuDao.getAllMenuDetails();
		if (!menus.isEmpty()) {
			int i = 1;
			for (Menu menu : menus) {
				if (operation.equals("order") && menu.getItems() != null) {
					System.out.println(i + "." + menu);
					i++;
				} else {
					System.out.println(i + "." + menu);
					if (operation.equals("item fetch")) {
						getItem(menu, "fetch");
					}
					i++;
				}
			}
			if (!operation.contains("fetch")) {
				System.out.println(menus.size()+1+".EXIT");				
				boolean b=false;
				do {
					System.out.println("select the menu number");
					int number = scanner.nextInt() - 1;
					if (number < menus.size()&&number>=0) {
						Menu menu = menus.get(number);
						return menu;
					}
					else if(number==menus.size());
					else {
						System.out.println("your input is wrong");
						b=true;
					}
				}while(b);
			}
		} else {
			System.out.println("menu list is empty");
		}
		return null;
	}

	public static Item getItem(Menu menu, String operation) {
		List<Item> items = menu.getItems();
		if (!items.isEmpty() && items != null) {
			int i = 1;
			for (Item item : items) {
				System.out.println("\t" + i + "." + item);
				i++;
			}
			if (!operation.equals("fetch")) {
				System.out.println("\t"+(items.size()+1)+".BACK");
				boolean b=false;
				do {
					System.out.println("Enter the " + operation + " item number");
					int number = scanner.nextInt() - 1;
					if(number < items.size()&&number>=0) {
						Item item = items.get(number);
						return item;
					}
					else if(number==items.size());
					else if(number==items.size()+1);
					else {
						System.out.println("your input is wrong");
						b=true;
					}
				}while(b);
			}
		} else {
			System.out.println("\titem list is empty");
		}
		return null;
	}
}
