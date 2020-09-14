package com.heidelpay.payment.marketplace;

import java.util.ArrayList;
import java.util.List;

import com.heidelpay.payment.AbstractTransaction;
import com.heidelpay.payment.Heidelpay;
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
	 * <b>Note:</b>: <code>amount</code> will be ignored due to fully cancel. Only <code>paymentReference</code> is processed.
	 * 
	 * @param cancel refers to MarketplaceCancel.FullChargeCancel
	 * @return
	 * @throws HttpCommunicationException
	 */
	public MarketplacePayment fullCancel(String paymentReference) throws HttpCommunicationException {
		MarketplaceCancel cancel = new MarketplaceCancel();
		cancel.setPaymentReference(paymentReference);
		return getHeidelpay().marketplaceFullChargesCancel(getPayment().getId(), cancel);
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
