package com.test.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext appContext = 
	    		new ClassPathXmlApplicationContext("spring-web-servlet.xml");
	}

}
