package com.panipuri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AddressVo;
import com.test.hibernate.Customer;
import com.test.hibernate.dao.CustomerDaoImpl;

@Component
@Lazy
public class CustomerInformationFetchService {
	@Autowired
	CustomerDaoImpl customerDaoImpl;
	
	public List<AddressVo> getCustomerDeliveryAddressList(String phoneNumber, String addressId) {
		
		List<AddressVo> addressList = customerDaoImpl.fetchCustomerAddressFromContact(phoneNumber, addressId);
		
		
		return addressList;
	}
	public Customer fetchCustomerinfo(String phoneNumber) {
		
		Customer customer = customerDaoImpl.fetchCustomerinfo(phoneNumber);
		
		
		return customer;
	}

	/**
	 * @param customerDaoImpl the customerDaoImpl to set
	 */
	public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
		this.customerDaoImpl = customerDaoImpl;
	}
	
}
