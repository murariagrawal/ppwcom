package com.panipuri.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.OrderFetchService;
import com.panipuri.vo.AddressVo;
import com.panipuri.vo.AreaVo;
import com.panipuri.vo.OrderVo;
import com.test.hibernate.Address;
import com.test.hibernate.Customer;
import com.test.hibernate.DeliveryArea;

@Controller
public class OrderVerificationController {
	@Autowired
	private OrderFetchService orderFetchService;
	@RequestMapping(method = RequestMethod.POST, value="/verifyDetails")
	public ModelAndView showVerifyOrder(final HttpServletRequest request) {
		System.out.println("in controller");
		ModelAndView mv = null;
		String slotId = request.getParameter("selectDeliverySlot");
		String firstName = request.getParameter("name[first]");
		String lastName = request.getParameter("name[last]");
		String address1 = request.getParameter("addr1");
		String address2 = request.getParameter("addr2");
		String landmark = request.getParameter("landmarkAddr");
		String areaId = request.getParameter("searchId");
		String phoneNumber = request.getParameter("phoneNumber");
		String orderId = request.getParameter("deliveryOrderId");
		String customerId = request.getParameter("customerId");
		String addressId = request.getParameter("deliveryAddressId");
		
		Customer customer = new Customer();
		if(null != customerId && !customerId.equals("")) {
			customer.setCustomerId(new Long(customerId));
		}
		
		customer.setContactNo1(phoneNumber);
		customer.setCustomerFirstName(firstName);
		customer.setCustomerLastName(lastName);
		Address address = new Address();
		if(null != addressId && !addressId.equals("")) {
			address.setAddressId(new Long(addressId));
		}
		address.setAddressLine1(address1);
		address.setAddressLine2(address2);
		address.setLandmark(landmark);
		DeliveryArea area = new DeliveryArea();
		area.setDeliveryAreaId(new Long(areaId));
		address.setArea(area);
		OrderVo orderDetails = orderFetchService.updateOrderAndgetOrderDetails(orderId, address, customer, slotId);
		AddressVo addressVo = orderDetails.getDeliveryAddress();
		
		String addressLine = "";
		if(null != addressVo.getAddressLine1()) {
			addressLine= addressLine+addressVo.getAddressLine1();
			if(null!=addressVo.getAddressline2()){
				addressLine =addressLine+" ";
			}
		}
		if(null!=addressVo.getAddressline2()){
			addressLine =addressLine+addressVo.getAddressline2();
		}
		String landmarkReturn = ""; 
		if(null!=addressVo.getLandmark()){
			landmarkReturn =landmarkReturn+addressVo.getLandmark();
		}		
		AreaVo areaDetails = addressVo.getArea();
		String zipcodeReturn = "";
		if(null != areaDetails.getSubAreaName()) {
			zipcodeReturn = zipcodeReturn+areaDetails.getSubAreaName()+" ";
		}
		if(null != areaDetails.getAreaName()) {
			zipcodeReturn = zipcodeReturn+areaDetails.getAreaName()+" ";
		}
		
		if(null != areaDetails.getZipcode()) {
			zipcodeReturn = zipcodeReturn+areaDetails.getZipcode();
		}
		
			mv = new ModelAndView("");
			mv.addObject("orderId", orderDetails.getOrderId());
			mv.addObject("contactNo", orderDetails.getContactNo());
			mv.addObject("deliverySlot", orderDetails.getDeliverySlot());
			mv.addObject("addressLine", addressLine);
			mv.addObject("landmarkReturn", landmarkReturn);
			mv.addObject("zipcodeReturn", zipcodeReturn);
			mv.addObject("itemList", orderDetails.getItemList());
			mv.addObject("toppingList", orderDetails.getToppingList());
		return mv;
	}
}
