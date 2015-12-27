package com.test.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.AddressVo;
import com.panipuri.vo.AreaVo;
import com.test.hibernate.Address;
import com.test.hibernate.Customer;
import com.test.hibernate.DeliveryArea;

public class CustomerDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    public List<AddressVo> fetchCustomerAddressFromContact(String phoneNumber) {
    	Session session = this.sessionFactory.openSession();
    	List<Address> addressList = session.createCriteria(Address.class, "address").createAlias("address.customer","customer")
    	.add(Restrictions.eq("customer.contactNo1", phoneNumber)).list();
    	List<AddressVo> addressVoList = new ArrayList<AddressVo>();
    	if(null != addressList && !addressList.isEmpty()) {
			String firstName = addressList.get(0).getCustomer().getCustomerFirstName();
			String lastName = addressList.get(0).getCustomer().getCustomerLastName();
			AddressVo deliveryAddress =null;
			for(Address address :addressList) {
				deliveryAddress = convertAddressToAddressVo(address);
				deliveryAddress.setFirstName(firstName);
				deliveryAddress.setLastName(lastName);
				addressVoList.add(deliveryAddress);
			}
    	}
    	return addressVoList;
    }
    public Customer fetchCustomerinfo(String phoneNumber) {
    	Session session = this.sessionFactory.openSession();
    	List<Customer> customerList = session.createCriteria(Customer.class, "customer")
    	.add(Restrictions.eq("customer.contactNo1", phoneNumber)).list();
    	Customer custInfo=null;
    	if(null != customerList && !customerList.isEmpty()) {
    		custInfo = customerList.get(0);
    	}
    	return custInfo;
    }
    private AddressVo convertAddressToAddressVo(Address address) {
		AddressVo addressVo = null;
		AreaVo areaVo = null;
		if(null != address) {
			DeliveryArea area = address.getArea();
			addressVo = new AddressVo();
			addressVo.setAddressId(address.getAddressId());
			addressVo.setAddressLine1(address.getAddressLine1());
			addressVo.setAddressline2(address.getAddressLine2());
			addressVo.setLandmark(address.getLandmark());
			if(area != null) {
				areaVo =  new AreaVo();
				areaVo.setAreaName(area.getAreaName());
				areaVo.setZipcode(String.valueOf(area.getZipcodes().getZipcode()));
				areaVo.setSubAreaName(area.getSubAreaName());
				areaVo.setDeliveryAreaId(area.getDeliveryAreaId());
			}
			addressVo.setArea(areaVo);
			
		}
		return addressVo;
	}
}
