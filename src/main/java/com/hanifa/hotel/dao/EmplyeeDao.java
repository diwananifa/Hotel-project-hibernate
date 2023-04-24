package com.hanifa.hotel.dao;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.hanifa.hotel.dto.Employee;


public class EmplyeeDao {
	
	 EntityTransaction entityTransaction;

	public boolean saveEmployee(Employee employee) {
		EntityManager entityManager=connect();
		try {
			entityTransaction.begin();
			entityManager.persist(employee);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			
		}
		entityTransaction.commit();
		return false;
	}
	
	public Employee getEmployeDetailsByEmail(String mail)	{
		EntityManager entityManager=connect();
		Query query= entityManager.createQuery("select e from Employee e where e.mail=?1");
		query.setParameter(1, mail);
		try {
			Employee employee=(Employee)query.getSingleResult();
			if(employee!=null)
				return employee;
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	
	public Employee getEmployeeDetailsById(int id)	{
		EntityManager entityManager=connect();
		Employee employee=entityManager.find(Employee.class, id);
		if(employee!=null)	{
			return employee;
		}
		return null;	
	}
	
	
	public List<Employee> getEmployeeDetails()	{
		EntityManager entityManager=connect();
		Query query= entityManager.createQuery("select e from Employee e");
		try {
			List<Employee> employees=query.getResultList();
			if(employees!=null)
				return employees;
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	public EntityManager connect() {
		EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory("dev");
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityTransaction=entityManager.getTransaction();
		return entityManager;
	}
}
