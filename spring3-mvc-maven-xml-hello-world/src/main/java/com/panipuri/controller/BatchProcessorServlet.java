package com.panipuri.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.test.hibernate.dao.DeliverySlotDaoImpl;
import com.test.hibernate.dao.OTPDaoImpl;
@SuppressWarnings("serial")
@Component
public class BatchProcessorServlet extends GenericServlet implements ApplicationContextAware {
	private static ApplicationContext ctx;

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		System.out.println();
		final Runnable delteOtps = new Runnable() {
		       public void run() { 
		    	   OTPDaoImpl otpDao = (OTPDaoImpl)ctx.getBean("oTPDaoImpl");
		    	   otpDao.deleteOldOTPs();
		    	   System.out.println("beep"); 
		    	   }
		     };
		     final Runnable stockUpdate = new Runnable() {
			       public void run() { 
			    	   DeliverySlotDaoImpl slotDaoImpl = (DeliverySlotDaoImpl)ctx.getBean("deliverySlotDaoImpl");
			    	   slotDaoImpl.resetSlotQuantity();
			    	   System.out.println("beep2"); 
			    	   }
			     };
		ScheduledExecutorService scheduledExecutorService =
	        Executors.newScheduledThreadPool(5);
		final ScheduledFuture<?> otpDeleter =
			 scheduledExecutorService.scheduleAtFixedRate(delteOtps, 0, 5*60, TimeUnit.SECONDS);
		Calendar cal =Calendar.getInstance(TimeZone.getTimeZone("IST"));
		Calendar cal2 =Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal2.set(Calendar.HOUR_OF_DAY,23);
		cal2.set(Calendar.MINUTE,55);
		cal2.set(Calendar.SECOND,0);
		cal2.set(Calendar.MILLISECOND,0);
		Date d = cal.getTime();
		Date d2 = cal2.getTime();
		long delay = d2.getTime() -d.getTime();
		final ScheduledFuture<?> stockUpdater =
				 scheduledExecutorService.scheduleAtFixedRate(stockUpdate, delay, 24*60*60, TimeUnit.SECONDS);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		ctx = arg0;
		
	}
	

}
