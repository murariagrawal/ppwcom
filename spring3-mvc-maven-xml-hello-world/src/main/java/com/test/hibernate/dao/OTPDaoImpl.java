package com.test.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.test.hibernate.OneTimePassword;
import com.test.hibernate.Order;

public class OTPDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	public void deleteOldOTPs() {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		long oldTime = new Date().getTime();
		oldTime = oldTime - (15*60*100);
		
		List<OneTimePassword> oldOtps = (List<OneTimePassword>)session.createCriteria(OneTimePassword.class).add(Restrictions.le("generatedTime", oldTime));
		session.delete(oldOtps);
		session.getTransaction().commit();
		session.close();
		
	}
	public OneTimePassword getOTPDetails(String otp, String contactNumber, long OrderId) {
		Session session = this.sessionFactory.getCurrentSession();
		OneTimePassword oneTimePassword =  (OneTimePassword)session.get(OneTimePassword.class, otp);
		return oneTimePassword;
	}
	public void addOTP(String otp, long orderId, String contactNumber) {
		Session session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		Order order = (Order) session.get(Order.class, orderId);
		OneTimePassword oneTimePassword = new OneTimePassword();
		oneTimePassword.setContactNumber(contactNumber);
		oneTimePassword.setOtp(otp);
		oneTimePassword.setOrder(order);		
		oneTimePassword.setGeneratedTime(new Date().getTime());
		session.save(oneTimePassword);
		session.getTransaction().commit();
		session.close();
	}
}
