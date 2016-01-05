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
import com.panipuri.service.MasterDataFetchService;
import com.panipuri.service.OrderCreationService;
import com.panipuri.vo.AddressVo;
import com.panipuri.vo.ComboItemVo;
import com.panipuri.vo.DeliverySlotInfoVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.OrderVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.ComboItemQuantity;
import com.test.hibernate.Customer;
import com.test.hibernate.DeliveryArea;
import com.test.hibernate.DeliverySlotStock;

@Controller
public class OrderInitiationController {
	@Autowired
	private CustomerInformationFetchService customerInformationFetchService;
	@Autowired
	private OrderCreationService orderCreationService;
	@Autowired
	private DeliveryDetailsService deliveryDetailsService;
	@Autowired
	private MasterDataFetchService masterDataFetchService;
	@RequestMapping(method = RequestMethod.POST, value="/updateItemStock")
	public ModelAndView updateItemStock(final HttpServletRequest request, @RequestParam(value = "selectedAreaId") String selectedAreaId,
			 @RequestParam(value = "phoneNumber") String phoneNumber) {
		System.out.println("in controller");
		DeliveryArea area = orderCreationService.fetchStockList(phoneNumber, selectedAreaId);
		List<ItemVo> itemList = masterDataFetchService.fetchAllComboItem();
		OrderVo orderIdLong = orderCreationService.createOrder(null, null, null, phoneNumber, selectedAreaId);
		
		List<DeliverySlotStock> stockList = area.getMasterArea().getDeliveryStockList();
		List<String> itemIds = null;
		List<String> quantity = null;
		List<ComboItemVo> comboItems = new ArrayList<ComboItemVo>();
		for(ItemVo item:itemList) {
			if(item.isComboItem()) {
				int comboQuantity = 0;
				ComboItemVo comboItemVo = new ComboItemVo();
				comboItemVo.setComboItemId(""+item.getItemId());
				List<ComboItemQuantity> itemQuantityList = item.getComboQuantityList();
				itemIds = new ArrayList<String>();
				quantity = new ArrayList<String>();
				for(ComboItemQuantity comboItemQuantity :itemQuantityList) {
					itemIds.add(""+comboItemQuantity.getItemIds());
					quantity.add(""+comboItemQuantity.getQuantity());
					comboItemVo.setItemIds(itemIds);
					comboItemVo.setQuantity(quantity);					
					int itemQuantityValue= 0;
					for(DeliverySlotStock stock:stockList) {
						if(!stock.isStuffing() && stock.getId() == Long.parseLong(comboItemQuantity.getItemIds()) ) {
							if(stock.getQuantity() >0 && comboItemQuantity.getQuantity()  >0) {
								itemQuantityValue = stock.getQuantity()/comboItemQuantity.getQuantity();
							}
							break;
						}
					}
					if(itemQuantityValue >0) {
						
						if(comboQuantity == 0 || comboQuantity > itemQuantityValue) {
							comboQuantity = itemQuantityValue;
						} 
					}					
				}
				DeliverySlotStock comboStock  =  new DeliverySlotStock();
				
				comboStock.setId(item.getItemId());
				comboStock.setQuantity(comboQuantity);
				comboStock.setStuffing(false);
				stockList.add(comboStock);
				comboItems.add(comboItemVo);
			}
		}
		ModelAndView mv = new ModelAndView("");
		DeliverySlotInfoVo deliverySlotInfo = null;
		try {
			deliverySlotInfo = deliveryDetailsService.fetchDeliverySlots(selectedAreaId, ""+orderIdLong.getOrderId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(DeliverySlotStock stock:stockList) {
			stock.setSlot(null);
			stock.setArea(null);
		}
		mv.addObject("orderId", orderIdLong.getOrderId());
		mv.addObject("availableStock", area.getMasterArea().getDeliveryStockList());
		mv.addObject("comboItems", comboItems);
		mv.addObject("availableSlots", deliverySlotInfo.getAvailableDeliverySlotList());
		return mv;
	}
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
                        if(null!=toppingQuantity && !toppingQuantity.trim().equals(""))
                        	selectedTopping.setQuantity(new Integer(toppingQuantity));
                        if(null != selectedTopping.getQuantity() && selectedTopping.getQuantity() >0) {
                        	selectedToppings.add(selectedTopping);
                        }                       
                    }
                }
            }            
        }
        OrderVo orderIdLong = orderCreationService.createOrder(selectedItems, selectedToppings, orderId, null, null);
        mv = new ModelAndView("deliveryDetails");
        mv.addObject("orderId", orderIdLong.getOrderId());
        mv.addObject("contactNumber", orderIdLong.getContactNo());
        
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/fetchDeliveryDetails")
	public ModelAndView fetchDeliveryDetails(HttpServletRequest request) {
		System.out.println("in fetch deliverydetails controller");
		String phoneNumber = request.getParameter("phoneNumber");
		String addressId = request.getParameter("deliveryAreaID");
		ModelAndView mv = null;
		//List<AddressVo> addressList = getAddressList();
		List<AddressVo> addressList = customerInformationFetchService.getCustomerDeliveryAddressList(phoneNumber, addressId);
		Customer customer = customerInformationFetchService.fetchCustomerinfo(phoneNumber);
		String emailAddress = "";
		if(null != customer && null != customer.getEmailAddress()) {
			emailAddress =customer.getEmailAddress();
		}
		mv = new ModelAndView("deliveryDetails");
		mv.addObject("addressList",addressList);
		mv.addObject("emailAddress",emailAddress);
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/fetchDeliverySlots")
	public ModelAndView fetchDeliverySlots(HttpServletRequest request) {
		System.out.println("in fetch deliverydetails controller");
		String areaId = request.getParameter("deliveryAreaID");
		String orderId = request.getParameter("deliveryOrderId");
		ModelAndView mv = null;
		DeliverySlotInfoVo deliverySlotsInfo = null;
		String errorMessage = "";
		try {
			deliverySlotsInfo = deliveryDetailsService.fetchDeliverySlots(areaId, orderId);
				//customerInformationFetchService.getCustomerDeliveryAddressList(phoneNumber);
		} catch (Exception e) {
			String message = e.getMessage();
			
			if(null != message && message.equals("ERR_NO_AREA")) {
				errorMessage = "Sorry! We currently are not serving your area. We will inform you once we start serving your area.";
			} else if(null != message && message.equals("ERR_NO_SLOT")) {
				errorMessage = "Sorry all the slots for today are full in your area. Please try again tommorow.";
			} else {
				errorMessage = "Something went wrong. Please try again.";
			}
		}
		mv = new ModelAndView("deliveryDetails");
		if(errorMessage.equals("")) {
			mv.addObject("deliverySlots",deliverySlotsInfo.getAvailableDeliverySlotList());
			mv.addObject("fullDeliverySlots",deliverySlotsInfo.getFullDeliverySlotsList());
			mv.addObject("unavailableItems",deliverySlotsInfo.getUnavailbleItemList());
		} else {
			mv.addObject("errormessage",errorMessage);
		}
		return mv;
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
