package com.test.hibernate.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.panipuri.vo.DiscountVo;
import com.test.hibernate.DiscountInformation;
import com.test.hibernate.Item;

public class DiscountDaoImpl {
	private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
    
    public void addDiscount(DiscountVo discountVo) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		DiscountInformation discountInfo = new DiscountInformation();
		discountInfo.setCouponCode(discountVo.getCouponCode());
		discountInfo.setDiscountCategory(discountVo.getDiscountCategory().name());
		discountInfo.setDiscountPercentage(discountVo.getDiscountPercentage());
		discountInfo.setStartDate(discountVo.getStartDate());
		discountInfo.setEndDate(discountVo.getEndDate());
		discountInfo.setOncePerUser(discountVo.isOncePerUser());
		discountInfo.setDiscountCondition(discountVo.getDiscountCondition());
		discountInfo.setDiscountConditionValue(discountVo.getDiscountConditionValue());
		
		session.save(discountInfo);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<DiscountInformation> getAllAvailableDiscounts() {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		List<DiscountInformation> allAvailableDiscounts = (List<DiscountInformation>)session.createCriteria(DiscountInformation.class).list();
		session.getTransaction().commit();
		session.close();
		return allAvailableDiscounts;
	}
	
	public void updateItemDetails(Date endDate, Float discountPercentage, long discountId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		DiscountInformation selectedDiscount = (DiscountInformation)session.get(DiscountInformation.class, discountId);
		if(null != selectedDiscount) {
			if(null != endDate) {
				selectedDiscount.setEndDate(endDate);
			} 
			if(null != discountPercentage) {
				selectedDiscount.setDiscountPercentage(discountPercentage);
			}
			
			session.update(selectedDiscount);
		}
		session.getTransaction().commit();
		session.close();
		
	}
	
	public void deleteItem(long discountId) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		DiscountInformation selectedItem = (DiscountInformation)session.get(DiscountInformation.class, discountId);
		session.delete(selectedItem);
		session.getTransaction().commit();
		session.close();
		
	}
	
	public DiscountInformation getDiscountInformation(String couponCode) {
		Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		DiscountInformation discountInfo = null;
		List<DiscountInformation> selectedCoupon= (List<DiscountInformation>)session.createCriteria(DiscountInformation.class)
											.add(Restrictions.eq("couponCode", couponCode)).list();
		if(null != selectedCoupon && !selectedCoupon.isEmpty()) {
			discountInfo = selectedCoupon.get(0);
		} 
		session.getTransaction().commit();
		session.close();
		return discountInfo;
	}
}
