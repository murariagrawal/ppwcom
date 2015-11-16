package com.panipuri.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.CustomerInformationFetchService;
import com.panipuri.service.DeliveryDetailsService;
import com.panipuri.service.OrderCreationService;
import com.panipuri.vo.AddressVo;
import com.panipuri.vo.DeliverySlotVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;

@Controller
public class OrderInitiationController {
	@Autowired
	private CustomerInformationFetchService customerInformationFetchService;
	@Autowired
	private OrderCreationService orderCreationService;
	@Autowired
	private DeliveryDetailsService deliveryDetailsService;
	

	@RequestMapping(method = RequestMethod.POST, value="/deliveryDetails")
	public ModelAndView showDeliveryDetails(final HttpServletRequest request, @RequestParam(value = "orderId") String orderId) {
		System.out.println("in controller");
		ModelAndView mv = null;
		Enumeration<String> paramNames = request.getParameterNames();
        List<ItemVo> selectedItems = new ArrayList<ItemVo>();
        List<ToppingVo> selectedToppings = new ArrayList<ToppingVo>();
        if (paramNames != null) {
            String paramName = null;
            String itemId = null;
            String itemQuantity = null;
            String toppingId = null;
            String toppingQuantity = null;
            while (paramNames.hasMoreElements()) {
                paramName = paramNames.nextElement();
                String[] paramValues = paramName.split("~");
                if (null != paramValues) {
                    if ("ITEM".equalsIgnoreCase(paramValues[0])) {
                    	ItemVo selectedItem = new ItemVo();
                    	itemId = paramValues[1];
                    	itemQuantity = request.getParameter(paramName);
                        selectedItem.setItemId(new Long(itemId));
                        if(null!=itemQuantity && !itemQuantity.trim().equals(""))
                        selectedItem.setItemQuantity(new Integer(itemQuantity));
                        if(selectedItem.getItemQuantity() >0) {
                        	selectedItems.add(selectedItem);
                        }                       
                    }
                    if ("stuffing".equalsIgnoreCase(paramValues[0])) {
                    	ToppingVo selectedTopping = new ToppingVo();
                    	toppingId = paramValues[1];
                    	toppingQuantity = request.getParameter(paramName);
                    	selectedTopping.setToppingId(new Long(toppingId));
                        if(null!=itemQuantity && !itemQuantity.trim().equals(""))
                        	selectedTopping.setQuantity(new Integer(toppingQuantity));
                        if(selectedTopping.getQuantity() >0) {
                        	selectedToppings.add(selectedTopping);
                        }                       
                    }
                }
            }            
        }
        Long orderIdLong = orderCreationService.createOrder(selectedItems, selectedToppings, orderId);
        mv = new ModelAndView("deliveryDetails");
        mv.addObject("orderId", orderIdLong);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/fetchDeliveryDetails")
	public ModelAndView fetchDeliveryDetails(HttpServletRequest request) {
		System.out.println("in fetch deliverydetails controller");
		String phoneNumber = request.getParameter("phoneNumber");
		ModelAndView mv = null;
		//List<AddressVo> addressList = getAddressList();
		List<AddressVo> addressList = customerInformationFetchService.getCustomerDeliveryAddressList(phoneNumber);
		mv = new ModelAndView("deliveryDetails");
		mv.addObject("addressList",addressList);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/fetchDeliverySlots")
	public ModelAndView fetchDeliverySlots(HttpServletRequest request) {
		System.out.println("in fetch deliverydetails controller");
		String zipcode = request.getParameter("zipcodeAddr");
		ModelAndView mv = null;
		List<AddressVo> addressList = getAddressList();
		List<DeliverySlotVo> deliverySlots = deliveryDetailsService.fetchDeliverySlots(zipcode);
				//customerInformationFetchService.getCustomerDeliveryAddressList(phoneNumber);
		mv = new ModelAndView("deliveryDetails");
		mv.addObject("deliverySlots",deliverySlots);
		return mv;
	}

	private List<AddressVo> getAddressList() {
		List<AddressVo> addressList = new ArrayList<AddressVo>();
		AddressVo address1= new AddressVo();
		address1.setAddressId(new Long(123));
		address1.setAddressLine1("D 303, Kool homes, Behind Maratha mandir");
		address1.setAddressline2("NDA road, Bavdhan");
		address1.setCity("Pune");
		address1.setState("Maharashtra");
		address1.setZipcode("411021");
		AddressVo address2= new AddressVo();
		address2.setAddressId(new Long(124));
		address2.setAddressLine1("D 303, Kool homes, Behind Maratha mandir");
		address2.setAddressline2("NDA road, Bavdhan");
		address2.setCity("Pune");
		address2.setState("Maharashtra");
		address2.setZipcode("411021");
		addressList.add(address1);
		addressList.add(address2);
		return addressList;
	}

	/**
	 * @param customerInformationFetchService the customerInformationFetchService to set
	 */
	public void setCustomerInformationFetchService(CustomerInformationFetchService customerInformationFetchService) {
		this.customerInformationFetchService = customerInformationFetchService;
	}

	/**
	 * @param orderCreationService the orderCreationService to set
	 */
	public void setOrderCreationService(OrderCreationService orderCreationService) {
		this.orderCreationService = orderCreationService;
	}
	
}
