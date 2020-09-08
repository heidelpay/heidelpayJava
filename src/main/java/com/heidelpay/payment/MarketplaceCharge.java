package com.heidelpay.payment;

import java.util.ArrayList;
import java.util.List;

import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

public class MarketplaceCharge extends AbstractTransaction<MarketplacePayment> {
	
	private String invoiceId;
	
	private List<MarketplaceCancel> cancelList;
	
	public MarketplaceCharge() {
		super();
		setCancelList(new ArrayList<MarketplaceCancel>());
	}
	public MarketplaceCharge(Heidelpay heidelpay) {
		super(heidelpay);
		setCancelList(new ArrayList<MarketplaceCancel>());
	}

	@Override
	public String getTypeUrl() {
		return "marketplace/payments/<paymentId>/charges";
	}
	
	/**
	 * Fully cancel for Marketplace Charge
	 * @param cancel refers to MarketplaceCancel.FullChargeCancel
	 * @return
	 * @throws HttpCommunicationException
	 */
	public MarketplacePayment fullCancel(MarketplaceCancel.FullChargeCancel cancel) throws HttpCommunicationException {		
		return getHeidelpay().fulllCancel(getPayment().getId(), cancel);
	}


	@Override
	public PaymentType map(PaymentType paymentType, JsonObject jsonObject) {
		return null;
	}
	
	public String getInvoiceId() {
		return invoiceId;
	}

	public MarketplaceCharge setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
		return this;
	}

	public List<MarketplaceCancel> getCancelList() {
		return cancelList;
	}

	public void setCancelList(List<MarketplaceCancel> cancelList) {
		this.cancelList = cancelList;
	}
}
