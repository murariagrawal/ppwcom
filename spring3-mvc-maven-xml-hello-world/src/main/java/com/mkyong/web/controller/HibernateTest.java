package com.mkyong.web.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateTest {
	private static SessionFactory factory; 
	public static Session getSession() 
    {
        if(factory == null)
        {
        	factory = new AnnotationConfiguration().configure().buildSessionFactory();
        }
        Session hibernateSession = factory.openSession();
        return hibernateSession;
    }
	   public static void main(String[] args) {
	      try{
	         
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
	   }
}
