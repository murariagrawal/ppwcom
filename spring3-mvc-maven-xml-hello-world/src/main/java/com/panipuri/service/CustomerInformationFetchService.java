package com.panipuri.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.AddressVo;
import com.test.hibernate.Address;
import com.test.hibernate.dao.CustomerDaoImpl;

@Component
@Lazy
public class CustomerInformationFetchService {
	@Autowired
	CustomerDaoImpl customerDaoImpl;
	
	public List<AddressVo> getCustomerDeliveryAddressList(String phoneNumber) {
		
		List<Address> addressListTemp = customerDaoImpl.fetchCustomerAddressFromContact(phoneNumber);
		List<AddressVo> addressList = new ArrayList<AddressVo>();
		if(null != addressListTemp && !addressListTemp.isEmpty()) {
			String firstName = addressListTemp.get(0).getCustomer().getCustomerFirstName();
			String lastName = addressListTemp.get(0).getCustomer().getCustomerLastName();
			for(Address address :addressListTemp) {
				AddressVo deliveryAddress = new AddressVo();
				deliveryAddress.setAddressId(address.getAddressId());
				deliveryAddress.setAddressLine1(address.getAddressLine1());
				deliveryAddress.setAddressline2(address.getAddressLine2());
				deliveryAddress.setLandmark(address.getLandmark());
				deliveryAddress.setCity(address.getCity());
				deliveryAddress.setState(address.getState());
				deliveryAddress.setZipcode(""+address.getZipcode());
				deliveryAddress.setFirstName(firstName);
				deliveryAddress.setLastName(lastName);
				addressList.add(deliveryAddress);
			}
		
		}	
		return addressList;
	}

	/**
	 * @param customerDaoImpl the customerDaoImpl to set
	 */
	public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
		this.customerDaoImpl = customerDaoImpl;
	}
	
}
