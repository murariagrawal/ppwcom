package com.panipuri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.panipuri.vo.ItemVo;
import com.panipuri.vo.StatusVo;
import com.test.hibernate.dao.ItemDaoImpl;
@Component
public class AdminService {
	@Autowired
	private ItemDaoImpl itemDaoImpl;
	public StatusVo addItem(ItemVo item) {
		itemDaoImpl.addItem(item);
		return new StatusVo();
	}
}
