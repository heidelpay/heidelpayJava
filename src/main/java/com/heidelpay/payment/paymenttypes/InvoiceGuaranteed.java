package com.heidelpay.payment.paymenttypes;

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

import com.heidelpay.payment.Basket;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Currency;

import com.heidelpay.payment.Charge;
import com.heidelpay.payment.Customer;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.json.JsonIdObject;
import com.heidelpay.payment.communication.json.JsonObject;

/**
 * Invoice guaranteed is an Invoice payment with guarantee for the Merchant
 * @author rene.felder
 *
 */
public class InvoiceGuaranteed extends AbstractPaymentType implements PaymentType {

	@Override
	public String getTypeUrl() {
		return "types/invoice-guaranteed";
	}

	@Override
	public PaymentType map(PaymentType invoiceGuaranteed, JsonObject jsonId) {
		((InvoiceGuaranteed) invoiceGuaranteed).setId(jsonId.getId());
		((InvoiceGuaranteed) invoiceGuaranteed).setRecurring(((JsonIdObject) jsonId).getRecurring());
		return invoiceGuaranteed;
	}

	public Charge charge(BigDecimal amount, Currency currency, URL returnUrl) throws HttpCommunicationException {
		return charge(amount, currency, returnUrl, null);
	}
	public Charge charge(BigDecimal amount, Currency currency, URL returnUrl, Customer customer) throws HttpCommunicationException {
		return getHeidelpay().charge(amount, currency, this, returnUrl, customer);
	}

	public Charge charge(BigDecimal amount, Currency currency, URL returnUrl, Customer customer, Basket basket, String invoiceId) throws HttpCommunicationException {
		return getHeidelpay().charge(getCharge(amount, currency, this, returnUrl, customer, basket, invoiceId));
	}

	private Charge getCharge(BigDecimal amount, Currency currency, InvoiceGuaranteed invoiceGuaranteed, URL returnUrl,
			Customer customer, Basket basket, String invoiceId) throws HttpCommunicationException {
		return ((Charge) new Charge()
				.setAmount(amount)
				.setCurrency(currency)
				.setTypeId(getHeidelpay().createPaymentType(invoiceGuaranteed).getId())
				.setReturnUrl(returnUrl)
				.setCustomerId(getHeidelpay().createCustomerIfPresent(customer).getId())
				.setBasketId(getHeidelpay().createBasket(basket).getId()))
				.setInvoiceId(invoiceId);
	}

}
