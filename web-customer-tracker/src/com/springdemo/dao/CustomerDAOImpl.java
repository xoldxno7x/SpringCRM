package com.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// create query - sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the list of customers that we have retrieved
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// save or update customer to db
		currentSession.saveOrUpdate(theCustomer);

	}

	@Override
	public Customer getCustomer(int theId) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// get customer from db
		Customer theCustomer = currentSession.get(Customer.class, theId);

		// return the customer
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete customer
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");

		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();

	}

	@Override
	public List<Customer> searchCusromers(String theSearchName) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		Query theQuery = null;
				
		//only search by name if the SearchName is not empty
				
		if(theSearchName !=null && theSearchName.trim().length()> 0) {
			
			//search for first name or last name.. case sensative
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName "
					+ "or lower(lastName) like :theName",Customer.class);
			theQuery.setParameter("theName", "%" +theSearchName.toLowerCase() +"%");
		}else {
			//search name is empty so just get all customers
			theQuery = currentSession.createQuery("from Customer",Customer.class);
		}
		
		//execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		//return the results
		return customers;
		 
	}

}
