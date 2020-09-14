package com.heidelpay.payment.marketplace;

import java.util.List;

/**
 * This model is part of marketplace authorization/charge cancellation request.
 * A marketplace payment may have more than one authorization(s) or charge(s).
 * <br>
 * Marketplace authorization/charge cancellation is cancellation of single or
 * multiple basket items for one participant of payment basket.
 * 
 * @author ngokienthuan
 *
 */
public class MarketplaceCancelBasket {
	private List<MarketplaceCancelBasketItems> items;

	public List<MarketplaceCancelBasketItems> getItems() {
		return items;
	}

	public void setItems(List<MarketplaceCancelBasketItems> items) {
		this.items = items;
	}
}
