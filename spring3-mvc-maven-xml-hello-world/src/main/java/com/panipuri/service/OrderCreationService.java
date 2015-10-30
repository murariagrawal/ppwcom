package com.panipuri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.panipuri.vo.ToppingVo;
import com.panipuri.vo.ItemVo;
import com.panipuri.vo.StatusVo;
import com.test.hibernate.dao.OderDaoImpl;
@Component
@Lazy
public class OrderCreationService {
	@Autowired
	OderDaoImpl oderDaoImpl;
	public StatusVo createOrder(List<ItemVo> selectedItems, List<ToppingVo> selectedToppings) {
		oderDaoImpl.addOrder(selectedItems, selectedToppings);
		
		return new StatusVo();
	}
}
