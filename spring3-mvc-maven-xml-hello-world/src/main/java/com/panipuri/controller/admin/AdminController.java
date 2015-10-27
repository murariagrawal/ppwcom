package com.panipuri.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.AdminService;
import com.panipuri.vo.ItemVo;
@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(method = RequestMethod.POST, value="/addItem")
	public ModelAndView addItem(@RequestParam(value = "itemName") String itemName, 
			@RequestParam(value = "itemPrice") String itemPrice,
			@RequestParam(value = "itemDetails") String itemDetails) {
		ModelAndView mv = null;
		ItemVo item = new ItemVo();
		item.setItemName(itemName);
		item.setItemPrice(new BigDecimal(itemPrice));
		List<String> itemDetailsList = new ArrayList<String>();
		itemDetailsList.add(itemDetails);
		item.setItemDetails(itemDetailsList);
		adminService.addItem(item);
		mv = new ModelAndView("adminHome");
		return mv;
	}
}
