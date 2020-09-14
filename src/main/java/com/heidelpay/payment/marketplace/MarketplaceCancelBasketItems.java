package com.heidelpay.payment.marketplace;

import java.math.BigDecimal;

/**
 * This model is part of marketplace authorization/charge cancellation request.
 * A marketplace payment may have more than one authorization(s) or charge(s).
 * <br>
 * This model is list of cancel basket item(s) of one participant. A marketplace
 * participant may have more than one basket item(s) in marketplace basket.
 * 
 * @author ngokienthuan
 *
 */
public class MarketplaceCancelBasketItems {
	private String participantId;
	private String basketItemReferenceId;
	private int quantity;
	private BigDecimal amountGross;

	public String getParticipantId() {
		return participantId;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public String getBasketItemReferenceId() {
		return basketItemReferenceId;
	}

	public void setBasketItemReferenceId(String basketItemReferenceId) {
		this.basketItemReferenceId = basketItemReferenceId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmountGross() {
		return amountGross;
	}

	public void setAmountGross(BigDecimal amountGross) {
		this.amountGross = amountGross;
	}
}
