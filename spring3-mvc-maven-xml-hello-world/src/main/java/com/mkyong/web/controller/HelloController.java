package com.mkyong.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	
	@RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
	public ModelAndView hello(@PathVariable("name") String name) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("msg", name);

		return model;

	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@PathVariable("username") String username,
			@PathVariable("password") String password) {

		ModelAndView model = new ModelAndView();
		model.setViewName("hello");
		model.addObject("msg", username);

		return model;

	}
}