package com.test.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.test.hibernate.Address;

public class CustomerDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    public List<Address> fetchCustomerAddressFromContact(String phoneNumber) {
    	Session session = this.sessionFactory.openSession();
    	List<Address> addressList = session.createCriteria(Address.class, "address").createAlias("address.customer","customer")
    	.add(Restrictions.eq("customer.contactNo1", phoneNumber)).list();
    	
    	return addressList;
    }
}
