package com.heidelpay.payment;

import java.util.List;

public class MarketplacePayment extends Payment {
	
	private List<Authorization> authorizationsList;

	public MarketplacePayment(Heidelpay heidelpay) {
		super(heidelpay);
	}

	@Override
	public String getTypeUrl() {
		return "marketplace/payments";
	}

	public List<Authorization> getAuthorizationsList() {
		return authorizationsList;
	}

	public void setAuthorizationsList(List<Authorization> authorizationsList) {
		this.authorizationsList = authorizationsList;
	}
}
