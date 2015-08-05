package com.test.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cash_invoice")
public class CashInvoice {
	@Id
	@Column(name="cash_invoice_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long invoiceId;
	@OneToOne(fetch=FetchType.EAGER)
	private Order order;
	/**
	 * @return the invoiceId
	 */
	public long getInvoiceId() {
		return invoiceId;
	}
	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
}
