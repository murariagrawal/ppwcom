package com.panipuri;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Test implements ApplicationContextAware {
	public static void main(String[] args) {
		try{
			final Runnable beeper = new Runnable() {
			       public void run() { System.out.println("beep"); }
			     };
			ScheduledExecutorService scheduledExecutorService =
		        Executors.newScheduledThreadPool(5);
			final ScheduledFuture<?> beeperHandle =
				 scheduledExecutorService.scheduleAtFixedRate(beeper, 0, 5*60, TimeUnit.SECONDS);

		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		
	}
}
