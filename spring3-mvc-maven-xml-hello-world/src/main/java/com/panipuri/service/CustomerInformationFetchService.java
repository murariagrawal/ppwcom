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
		if(null != addressListTemp) {
			for(Address address :addressListTemp) {
				AddressVo deliveryAddress = new AddressVo();
				deliveryAddress.setAddressId(address.getAddressId());
				deliveryAddress.setAddressLine1(address.getAddressLine1());
				deliveryAddress.setAddressline2(address.getAddressLine2());
				deliveryAddress.setAddressLine3(address.getAddressLine3());
				deliveryAddress.setCity(address.getCity());
				deliveryAddress.setState(address.getState());
				deliveryAddress.setZipcode(""+address.getZipcode());
				addressList.add(deliveryAddress);
			}
		}
		AddressVo deliveryAddress1 = new AddressVo();
		deliveryAddress1.setAddressId(1121);
		deliveryAddress1.setAddressLine1("D 303, Kool homes");
		deliveryAddress1.setAddressline2("Nda Road, bavdhan");
		deliveryAddress1.setCity("Pune");
		deliveryAddress1.setContactNumber("9673142211");
		deliveryAddress1.setState("Maharashtra");
		deliveryAddress1.setZipcode("411021");
		AddressVo deliveryAddress2 = new AddressVo();
		deliveryAddress2.setAddressId(1121);
		deliveryAddress2.setAddressLine1("D 303, Kool homes");
		deliveryAddress2.setAddressline2("Nda Road, bavdhan");
		deliveryAddress2.setCity("Pune");
		deliveryAddress2.setContactNumber("9673142211");
		deliveryAddress2.setState("Maharashtra");
		deliveryAddress2.setZipcode("411021");
		//addressList.add(deliveryAddress1);
		//addressList.add(deliveryAddress2);
		return addressList;
	}

	/**
	 * @param customerDaoImpl the customerDaoImpl to set
	 */
	public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
		this.customerDaoImpl = customerDaoImpl;
	}
	
}
