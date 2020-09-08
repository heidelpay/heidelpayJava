package com.heidelpay.payment;

import java.math.BigDecimal;

import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

public class MarketplaceCancel extends AbstractTransaction<MarketplacePayment> {
	
	private String typeUrl;
	private BigDecimal amountGross;
	private BigDecimal amountNet;
	private BigDecimal amountVat;
	
	public MarketplaceCancel() {
		super();
	}
	
	public MarketplaceCancel(Heidelpay heidelpay) {
		super(heidelpay);
	}

	public class FullAuthorizationCancel extends MarketplaceCancel {
		
		public FullAuthorizationCancel() {
			super();
		}
		
		public FullAuthorizationCancel(Heidelpay heidelpay) {
			super(heidelpay);
		}

		@Override
		public String getTypeUrl() {
			MarketplaceCancel.this.typeUrl = "marketplace/payments/<paymentId>/authorize/cancels";
			return MarketplaceCancel.this.typeUrl;
		}
	}

	public class FullChargeCancel extends MarketplaceCancel {
		
		public FullChargeCancel() {
			super();
		}
		
		public FullChargeCancel(Heidelpay heidelpay) {
			super(heidelpay);
		}

		@Override
		public String getTypeUrl() {
			MarketplaceCancel.this.typeUrl = "marketplace/payments/<paymentId>/charges/cancels";
			return MarketplaceCancel.this.typeUrl;
		}
	}

	@Override
	public String getTypeUrl() {
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
