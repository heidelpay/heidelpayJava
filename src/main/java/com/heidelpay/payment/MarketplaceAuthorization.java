package com.heidelpay.payment;

/**
 * Business object for Authorization. Amount, currency and typeId are mandatory parameter to 
 * execute an Authorization. 
 * 
 * The returnUrl is mandatory in case of redirectPayments like Sofort, Paypal, Giropay, Creditcard 3DS
 * @author rene.felder
 *
 */
public class MarketplaceAuthorization extends Authorization {
	
	@Override
	public String getTypeUrl() {
		return "marketplace/payments/<paymentId>/authorize";
	}
}
