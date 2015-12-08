package com.panipuri.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.MasterDataFetchService;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;
import com.test.hibernate.PartyItemQuantity;
@Controller
public class AdminController {
	@Autowired
	private MasterDataFetchService masterDataFetchService;
	
	@RequestMapping(method = RequestMethod.GET, value="/getAllItemAndStuffing")
	public ModelAndView fetchAllItemAndStuffing() {
		List<ItemVo> itemList = masterDataFetchService.fetchAllAvailableItem();
		List<ToppingVo> stuffingList = masterDataFetchService.fetchAllAvailableStuffing();
		List<PartyItemQuantity> quantityList = new ArrayList<PartyItemQuantity>();
		if(null != itemList) {
			for(ItemVo itemVo:itemList) {
				if(itemVo.isPartyItem()) {					
					quantityList.addAll(itemVo.getPartyQuantitylist());
					itemVo.setPartyQuantitylist(null);
				}
			}
		}
		ModelAndView mv = null;		
		mv = new ModelAndView("");	
		mv.addObject("itemList", itemList);
		mv.addObject("quantityList",quantityList);
		mv.addObject("stuffingList", stuffingList);
		
		return mv;
	}
}
