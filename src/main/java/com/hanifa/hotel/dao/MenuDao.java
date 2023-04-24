package com.hanifa.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Session;

import com.hanifa.hotel.dto.Item;
import com.hanifa.hotel.dto.Menu;

public class MenuDao {
	EntityTransaction entityTransaction;
	
	public boolean saveMenu(Menu menu) {
		EntityManager entityManager=connect();
		try {
			entityTransaction.begin();
			entityManager.persist(menu);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			
		}
		entityTransaction.commit();
		return false;
	}
	public Menu getMenuById(int id) {
		EntityManager entityManager=connect();
		Menu menu= entityManager.find(Menu.class, id);
		if(menu!=null)	{
			return menu;
		}
		return null;
	}
	public List<Menu> getAllMenuDetails()	{
		EntityManager entityManager=connect();
		Query query= entityManager.createQuery("select m from Menu m");
		return query.getResultList();
	}
	public boolean removeMenuById(int id) {
		EntityManager entityManager=connect();
		Menu menu=entityManager.find(Menu.class, id);
		if(menu!=null) {	
			entityTransaction.begin();
			List<Item>items= menu.getItems();
			if(items!=null) {
				for (Item item : items) {
					entityManager.remove(item);
				}
			}
			entityManager.remove(menu);
			entityTransaction.commit();
			return true;
		}
		return false;
	}
	
	public boolean updateMenu(Menu menu) {	
		EntityManager entityManager=connect();
		try {
			entityTransaction.begin();
			entityManager.merge(menu);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			
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
