package com.hanifa.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.hanifa.hotel.dto.Item;

public class ItemDao {
	
	EntityTransaction entityTransaction;

	public boolean saveItem(Item item) {
		EntityManager entityManager=connect();
		try {
			entityTransaction.begin();
			entityManager.persist(item);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			
		}
		entityTransaction.commit();
		return false;
	}

	public Item getItemById(int id) {
		EntityManager entityManager=connect();
		Item item = entityManager.find(Item.class, id);
		if (item != null) {
			return item;
		}
		return null;
	}

	public List<Item> getAllItemDetails() {
		EntityManager entityManager=connect();
		Query query=entityManager.createQuery("select i from Item i");
		return query.getResultList();	
	}
	public boolean deleteItemById(int id) {
		EntityManager entityManager=connect();
		Item item=entityManager.find(Item.class,id);
		if(item!=null) {
			entityTransaction.begin();
			entityManager.remove(item);
			entityTransaction.commit();
			return true;
		}
		return false;
	}
	public boolean updateItem(Item item) {
		EntityManager entityManager=connect();
		try {
			entityTransaction.begin();
			entityManager.merge(item);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			
		}
		entityTransaction.commit();
		return false;
	}
	public EntityManager connect() {
		EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("dev");
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityTransaction=entityManager.getTransaction();
		return entityManager;
	}
}
