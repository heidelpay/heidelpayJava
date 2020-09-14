package com.heidelpay.payment.marketplace;

import java.util.List;

import com.heidelpay.payment.AbstractTransaction;
import com.heidelpay.payment.Heidelpay;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

public class MarketplaceAuthorization extends AbstractTransaction<MarketplacePayment> {
	
	private List<MarketplaceCancel> cancelList;
	
	public MarketplaceAuthorization() {
		super();
	}

	public MarketplaceAuthorization(Heidelpay heidelpay) {
		super(heidelpay);
	}

	@Override
	public String getTypeUrl() {
		return "marketplace/payments/<paymentId>/authorize";
	}

	/**
	 * Fully cancel for Marketplace Authorization
	 * <b>Note:</b>: <code>amount</code> will be ignored due to fully cancel. Only <code>paymentReference</code> is processed.
	 * 
	 * @param cancel refers MarketplaceCancel.FullAuthorizationCancel
	 * @return
	 * @throws HttpCommunicationException
	 */
	public MarketplacePayment fullCancel(String paymentReference) throws HttpCommunicationException {
		MarketplaceCancel cancel = new MarketplaceCancel();
		cancel.setPaymentReference(paymentReference);
		return getHeidelpay().marketplaceFullAuthorizeCancel(getPayment().getId(), cancel);
	}

	@Override
	public PaymentType map(PaymentType paymentType, JsonObject jsonObject) {
		return null;
	}
	
	public List<MarketplaceCancel> getCancelList() {
		return cancelList;
	}

	public void setCancelList(List<MarketplaceCancel> cancelList) {
		this.cancelList = cancelList;
	}

	public MarketplaceCancel getCancel(String cancelId) {
		if (cancelList == null) return null;
		for (MarketplaceCancel cancel : cancelList) {
			if (cancelId.equalsIgnoreCase(cancel.getId())) {
				return cancel;
			}
		}
		return null;
	}
}
