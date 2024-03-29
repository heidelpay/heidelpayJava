package com.heidelpay.payment.marketplace;

/*-
 * #%L
 * Heidelpay Java SDK
 * %%
 * Copyright (C) 2018 - 2021 Heidelpay GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.List;

/**
 * This DTO is part of marketplace cancel request, to request cancel for list of basket item(s) of one marketplace participant.
 * <br>
 * The basket item(s) list can contains basket item(s) of single or multiple marketplace participant(s) to be cancelled.
 * <br>
 * <b>Note: <code>basketItemReferenceId</code> must be unique among basket items.</b>
 * @author ngokienthuan
 *
 */
public class MarketplaceCancelBasket {
	private List<MarketplaceCancelBasketItem> items;

	public List<MarketplaceCancelBasketItem> getItems() {
		return items;
	}

	public void setItems(List<MarketplaceCancelBasketItem> items) {
		this.items = items;
	}

}
