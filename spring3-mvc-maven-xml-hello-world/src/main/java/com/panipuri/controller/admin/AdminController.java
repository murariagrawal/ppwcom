package com.panipuri.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.MasterDataFetchService;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;
@Controller
public class AdminController {
	@Autowired
	private MasterDataFetchService masterDataFetchService;
	
	@RequestMapping(method = RequestMethod.GET, value="/getAllItemAndStuffing")
	public ModelAndView fetchAllItemAndStuffing() {
		List<ItemVo> itemList = masterDataFetchService.fetchAllAvailableItem();
		List<ToppingVo> stuffingList = masterDataFetchService.fetchAllAvailableStuffing();
		ModelAndView mv = null;		
		mv = new ModelAndView("");	
		mv.addObject("itemList", itemList);
		mv.addObject("stuffingList", stuffingList);
		
		return mv;
	}
}
