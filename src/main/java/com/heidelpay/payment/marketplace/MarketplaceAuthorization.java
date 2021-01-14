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

import com.heidelpay.payment.AbstractTransaction;
import com.heidelpay.payment.Heidelpay;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

public class MarketplaceAuthorization extends AbstractTransaction<MarketplacePayment> {
	
	private List<MarketplaceCancel> cancelList;
	
	public MarketplaceAuthorization() {
		super();
	}

	public MarketplaceAuthorization(Heidelpay heidelpay) {
		super(heidelpay);
	}

	@Override
	public String getTypeUrl() {
		return "marketplace/payments/<paymentId>/authorize";
	}

	@Override
	public PaymentType map(PaymentType paymentType, JsonObject jsonObject) {
		return null;
	}
	
	public List<MarketplaceCancel> getCancelList() {
		return cancelList;
	}

	public void setCancelList(List<MarketplaceCancel> cancelList) {
		this.cancelList = cancelList;
	}

	public MarketplaceCancel getCancel(String cancelId) {
		if (cancelList == null) return null;
		for (MarketplaceCancel cancel : cancelList) {
			if (cancelId.equalsIgnoreCase(cancel.getId())) {
				return cancel;
			}
		}
		return null;
	}
	
	public MarketplaceCharge charge(MarketplaceCharge charge) throws HttpCommunicationException {
		return getHeidelpay().marketplaceChargeAuthorization(this.getPaymentId(), this.getId(), charge);
	}
	
	/**
	 * Cancel for this authorization.
	 * 
	 * @param cancel refers to MarketplaceCancel.
	 * @return MarketplaceCancel
	 * @throws HttpCommunicationException
	 */
	public MarketplaceCancel cancel(MarketplaceCancel cancel) throws HttpCommunicationException {
		return getHeidelpay().marketplaceAuthorizationCancel(this.getPaymentId(), this.getId(), cancel);
	}
}
