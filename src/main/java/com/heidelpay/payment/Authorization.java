package com.heidelpay.payment;

/*-
 * #%L
 * Heidelpay Java SDK
 * %%
 * Copyright (C) 2018 Heidelpay GmbH
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

import java.math.BigDecimal;
import java.util.List;

import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

/**
 * Business object for Authorization. Amount, currency and typeId are mandatory parameter to 
 * execute an Authorization. 
 * 
 * The returnUrl is mandatory in case of redirectPayments like Sofort, Paypal, Giropay, Creditcard 3DS
 * @author rene.felder
 *
 */
public class Authorization extends AbstractTransaction<Payment> {

	private BigDecimal effectiveInterestRate;
	
	private List<Cancel> cancelList;

	public Authorization() {
		super();
	}

	public Authorization(Heidelpay heidelpay) {
		super(heidelpay);
	}

	public Charge charge() throws HttpCommunicationException {
		return getHeidelpay().chargeAuthorization(getPayment().getId());
	}

	public Charge charge(BigDecimal amount) throws HttpCommunicationException {
		return getHeidelpay().chargeAuthorization(getPayment().getId(), amount);
	}

	public Charge charge(BigDecimal amount, String paymentReference) throws HttpCommunicationException {
		return getHeidelpay().chargeAuthorization(getPayment().getId(), amount, paymentReference);
	}

	public Cancel cancel() throws HttpCommunicationException {
		return getHeidelpay().cancelAuthorization(getPayment().getId());
	}

	public Cancel cancel(BigDecimal amount) throws HttpCommunicationException {
		return getHeidelpay().cancelAuthorization(getPayment().getId(), amount);
	}

	public Cancel cancel(Cancel cancel) throws HttpCommunicationException {
		return getHeidelpay().cancelAuthorization(getPayment().getId(), cancel);
	}

	@Override
	public String getTypeUrl() {
		return "payments/<paymentId>/authorize";
	}

	@Override
	public PaymentType map(PaymentType paymentType, JsonObject jsonObject) {
		return null;
	}

	public BigDecimal getEffectiveInterestRate() {
		return effectiveInterestRate;
	}

	public Authorization setEffectiveInterestRate(BigDecimal effectiveInterestRate) {
		this.effectiveInterestRate = effectiveInterestRate;
		return this;
	}
	
	public List<Cancel> getCancelList() {
		return cancelList;
	}

	public void setCancelList(List<Cancel> cancelList) {
		this.cancelList = cancelList;
	}

	public Cancel getCancel(String cancelId) {
		if (cancelList == null) return null;
		for (Cancel cancel : cancelList) {
			if (cancelId.equalsIgnoreCase(cancel.getId())) {
				return cancel;
			}
		}
		return null;
	}
}
