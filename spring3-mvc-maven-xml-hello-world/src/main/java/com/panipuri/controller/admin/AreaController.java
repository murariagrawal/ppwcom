package com.panipuri.controller.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.panipuri.AreaSlotOrderBean;
import com.panipuri.service.admin.AdminService;
import com.panipuri.vo.AreaSubAreaVo;
import com.panipuri.vo.OrderVo;
import com.test.hibernate.DeliverySlot;
import com.test.hibernate.DeliverySlotStock;
import com.test.hibernate.MasterDeliveryArea;
@Controller
public class AreaController {
	@Autowired
	private AdminService adminService;
	@RequestMapping(method = RequestMethod.POST, value="/addMasterArea")
	public ModelAndView addMasterArea(@RequestParam(value = "areaName") String areaName, 
			@RequestParam(value = "areaCity") String areaCity,
			@RequestParam(value = "areaState") String areaState, HttpServletRequest request) {
		ModelAndView mv = null;
		
		String[] slots;
		slots = request.getParameterValues("deliverySlots");
		String[] quantity;
		quantity = request.getParameterValues("dliveryQuantity");
		String[] itemIds = request.getParameterValues("itemId");
		String[] stuffingIds = request.getParameterValues("stuffingId");
		String[] stuffingQuantity = request.getParameterValues("stuffingQuantity");
		String[] itemQuantity = request.getParameterValues("itemQuantity");
		
		List<DeliverySlot> deliverySlots = new ArrayList<DeliverySlot>();
		DeliverySlot deliverySlot = null;
		
		for(int k = 0; k < slots.length; k++) {
			List<DeliverySlotStock> stockInfo = new ArrayList<DeliverySlotStock>();
			DeliverySlotStock deliverySlotStock =  null;
			
			deliverySlot = new DeliverySlot();
			String[] slotTimes = slots[k].split("-");
			deliverySlot.setStartTime(slotTimes[0]+"PM");
			deliverySlot.setStartTimeNum(Integer.parseInt(slotTimes[0]));
			deliverySlot.setEndTime(slotTimes[1]+"PM");
			deliverySlot.setSlotQuantity(Integer.parseInt(quantity[k]));
			deliverySlot.setTodaySlotQuantity(Integer.parseInt(quantity[k]));
			for(int i = 0; i < itemIds.length; i++) {
				deliverySlotStock = new DeliverySlotStock();
				deliverySlotStock.setId(new Long(itemIds[i]));
				deliverySlotStock.setQuantity(new Integer(itemQuantity[i]));
				deliverySlotStock.setInitialQuantity(new Integer(itemQuantity[i]));
				deliverySlotStock.setQuantityOrdered(0);
				deliverySlotStock.setSlot(deliverySlot);
				stockInfo.add(deliverySlotStock);
			}
			for(int j = 0; j < stuffingIds.length; j++) {
				deliverySlotStock = new DeliverySlotStock();
				deliverySlotStock.setId(new Long(stuffingIds[j]));
				deliverySlotStock.setQuantity(new Integer(stuffingQuantity[j]));
				deliverySlotStock.setInitialQuantity(new Integer(stuffingQuantity[j]));
				deliverySlotStock.setQuantityOrdered(0);
				deliverySlotStock.setStuffing(true);
				deliverySlotStock.setSlot(deliverySlot);
				stockInfo.add(deliverySlotStock);
			}
			
			deliverySlot.setDeliveryStockList(stockInfo);
			deliverySlots.add(deliverySlot);
		}
		List<DeliverySlotStock> stockInfo = new ArrayList<DeliverySlotStock>();
		DeliverySlotStock deliverySlotStock =  null;
		for(int i = 0; i < itemIds.length; i++) {
			deliverySlotStock = new DeliverySlotStock();
			deliverySlotStock.setId(new Long(itemIds[i]));
			deliverySlotStock.setQuantity(new Integer(itemQuantity[i]));
			deliverySlotStock.setInitialQuantity(new Integer(itemQuantity[i]));
			deliverySlotStock.setQuantityOrdered(0);			
			
			stockInfo.add(deliverySlotStock);
		}
		for(int j = 0; j < stuffingIds.length; j++) {
			deliverySlotStock = new DeliverySlotStock();
			deliverySlotStock.setId(new Long(stuffingIds[j]));
			deliverySlotStock.setQuantity(new Integer(stuffingQuantity[j]));
			deliverySlotStock.setInitialQuantity(new Integer(stuffingQuantity[j]));
			deliverySlotStock.setQuantityOrdered(0);			
			deliverySlotStock.setStuffing(true);
			
			stockInfo.add(deliverySlotStock);
		}
		adminService.addMasterArea(areaName, areaCity, areaState, deliverySlots, stockInfo);
		mv = new ModelAndView("adminHome");
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/addArea")
	public ModelAndView addArea(@RequestParam(value = "deliveryAreaName") String areaName, 
			 HttpServletRequest request) {
		ModelAndView mv = null;
		
		String subAreaName = request.getParameter("deliverySubAreaName");
		String zipcode = request.getParameter("areaZipcode");
		String masterAreaId = request.getParameter("masterArea");
		String servingStr = request.getParameter("serving");
		String servingPartyStr = request.getParameter("servingParty");
		Long zipcodeLong = null;
		Long masterAreaIdLong = null;
		if(null != zipcode && !zipcode.equals("")) {
			zipcodeLong = new Long(zipcode);
		}
		if(null != masterAreaId && !masterAreaId.equals("")) {
			masterAreaIdLong = new Long(masterAreaId);
		}
		boolean serving = false;
		if(null != servingStr && servingStr.equals("on")) {
			serving = true;
		}
		boolean servingParty = false;
		if(null != servingPartyStr && servingPartyStr.equals("on")) {
			servingParty = true;
		}
		adminService.addArea(areaName, subAreaName, zipcodeLong, masterAreaIdLong, serving, servingParty);
		mv = new ModelAndView("adminHome");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/getAreaStockAndSlot")
	public ModelAndView getAreaStockAndSlot(HttpServletRequest request) {
		ModelAndView mv = null;
		
		String masterAreaId = request.getParameter("masterAreaStock");
		
		MasterDeliveryArea masterArea = adminService.fetchMasterArea(masterAreaId);
		 //List<OrderVo> orderList = adminService.fetchOrdersInArea(masterAreaId);
		masterArea.setDeliveryStockList(null);
		
		for(DeliverySlot slot:masterArea.getDeliverySlots()) {
			slot.setDeliveryArea(null);	
			for(DeliverySlotStock slotStock :slot.getDeliveryStockList()) {
				slotStock.setArea(null);
				slotStock.setSlot(null);
				
			}
		}
		mv = new ModelAndView("adminHome");
		//mv.addObject("areaStock", masterArea.getDeliveryStockList());
		mv.addObject("areaSlots", masterArea.getDeliverySlots());
		//mv.addObject("orderList", orderList);
		return mv;
	}
	@RequestMapping(method = RequestMethod.POST, value="/addStockToArea")
	public ModelAndView addStockToArea(HttpServletRequest request) {
		ModelAndView mv = null;
		String areaId = request.getParameter("masterAreaStock");
		String slotStartTimeStr = request.getParameter("slotStock");
		int slotStartTime = Integer.parseInt(slotStartTimeStr);
		String[] itemIds = request.getParameterValues("itemIdStock");
		String[] stuffingIds = request.getParameterValues("stuffingIdStock");
		String[] stuffingQuantity = request.getParameterValues("stuffingQuantityStock");
		String[] itemQuantity = request.getParameterValues("itemQuantityStock");
		
		List<DeliverySlotStock> stockInfo = new ArrayList<DeliverySlotStock>();
		DeliverySlotStock deliverySlotStock =  null;
		for(int i = 0; i < itemIds.length; i++) {
			deliverySlotStock = new DeliverySlotStock();
			deliverySlotStock.setId(new Long(itemIds[i]));
			deliverySlotStock.setQuantity(new Integer(itemQuantity[i]));			
			stockInfo.add(deliverySlotStock);
		}
		for(int j = 0; j < stuffingIds.length; j++) {
			deliverySlotStock = new DeliverySlotStock();
			deliverySlotStock.setId(new Long(stuffingIds[j]));
			deliverySlotStock.setQuantity(new Integer(stuffingQuantity[j]));						
			deliverySlotStock.setStuffing(true);			
			stockInfo.add(deliverySlotStock);
		}
		adminService.addStockToArea(areaId, slotStartTime, stockInfo);
		
		mv = new ModelAndView("adminHome");
		
		return mv;
	}
	private List<AreaSlotOrderBean> getAreaSlotOrders(List<OrderVo> orderList, MasterDeliveryArea area) {
		List<AreaSlotOrderBean> areaSlotOrderList = new ArrayList<AreaSlotOrderBean>();
		List<DeliverySlot> slots = area.getDeliverySlots();
		String deliverySlotTime = "";
		AreaSlotOrderBean areaBean = null;
		LinkedHashMap<String, AreaSlotOrderBean> areaSlotOrderMap =  new LinkedHashMap<String, AreaSlotOrderBean>();
		for(DeliverySlot slot :slots) {
			deliverySlotTime = slot.getStartTime()+"-"+slot.getEndTime();
			areaBean = new AreaSlotOrderBean();
			areaBean.setSlotTime(deliverySlotTime);
			areaBean.setSlotQuantity(String.valueOf(slot.getTodaySlotQuantity()));
			areaSlotOrderMap.put(deliverySlotTime, areaBean);
		}
		for(OrderVo order:orderList) {
			order.getDeliverySlot();
			AreaSlotOrderBean areaSlotBean = areaSlotOrderMap.get(order.getDeliverySlot());
			areaSlotBean.getItemId();
			areaSlotBean.getItemOrderQuantity();
				
				
				if(order.getDeliverySlot().equals(deliverySlotTime)) {
					areaBean = new AreaSlotOrderBean();
					areaBean.setSlotTime(deliverySlotTime);
					
				}
			
		}
		
		
		return null;
	}
	@RequestMapping(method = RequestMethod.GET, value="/fetchAllArea")
	public ModelAndView fetchAllArea() {
		ModelAndView mv = null;
		
		List<AreaSubAreaVo> allDeliveryAreas = adminService.fetchAllArea();
		mv = new ModelAndView("adminHome");
		mv.addObject("deliveryArea", allDeliveryAreas);
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET, value="/fetchAllMasterArea")
	public ModelAndView fetchAllMasterArea() {
		ModelAndView mv = null;
		
		List<MasterDeliveryArea> allDeliveryAreas = adminService.fetchAllMasterArea();
		for(MasterDeliveryArea area:allDeliveryAreas) {
			area.setDeliveryStockList(null);
		}
		mv = new ModelAndView("adminHome");
		mv.addObject("masterDeliveryArea", allDeliveryAreas);
		return mv;
	}
}
