package com.hanifa.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.hanifa.hotel.dto.Customer;

public class CustomerDao {
	
	EntityTransaction entityTransaction;
	public boolean saveCustomer(Customer customer) {
		EntityManager entityManager=connect();
		entityTransaction.begin();
		entityManager.persist(customer);
		entityTransaction.commit();	
		return true;
	}
	public List<Customer> getCustomerDetails(){
		EntityManager entityManager=connect();
		Query query = entityManager.createQuery("select c from Customer c");
		return query.getResultList();
	}
	public Customer getCustomerById(int id) {
		EntityManager entityManager=connect();
		Customer customer= entityManager.find(Customer.class, id);
		if(customer!=null) {
			return customer;
		}
		return null;
	}
	public boolean updateCustomerById(Customer customer) {
		EntityManager entityManager=connect();
		entityTransaction.begin();
		entityManager.merge(customer);
		entityTransaction.commit();
		return true;
	}
	public boolean removeCustomerById(int id) {
		EntityManager entityManager=connect();
		Customer customer=entityManager.find(Customer.class, id);
		if(customer!=null) {
			entityTransaction.begin();
			entityManager.remove(customer);
			entityTransaction.commit();
			return true;
		}
		return false;
	}
	public EntityManager connect() {
		EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("dev");
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityTransaction=entityManager.getTransaction();
		return entityManager;
	}
}
