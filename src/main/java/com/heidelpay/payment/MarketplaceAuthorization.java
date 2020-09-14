package com.heidelpay.payment;

import java.util.List;

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
	
	public MarketplaceCharge charge(MarketplaceCharge charge) throws HttpCommunicationException {
		return getHeidelpay().marketplaceChargeAuthorization(this.getPaymentId(), this.getId(), charge);
	}
}
