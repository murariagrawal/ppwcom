package com.panipuri.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.MasterDataFetchService;
import com.panipuri.service.admin.AdminService;
import com.panipuri.vo.ItemVo;
import com.test.hibernate.PartyItemQuantity;
@Controller
public class ItemController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private MasterDataFetchService masterService;
	@RequestMapping(method = RequestMethod.POST, value="/addItem")
	public ModelAndView addItem(@RequestParam(value = "itemName") String itemName, 
			@RequestParam(value = "itemDetails") String itemDetails,  HttpServletRequest request) {
		ModelAndView mv = null;
		ItemVo item = new ItemVo();
		item.setItemName(itemName);
		String partyItem = request.getParameter("partyItem");
		if(null!= partyItem && partyItem.equals("on")) {
			item.setPartyItem(true);
			String[] quantity;
			quantity = request.getParameterValues("partyItemQuantity");
			String[] price;
			price = request.getParameterValues("partyItemPrice");
			List<PartyItemQuantity> partyItemQuantityList = new ArrayList<PartyItemQuantity>();
			PartyItemQuantity partyItemQuantity = null;
			for(int j = 0; j < quantity.length; j++) {
				partyItemQuantity = new PartyItemQuantity();
				partyItemQuantity.setPrice(new BigDecimal(price[j]));
				partyItemQuantity.setQuantity(new Integer(quantity[j]));
				partyItemQuantityList.add(partyItemQuantity);
			}
			item.setPartyQuantitylist(partyItemQuantityList);
		} else {
			String itemPrice = request.getParameter("itemPrice");
			item.setItemPrice(new BigDecimal(itemPrice));
		}
		
		List<String> itemDetailsList = new ArrayList<String>();
		itemDetailsList.add(itemDetails);
		item.setItemDetails(itemDetailsList);
		adminService.addItem(item);
		mv = new ModelAndView("");
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET, value="/getAllItems")
	public ModelAndView getAllItems() {
		ModelAndView mv = null;
		
		List<ItemVo> itemList = masterService.fetchAllAvailableItem();
		mv = new ModelAndView("");
		mv.addObject("itemList",itemList);
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/updateItem")
	public ModelAndView updateItem(@RequestParam(value = "selectUpdateItem") String itemId, 
			@RequestParam(value = "itemUpdatePrice") String itemPrice,
			@RequestParam(value = "itemUpdateDetails") String itemDetails) {
		ModelAndView mv = null;
		ItemVo item = new ItemVo();
		item.setItemId(new Long(itemId));
		item.setItemPrice(new BigDecimal(itemPrice));
		List<String> itemDetailsList = new ArrayList<String>();
		itemDetailsList.add(itemDetails);
		item.setItemDetails(itemDetailsList);
		adminService.updateItem(item);
		mv = new ModelAndView("");
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/deleteItem")
	public ModelAndView deleteItem(@RequestParam(value = "selectDeleteItem") String itemId) {
		ModelAndView mv = null;
		
		adminService.deleteItem(new Long(itemId));
		mv = new ModelAndView("adminHome");
		return mv;
	}
}
