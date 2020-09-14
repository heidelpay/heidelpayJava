package com.heidelpay.payment.marketplace;

import java.math.BigDecimal;

import com.heidelpay.payment.AbstractTransaction;
import com.heidelpay.payment.Heidelpay;
import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

public class MarketplaceCancel extends AbstractTransaction<MarketplacePayment> {
	
	private static final String FULL_AUTHORIZE_CANCEL_URL = "marketplace/payments/%1$s/authorize/cancels";
	private static final String FULL_CHARGE_CANCEL_URL = "marketplace/payments/%1$s/charges/cancels";
	private static final String PARTIAL_AUTHORIZE_CANCEL_URL = "marketplace/payments/%1$s/authorize/%2$s/cancels";
	private static final String PARTIAL_CHARGE_CANCEL_URL = "marketplace/payments/%1$s/charges/%2$s/cancels";
	
	private String typeUrl;
	private BigDecimal amountGross;
	private BigDecimal amountNet;
	private BigDecimal amountVat;
	private MarketplaceCancelBasket canceledBasket;
	
	public MarketplaceCancelBasket getCanceledBasket() {
		return canceledBasket;
	}

	public void setCanceledBasket(MarketplaceCancelBasket canceledBasket) {
		this.canceledBasket = canceledBasket;
	}

	public MarketplaceCancel() {
		super();
	}
	
	public MarketplaceCancel(Heidelpay heidelpay) {
		super(heidelpay);
	}
	
	public String getFullAuthorizeCancelUrl(String paymentId) {
		return String.format(FULL_AUTHORIZE_CANCEL_URL, paymentId);
	}
	
	public String getFullChargesCancelUrl(String paymentId) {
		return String.format(FULL_CHARGE_CANCEL_URL, paymentId);
	}
	
	public String getPartialAuthorizeCancelUrl(String paymentId, String authorizeId) {
		return String.format(PARTIAL_AUTHORIZE_CANCEL_URL, paymentId, authorizeId);
	}
	
	public String getPartialChargesCancelUrl(String paymentId, String chargeId) {
		return String.format(PARTIAL_CHARGE_CANCEL_URL, paymentId, chargeId);
	}

	@Override
	public String getTypeUrl() {
		if(this.typeUrl == null || this.typeUrl.isEmpty()) {
			throw new IllegalArgumentException("typeUrl is null or emtpty. Please initialize nested classes to assign value.");
		}
		return this.typeUrl;
	}

	@Override
	public PaymentType map(PaymentType paymentType, JsonObject jsonObject) {
		return null;
	}

	public BigDecimal getAmountGross() {
		return amountGross;
	}

	public void setAmountGross(BigDecimal amountGross) {
		this.amountGross = amountGross;
	}

	public BigDecimal getAmountNet() {
		return amountNet;
	}

	public void setAmountNet(BigDecimal amountNet) {
		this.amountNet = amountNet;
	}

	public BigDecimal getAmountVat() {
		return amountVat;
	}

	public void setAmountVat(BigDecimal amountVat) {
		this.amountVat = amountVat;
	}
}
