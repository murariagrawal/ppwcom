package com.panipuri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.hibernate.dao.DiscountDaoImpl;

@Component
public class DiscountService {
	@Autowired
	DiscountDaoImpl discountDaoImpl;
	
	

	/**
	 * @param discountDaoImpl the discountDaoImpl to set
	 */
	public void setDiscountDaoImpl(DiscountDaoImpl discountDaoImpl) {
		this.discountDaoImpl = discountDaoImpl;
	}
}
