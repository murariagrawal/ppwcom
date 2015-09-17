package com.mkyong.web.controller;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory ;
    static {
   	Configuration configuration = new Configuration().configure();
   	//StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
       sessionFactory = configuration.buildSessionFactory();
    }
   public static SessionFactory getSessionFactory() {
       return sessionFactory;
   }
}
