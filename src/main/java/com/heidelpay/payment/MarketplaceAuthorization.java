package com.heidelpay.payment;

public class MarketplaceAuthorization extends Authorization {
	
	@Override
	public String getTypeUrl() {
		return "marketplace/payments/<paymentId>/authorize";
	}
}
