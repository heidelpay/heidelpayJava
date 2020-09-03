package com.heidelpay.payment;

/**
 * Business object for a Payment. A Payment is the object that holds toghether
 * several requests over time. This means that a payment belongs to one offer
 * from the merchant.
 * 
 * Within the Payment you also find the list of Charges, Cancels and the
 * Authorization object.
 * 
 * @author rene.felder
 *
 */
public class MarketplacePayment extends Payment {

	public MarketplacePayment(Heidelpay heidelpay) {
		super(heidelpay);
	}

	@Override
	public String getTypeUrl() {
		return "marketplace/payments";
	}

}
