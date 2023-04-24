package com.hanifa.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.hanifa.hotel.dto.Customer;
import com.hanifa.hotel.dto.Product;

public class ProductDao {
	EntityTransaction entityTransaction;
	public boolean saveProduct(Product product) {
		EntityManager entityManager=connect();
		entityTransaction.begin();
		entityManager.persist(product);
		entityTransaction.commit();	
		return true;
	}
	public List<Product> getProductDetailsBYCustomerId(Customer customer){
		EntityManager entityManager=connect();
		Query query = entityManager.createQuery("select p from Product p where p.customer=?1");
		query.setParameter(1,customer );
		return query.getResultList();
	}
	public List<Product> getProductDetails(){
		EntityManager entityManager=connect();
		Query query = entityManager.createQuery("select p from Product p ");
		return query.getResultList();
	}
	public Product getProductById(int id) {
		EntityManager entityManager=connect();
		Product product= entityManager.find(Product.class, id);
		if(product!=null) {
			return product;
		}
		return null;
	}
	public boolean updateProductById(Product product) {
		EntityManager entityManager=connect();
		entityTransaction.begin();
		entityManager.merge(product);
		entityTransaction.commit();
		return true;
	}
	public boolean removeproductById(int id) {
		EntityManager entityManager=connect();
		Product product=entityManager.find(Product.class, id);
		if(product!=null) {
			entityTransaction.begin();
			entityManager.remove(product);
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
