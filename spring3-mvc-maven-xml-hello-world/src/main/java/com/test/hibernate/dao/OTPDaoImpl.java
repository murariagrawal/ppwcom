package com.test.hibernate.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.test.hibernate.OneTimePassword;
import com.test.hibernate.Order;

public class OTPDaoImpl extends HibernateDaoSupport {
	public void deleteOldOTPs() {
		long oldTime = new Date().getTime();
		oldTime = oldTime - (15*60*100);
		DetachedCriteria oldOtpCriteria = DetachedCriteria.forClass(OneTimePassword.class).add(Restrictions.le("generatedTime", oldTime));
		List<OneTimePassword> oldOtps = getHibernateTemplate().findByCriteria(oldOtpCriteria);
		getHibernateTemplate().deleteAll(oldOtps);
		
	}
	public OneTimePassword getOTPDetails(String otp, String contactNumber, long OrderId) {
		OneTimePassword oneTimePassword =  (OneTimePassword)getHibernateTemplate().get(OneTimePassword.class, otp);
		return oneTimePassword;
	}
	public void addOTP(String otp, long orderId, String contactNumber) {
		Order order = (Order) getHibernateTemplate().get(Order.class, orderId);
		OneTimePassword oneTimePassword = new OneTimePassword();
		oneTimePassword.setContactNumber(contactNumber);
		oneTimePassword.setOtp(otp);
		oneTimePassword.setOrder(order);		
		oneTimePassword.setGeneratedTime(new Date().getTime());
		getHibernateTemplate().save(oneTimePassword);
	}
}
