package com.mkyong.web.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateTest {
	private static SessionFactory factory; 
	
	   public static void main(String[] args) {
		   Session session = HibernateUtil.getSessionFactory().openSession();
		   System.out.println("completed");
	   }
}
