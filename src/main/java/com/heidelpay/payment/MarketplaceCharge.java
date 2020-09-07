package com.heidelpay.payment;

public class MarketplaceCharge extends Charge {

	@Override
	public String getTypeUrl() {
		return "marketplace/payments/<paymentId>/charges";
	}
}
