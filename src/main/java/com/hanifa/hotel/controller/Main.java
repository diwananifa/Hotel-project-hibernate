

package com.hanifa.hotel.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.hanifa.hotel.dao.CustomerDao;
import com.hanifa.hotel.dao.EmplyeeDao;
import com.hanifa.hotel.dao.ItemDao;
import com.hanifa.hotel.dao.MenuDao;
import com.hanifa.hotel.dao.ProductDao;
import com.hanifa.hotel.dto.Customer;
import com.hanifa.hotel.dto.Employee;
import com.hanifa.hotel.dto.Item;
import com.hanifa.hotel.dto.Menu;
import com.hanifa.hotel.dto.Product;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	static EmplyeeDao emplyeeDao = new EmplyeeDao();
	static MenuDao menuDao = new MenuDao();
	static ItemDao itemDao = new ItemDao();
	static ProductDao productDao = new ProductDao();
	static CustomerDao customerDao = new CustomerDao();

	public static void main(String[] args) {
		boolean b = true;
		while (b) {
			System.out.println("Enter the choice");
			System.out.println("1.Sign up\n2.log in\n3.Exit");
			switch (scanner.nextInt()) {
			case 1: {
				System.out.println("Enter the employee name");
				String name = scanner.next();
				System.out.println("Enter the role of the employee staff or manager");
				String role = scanner.next().toUpperCase();
				System.out.println("Enter the employee mail");
				String mail = scanner.next();
				System.out.println("Enter the employee password");
				String password = scanner.next();
				System.out.println("Renter the password");
				String re_password = scanner.next();
				if (role.equals("STAFF") || role.equals("MANAGER")) {
//					if password and reenter password is not match at the time go for the else block
					if (password.equals(re_password)) {
						Employee employee = new Employee();
						employee.setE_name(name);
						employee.setE_role(role);
						employee.setMail(mail);
						employee.setPassword(password);
						System.out.println(emplyeeDao.saveEmployee(employee) ? "employee details saved sucessfully"
								: "data not saved email id is already present");
					}
//					password not match
					else {
						System.out.println("password is mismatch");
					}
				} else {
					System.out.println("role is not match");
				}

				break;
			}
//			sign up page
			case 2: {
				System.out.println("Enter the employee mail");
				String mail = scanner.next();
				System.out.println("Enter the employee password");
				String password = scanner.next();
				Employee employee = emplyeeDao.getEmployeDetailsByEmail(mail);
//			 check for mail is present for the database
				if (employee != null) {
//			 if mail is present at the time verify the user password and currect password in the database
					if (employee.getPassword().equals(password)) {
//			if role is manager at the time go for the manager page 
						if (employee.getE_role().equalsIgnoreCase("manager")) {
							boolean b1 = true;
							while (b1) {
								System.out.println("Enter the choice");
								System.out.println(
										"1.Menu\n2.Item\n3.Full customer list\n4.Full product list\n5.Log out");
								switch (scanner.nextInt()) {
								case 1: {
									boolean b2 = true;
									while (b2) {
										System.out.println(
												"1.Add the menu\n2.Delete the menu\n3.modify the menu name\n4.Full menu list\n5.Back\n6.Log out");
										switch (scanner.nextInt()) {
//										add the menu case
										case 1: {
//										go for menu page
											Menu menu = new Menu();
											System.out.println("Enter the menu name");
											menu.setM_type(scanner.next().toUpperCase());
											System.out
													.println(menuDao.saveMenu(menu) ? "New menu data saved sucessfully"
															: "menu name already present");
											break;
										}
//										delete the menu case
										case 2: {
											Menu menu = getMenu("delete");
											if (menu != null) {
												menuDao.removeMenuById(menu.getM_id());
											}
											break;
										}
//										update the menu name case
										case 3: {
											Menu menu = getMenu("update");
											if (menu != null) {
												System.out.println("Enter the new name of the menu");
												menu.setM_type(scanner.next().toUpperCase());
												System.out.println(
														menuDao.updateMenu(menu) ? "menu name updated sucessfully"
																: "This menu name is already present");
											}
											break;
										}
//										all details for the menu case
										case 4: {
											getMenu("fetch");
											break;
										}
//										exit case
										case 5: {
											b2 = false;
											break;
										}
										case 6: {
											b1 = false;
											b2 = false;
										}
//										logout the manager case
										default: {
											System.out.println("Your input is wrong");
										}
										}
									}
									break;
								}
								case 2: {
//									item list page
									boolean b2 = true;
									while (b2) {
										System.out.println(
												"1.Add the item\n2.Delete the item\n3.modify the item price\n4.Full item list\n5.Back\n6.Log out");
										switch (scanner.nextInt()) {
										case 1: {
//											add the item case
											boolean b3 = false;
//											get the menu to add the item
											Menu menu = getMenu("add");
//											menu is present or not in the database
											if (menu != null) {
//												
												List<Item> items = menu.getItems();
												if (items == null) {
													items = new ArrayList<Item>();
												}
//												create the item object
												Item item = new Item();
												do {
													System.out.println("Enter the item name");
													item.setI_name(scanner.next().toUpperCase());
													System.out.println("Enter the item price");
													double price = scanner.nextDouble();
//													check price is 0 or not
													if (price == 0) {
														System.out.println(
																"your input price is zero please check the price");
														b3 = true;
													} else {
														item.setI_price(price);
														items.add(item);
														menu.setItems(items);
														item.setMenu(menu);
														System.out.println(
																itemDao.saveItem(item) ? "Item saved sucessfully"
																		: "Item name is already present");
														b3 = false;
													}
												} while (b3);
											}
											break;
										}
//										delete the item case
										case 2: {
//											get the menu
											Menu menu = getMenu("delete");
//											check menu is empty or not
											if (menu != null) {
//											get the which item will be delete
												Item item = getItem(menu, "delete");
//											check item is present or not 
												if (item != null) {
													if (itemDao.deleteItemById(item.getI_id())) {
														System.out.println("item removed sucessfully");
													}
												}
											}
											break;
										}
//										update the item case
										case 3: {
//											get the menu type 
											Menu menu = getMenu("update");
											if (menu != null) {
//											get the item 
												Item item = getItem(menu, "update");
												if (item != null) {
													boolean b3 = false;
													do {
														System.out.println("Enter the new price for this item");
														double price = scanner.nextDouble();
//														check input price is zero or not
														if (price == 0) {
															System.out.println(
																	"your input price is zero so check the input");
															b3 = true;
														} else {
															item.setI_price(price);
															if (itemDao.updateItem(item)) {
																System.out.println("item updated sucessfully");
															}
															b3 = false;
														}

													} while (b3);
												}
											}
											break;
										}
										case 4: {
//											get the menu and menu method contain item method
											getMenu("item fetch");
											break;
										}
										case 5: {
//											exit the page
											b2 = false;
											break;
										}
//										logout 
										case 6: {
											b2 = false;
											b1 = false;
											break;
										}
										default: {
											System.out.println("Your input is wrong");
											break;
										}

										}

									}
									break;
								}
//								fetch the full details for the customer
								case 3: {
									List<Customer> customers = customerDao.getCustomerDetails();
									if (customers.isEmpty()) {
										int i = 1;
										for (Customer customer : customers) {
											System.out.println(i + "." + customer);
										}
									} else {
										System.out.println("Customer list is empty");
									}
									break;
								}
								case 4: {
//									ftch the details for the product with customer
									List<Customer> customers = customerDao.getCustomerDetails();
									if (!customers.isEmpty()) {
										int i = 1;
										for (Customer customer : customers) {
											System.out.println(i + "." + customer);
											List<Product> products = customer.getProducts();
											if (!products.isEmpty()) {
												int j = 1;
												for (Product product : products) {
													System.out.println(j + "." + product);
												}
											}
										}
									} else {
										System.out.println("Customer list is empty");
									}
									break;

								}
								case 5: {
									b1 = false;
									break;
								}
								default: {
									System.out.println("your input is wrong");
									break;
								}
								}
							}
						} else {
							boolean b1 = true;
							while (b1) {
								Customer customer = new Customer();
								System.out.println("Enter the customer details");
								System.out.println("Enter the name");
								customer.setC_name(scanner.next().toUpperCase());
								boolean b5 = false;
								do {
									System.out.println("Enter the phone number");
									long phone = scanner.nextLong();
//									check the pasword length is 10 or not
									if (String.valueOf(phone).length() != 10) {
										System.out.println("phone number must should be in 10 digit");
										b5 = true;
									} else {
										customer.setC_phone(phone);
										b5 = false;
									}

								} while (b5);
								customer.setTotal_price(0);
//								save the cutomer on the database with total price is zero
								customerDao.saveCustomer(customer);
								System.out.println("select the choice");
//								go for the order page to order the product
								order(customer);
								boolean b3 = true;
//								final conformation
								while (b3) {
//									go the finesh method to display the all product for the customer ordered
									List<Product> products = fineshTheOrder(customer);
//									check customer order product is empty or not
									if (products != null) {
//									add the product 
										switch (scanner.nextInt()) {
										case 1: {
											order(customer);
											break;
										}
//									delete the product	
										case 2: {
											Product product = getOrder(products);
											if (product != null) {
												productDao.removeproductById(product.getId());
												System.out.println("product deleted sucessfully");
											}
											break;
										}
//										modify the product quantity
										case 3: {
											Product product = getOrder(products);
											if (product != null) {
												System.out.println("Entre the new quantity");
												int qty = scanner.nextInt();
												product.setP_quantity(qty);
												product.setP_total_price(qty * product.getP_price());
												productDao.updateProductById(product);
												System.out.println("product updated sucessfully");
											}
											break;
										}
//										remove the product
										case 4: {
											customerDao.removeCustomerById(customer.getC_id());
											b3 = false;
											break;
										}
//										if customer conform the order at the time do for the billing process
										case 5: {
											fileCreation(products, customer);
											b3 = false;
											break;
										}
										default: {
											System.out.println("your input is wrong");
										}
										}
									} else {
										b3 = false;
//										customer not oder the product at the time delete the customer object
										System.out.println("zero order for the customer");
										customerDao.removeCustomerById(customer.getC_id());
									}
								}
							}
						}
					} else {
						System.out.println("password is wrong");
					}
				} else {
					System.out.println("email not found");
				}
				break;
			}
			case 3: {
				System.out.println("Thank you for coming");
				b = false;
				break;
			}
			}
		}
	}
