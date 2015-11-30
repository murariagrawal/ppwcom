package com.panipuri.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.service.MasterDataFetchService;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.ToppingVo;

@Controller
public class LoginController {
	@Autowired
	private MasterDataFetchService masterDataFetchService;
	@RequestMapping(method = RequestMethod.POST, value="/login")
	public ModelAndView showMessage(
			@RequestParam(value = "username") String username, 
			@RequestParam(value = "password") String password) {
		System.out.println("in controller");
		ModelAndView mv = null;
		if(null!= username && username.equals("piyush")) {
			
			
			
			mv = new ModelAndView("redirect:home");
			
		} else if(null!= username && username.equals("anil")) {
			mv = new ModelAndView("redirect:adminhome");
			
		} else {
			mv = new ModelAndView("home");
			mv.addObject("message","Logged in");
			mv.addObject("username",username);
		}
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET, value="/abc")
	public ModelAndView home() {
		
		ModelAndView mv = null;		
		mv = new ModelAndView("index");			
		return mv;
	}
	/*@RequestMapping(method = RequestMethod.GET, value="/home")
	public ModelAndView homePaniPuri() {
		
		ModelAndView mv = null;		
		mv = new ModelAndView("home");	
		
		mv.addObject("orderId", "");
		return mv;
	}*/
	@RequestMapping(method = RequestMethod.GET, value="/home")
	public ModelAndView orderOnline() {
		List<ItemVo> itemList = masterDataFetchService.fetchAllAvailableItem();
		List<ToppingVo> stuffingList = masterDataFetchService.fetchAllAvailableStuffing();
		ModelAndView mv = null;		
		mv = new ModelAndView("homePaniPuri");	
		mv.addObject("itemList", itemList);
		mv.addObject("stuffingList", stuffingList);
		mv.addObject("orderId", "");
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET, value="/adminhome")
	public ModelAndView homeAdmin() {
		ModelAndView mv = null;		
		mv = new ModelAndView("adminHome");
		
		return mv;
	}
	/**
	 * @param masterDataFetchService the masterDataFetchService to set
	 */
	public void setMasterDataFetchService(MasterDataFetchService masterDataFetchService) {
		this.masterDataFetchService = masterDataFetchService;
	}
}