//		
	public static Menu getMenu(String operation) {
		List<Menu> menus = menuDao.getAllMenuDetails();
//		check menu empty or not
		if (!menus.isEmpty()) {
			int i = 1;
//			print the all menu list 
			for (Menu menu : menus) {
//				if customer order the product at the time go for the it block and not dispaly the empty item list menu
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
				System.out.println(menus.size() + 1 + ".BACK");
				boolean b = false;
				do {
					System.out.println("select the menu number");
					int number = scanner.nextInt() - 1;
					if (number < menus.size() && number >= 0) {
						Menu menu = menus.get(number);
						return menu;
					} else if (number == menus.size())
						;
					else {
						System.out.println("your input is wrong");
						b = true;
					}
				} while (b);
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
				System.out.println("\t" + (items.size() + 1) + ".BACK");
				boolean b = false;
				do {
					System.out.println("Enter the " + operation + " item number");
					int number = scanner.nextInt() - 1;
					if (number < items.size() && number >= 0) {
						Item item = items.get(number);
						return item;
					} else if (number == items.size())
						;
					else {
						System.out.println("your input is wrong");
						b = true;
					}
				} while (b);
			}
		} else {
			System.out.println("\titem list is empty");
		}
		return null;
	}

	public static void order(Customer customer) {
		boolean b2 = true;
		while (b2) {
			Menu menu = getMenu("order");
			if (menu != null) {
				Item item = getItem(menu, "order");
				if (item != null) {
					Product product = new Product();
					product.setP_name(item.getI_name());
					product.setP_price(item.getI_price());
					System.out.println("Enter the quantity");
					int quantity = scanner.nextInt();
					product.setP_quantity(quantity);
					product.setP_total_price(quantity * item.getI_price());
					List<Product> products = customer.getProducts();
					if (products == null) {
						products = new ArrayList<Product>();
					}
					products.add(product);
					customer.setProducts(products);
					product.setCustomer(customer);
					productDao.saveProduct(product);
				}
			} else {
				b2 = false;
			}
		}
	}

	public static List<Product> fineshTheOrder(Customer customer) {
		List<Product> products = productDao.getProductDetailsBYCustomerId(customer);
		if (products.size() > 0) {
			System.out.println("your total order is:");
			int i = 1;
			double total = 0;
			for (Product product : products) {
				System.out.println("\t" + i + "." + product);
				total += product.getP_total_price();
				i++;
			}
			customer.setTotal_price(total);
			customerDao.updateCustomerById(customer);
			System.out.println("total product price is " + total);
			System.out.println("\n\nEnter the choice");
			System.out.println(
					"1.Add the product\n2.remove the product\n3.change the quantity\n4.cancel the order\n5.order the above product");
			return products;
		}
		return null;
	}

	public static Product getOrder(List<Product> products) {
		int i = 1;
		for (Product product : products) {
			System.out.println("\t" + i + "." + product);
			i++;
		}
		System.out.println("\t" + products.size() + 1 + ".BACK");
		boolean b2 = false;
		do {

			System.out.println("Enter the product number");
			int number = scanner.nextInt() - 1;
			if (products.size() > number && number >= 0) {
				return products.get(number);
			} else if (products.size() == number)
				;
			else {
				System.out.println("your input is wrong");
				b2 = true;
			}
		} while (b2);
		return null;
	}

	public static void fileCreation(List<Product> products, Customer customer) {
		int a = 4;
		String s = "customer name  :" + customer.getC_name() + "\nCustomer phone :" + customer.getC_phone() + "\n";
		String s1 = "S.NO   P.Name                       P.price   P.Qty   Total  \n";
		String res = s + s1;
		int i = 1;
		for (Product product : products) {
			res += space(String.valueOf(i++), 7) + space(product.getP_name(), 29)
					+ space(String.valueOf(product.getP_price()), 10)
					+ space(String.valueOf(product.getP_quantity()), 8) + product.getP_total_price() + "\n";
		}
		String s6 = "_____________________________________________________________\n";
		String s7 = "                                                Total=" + customer.getTotal_price() + "\n";
		res += s6 + s7 + s6;
		try {
			FileWriter fileWriter = new FileWriter("d:\\bill\\" + customer.getC_id() + ".txt");
			fileWriter.write(res);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("Thank you for order\n\twelcome");
	}

	public static String space(String s, int n) {
		int length = n - s.length();
		for (int i = 0; i < length; i++) {
			s += " ";
		}
		return s;
	}
}
