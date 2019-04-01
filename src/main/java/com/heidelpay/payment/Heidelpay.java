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
import java.net.URL;
import java.util.Currency;

import com.heidelpay.payment.communication.HeidelpayRestCommunication;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.impl.HttpClientBasedRestCommunication;
import com.heidelpay.payment.paymenttypes.PaymentType;
import com.heidelpay.payment.service.PaymentService;

/**
 * {@code Heidelpay} is a facade to the Heidelpay REST Api. The facade is
 * initialized with your privateKey. For having no forced dependencies you can
 * inject an appropriate implementation of {@code HeidelpayRestCommunication}
 * implementing the http-communication Layer. If you are fine with apache's
 * httpClient you can choose the {@code HttpClientBasedRestCommunication}.
 * 
 * @see HeidelpayRestCommunication for details of the http-layer abstraction.
 */
public class Heidelpay {
	private String privateKey;
	private PaymentService paymentService;

	public Heidelpay(String privateKey) {
		this(new HttpClientBasedRestCommunication(), privateKey);
	}
	
	/**
	 * Creates an instance of the {@code Heidelpay}-facade.
	 * @param restCommunication - an appropriate implementation of {@code HeidelpayRestCommunication}. If you are fine with apache's httpCLient you might choose {@code HttpClientBasedRestCommunication}.
	 * @param privateKey - your private key as generated within the heidelpay Intelligence Platform (hIP)
	 */
	public Heidelpay(HeidelpayRestCommunication restCommunication, String privateKey) {
		super();
		this.privateKey = privateKey;
		this.paymentService = new PaymentService(this, restCommunication);
	}

	/**
	 * Create a customer if it is not null. In case the customer is null it will
	 * return null
	 * 
	 * @param customer used customer for creation
	 * @return Customer with id
	 * @throws HttpCommunicationException
	 */
	public Customer createCustomerIfPresent(Customer customer) throws HttpCommunicationException {
		if (customer == null)
			return null;
		return createCustomer(customer);
	}

	/**
	 * Create a customer. This is only possible for new customers. If the customer
	 * is already on the system (has an id) this method will throw an Exception.
	 * 
	 * @param customer
	 * @return Customer with Id
	 * @throws HttpCommunicationException
	 */
	public Customer createCustomer(Customer customer) throws HttpCommunicationException {
		if (customer == null)
			throw new IllegalArgumentException("Customer must not be null");
		if (customer.getId() != null)
			throw new PaymentException(
					"Customer has an id set. createCustomer can only be called without Customer.id. Please use updateCustomer or remove the id from Customer.");
		return paymentService.createCustomer(customer);
	}

	/**
	 * Update a customers data. All parameters that are null are ignored. Only the
	 * non null values are updated.
	 * 
	 * @param id
	 * @param customer
	 * @return Customer
	 * @throws HttpCommunicationException
	 */
	public Customer updateCustomer(String id, Customer customer) throws HttpCommunicationException {
		return paymentService.updateCustomer(id, customer);
	}

	/**
	 * Delete a customer at Heidelpay
	 * 
	 * @param customerId
	 * @throws HttpCommunicationException
	 */
	public void deleteCustomer(String customerId) throws HttpCommunicationException {
		paymentService.deleteCustomer(customerId);
	}

	
	public Metadata createMetadata(Metadata metadata) throws PaymentException, HttpCommunicationException {
		return paymentService.createMetadata(metadata);
	}
	
	public Metadata fetchMetadata(String id) throws PaymentException, HttpCommunicationException {
		return paymentService.fetchMetadata(id);
	}

	public Basket createBasket(Basket basket) throws PaymentException, HttpCommunicationException {
		return paymentService.createBasket(basket);
	}

	public Basket fetchBasket(String id) throws PaymentException, HttpCommunicationException {
		return paymentService.fetchBasket(id);
	}

	public Basket updateBasket(Basket basket, String id) throws PaymentException, HttpCommunicationException {
		return paymentService.updateBasket(id, basket);
	}




	/**
	 * Create a new PaymentType at Heidelpay. This can be any Object which
	 * implements the Interface PaymentType
	 * 
	 * @param paymentType
	 * @return PaymentType Object with an id
	 * @throws HttpCommunicationException
	 */
	public <T extends PaymentType> T createPaymentType(T paymentType) throws HttpCommunicationException {
		if (paymentType != null && paymentType.getId() == null) {
			return paymentService.createPaymentType(paymentType);
		} else if (paymentType != null && paymentType.getId() != null) {
			return paymentType;
		} else {
			return null;
		}
	}

	/**
	 * Authorize call with customerId and typeId. This is used if the type is
	 * created using the Javascript SDK or the Mobile SDK
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param customerId
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId, String customerId)
			throws HttpCommunicationException {
		return authorize(amount, currency, typeId, null, customerId);
	}

	/**
	 * Authorize call. This is used if the type is created using the Javascript SDK
	 * or the Mobile SDK
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId)
			throws HttpCommunicationException {
		return authorize(amount, currency, typeId, null, (String) null);
	}

	/**
	 * Authorize call for redirect payments with a returnUrl. This is used if the
	 * type is created using the Javascript SDK or the Mobile SDK
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId, URL returnUrl)
			throws HttpCommunicationException {
		return authorize(amount, currency, typeId, returnUrl, (String) null);
	}

	/**
	 * Authorize call for redirect payments with a returnUrl. This is used if the
	 * type is created using the Javascript SDK or the Mobile SDK
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param card3ds
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId, URL returnUrl, Boolean card3ds)
			throws HttpCommunicationException {
		return authorize(amount, currency, typeId, returnUrl, null, card3ds);
	}
	/**
	 * Authorize call for redirect payments with returnUrl and a customer. This is
	 * used if the type is created using the Javascript SDK or the Mobile SDK. The
	 * customer can already exist. If the customer does not exist it will be created
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param customer
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId, URL returnUrl,
			Customer customer) throws HttpCommunicationException {
		return authorize(amount, currency, typeId, returnUrl, getCustomerId(createCustomerIfPresent(customer)));
	}

	/**
	 * Authorize call. Creates a new paymentType
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, PaymentType paymentType)
			throws HttpCommunicationException {
		return authorize(amount, currency, paymentType, null);
	}

	/**
	 * Authorize call for redirect payments with returnUrl. Creates a new
	 * paymentType
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl)
			throws HttpCommunicationException {
		return authorize(amount, currency, paymentType, returnUrl, null, null);
	}

	/**
	 * Authorize call for redirect payments with returnUrl. Creates a new
	 * paymentType
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl, Boolean card3ds)
			throws HttpCommunicationException {
		return authorize(amount, currency, paymentType, returnUrl, null, card3ds);
	}

	/**
	 * Authorize call for redirect payments with returnUrl and a customer. Creates a
	 * new paymentType The customer can already exist. If the customer does not
	 * exist it will be created
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @param customer
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl,
			Customer customer) throws HttpCommunicationException {
		return authorize(amount, currency, createPaymentType(paymentType).getId(), returnUrl,
				getCustomerId(createCustomerIfPresent(customer)));
	}

	/**
	 * Authorize call for redirect payments with returnUrl and a customer. Creates a
	 * new paymentType The customer can already exist. If the customer does not
	 * exist it will be created
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @param customer
	 * @param card3ds
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl,
			Customer customer, Boolean card3ds) throws HttpCommunicationException {
		return authorize(amount, currency, createPaymentType(paymentType).getId(), returnUrl,
				getCustomerId(createCustomerIfPresent(customer)), card3ds);
	}

	/**
	 * Authorize call for redirect payments with returnUrl and a customerId. The
	 * customer must exist.
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param customerId
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId, URL returnUrl,
			String customerId) throws HttpCommunicationException {
		return authorize(getAuthorization(amount, currency, typeId, returnUrl, customerId, null));
	}

	/**
	 * Authorize call for redirect payments with returnUrl and a customerId. The
	 * customer must exist.
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param customerId
	 * @param card3ds
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(BigDecimal amount, Currency currency, String typeId, URL returnUrl,
			String customerId, Boolean card3ds) throws HttpCommunicationException {
		return authorize(getAuthorization(amount, currency, typeId, returnUrl, customerId, card3ds));
	}

	/**
	 * Authorize call with an Authorization object. The Authorization object must
	 * have at least an amount, a currency, a typeId
	 * 
	 * @param authorization
	 * @return Authorization with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Authorization authorize(Authorization authorization) throws HttpCommunicationException {
		return paymentService.authorize(authorization);
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId) throws HttpCommunicationException {
		return charge(amount, currency, typeId, (URL) null);
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * and a customerId
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param customerId
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId, String customerId)
			throws HttpCommunicationException {
		return charge(amount, currency, typeId, null, customerId);
	}

	/**
	 * Charge call. The PaymentType will be created within this method
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, PaymentType paymentType)
			throws HttpCommunicationException {
		return charge(amount, currency, createPaymentType(paymentType).getId(), null, (String) null);
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * for redirect payments
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId, URL returnUrl)
			throws HttpCommunicationException {
		return charge(amount, currency, typeId, returnUrl, (String) null);
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * for redirect payments
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId, URL returnUrl, Boolean card3ds)
			throws HttpCommunicationException {
		return charge(amount, currency, typeId, returnUrl, null, card3ds);
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * for redirect payments
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param customerId
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId, URL returnUrl, String customerId)
			throws HttpCommunicationException {
		return charge(amount, currency, typeId, returnUrl, customerId, null);
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * for redirect payments
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param customerId
	 * @param basketId
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId, URL returnUrl, String customerId, String basketId, Boolean card3ds)
			throws HttpCommunicationException {
		return charge(getCharge(amount, currency, typeId, returnUrl, customerId, basketId, card3ds));
	}

	/**
	 * Charge call with a typeId that was created using the Javascript or Mobile SDK
	 * for redirect payments
	 * 
	 * @param amount
	 * @param currency
	 * @param typeId
	 * @param returnUrl
	 * @param customerId
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, String typeId, URL returnUrl, String customerId, Boolean card3ds)
			throws HttpCommunicationException {
		return charge(getCharge(amount, currency, typeId, returnUrl, customerId, null, card3ds));
	}

	/**
	 * Charge call for redirect payments. The PaymentType will be created within
	 * this method
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl)
			throws HttpCommunicationException {
		return charge(amount, currency, createPaymentType(paymentType).getId(), returnUrl, (String) null);
	}

	/**
	 * Charge call for redirect payments. The PaymentType will be created within
	 * this method
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl, Boolean card3ds)
			throws HttpCommunicationException {
		return charge(amount, currency, createPaymentType(paymentType).getId(), returnUrl, card3ds);
	}
	
	/**
	 * Charge call for redirect payments. The PaymentType will be created within
	 * this method
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @param customer
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl,
			Customer customer) throws HttpCommunicationException {
		return charge(amount, currency, createPaymentType(paymentType).getId(), returnUrl,
				getCustomerId(createCustomerIfPresent(customer)));
	}

	/**
	 * Charge call for redirect payments. The PaymentType will be created within
	 * this method
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @param customer
	 * @param basket
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl,
			Customer customer, Basket basket) throws HttpCommunicationException {
		return charge(amount, currency, createPaymentType(paymentType).getId(), returnUrl,
				getCustomerId(createCustomerIfPresent(customer)), getBasketId(createBasket(basket)), null);
	}
	
	private String getBasketId(Basket basket) {
		if (basket != null) return basket.getId();
		else return null;
	}

	/**
	 * Charge call for redirect payments. The PaymentType will be created within
	 * this method
	 * 
	 * @param amount
	 * @param currency
	 * @param paymentType
	 * @param returnUrl
	 * @param customer
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(BigDecimal amount, Currency currency, PaymentType paymentType, URL returnUrl,
			Customer customer, Boolean card3ds) throws HttpCommunicationException {
		return charge(amount, currency, createPaymentType(paymentType).getId(), returnUrl,
				getCustomerId(createCustomerIfPresent(customer)), card3ds);
	}

	/**
	 * Charge call with a Charge object. At least amount, currency and paymentTypeId
	 * are mandatory
	 * 
	 * @param charge
	 * @return Charge with paymentId and authorize id
	 * @throws HttpCommunicationException
	 */
	public Charge charge(Charge charge) throws HttpCommunicationException {
		return paymentService.charge(charge);
	}

	/**
	 * Charge the full amount that was authorized
	 * 
	 * @param paymentId
	 * @return Charge with id
	 * @throws HttpCommunicationException
	 */
	public Charge chargeAuthorization(String paymentId) throws HttpCommunicationException {
		return paymentService.chargeAuthorization(paymentId);
	}

	/**
	 * Charge partial amount of Authorization. As there is only one Authorization
	 * for a Payment id you only need to provide a paymentId
	 * 
	 * @param paymentId
	 * @param amount
	 * @return Charge with id
	 * @throws HttpCommunicationException
	 */
	public Charge chargeAuthorization(String paymentId, BigDecimal amount) throws HttpCommunicationException {
		return paymentService.chargeAuthorization(paymentId, amount);
	}

	/**
	 * Cancel the full Authorization. As there is only one Authorization for a
	 * Payment id you only need to provide a paymentId
	 * 
	 * @param paymentId
	 * @return Cancel with id
	 * @throws HttpCommunicationException
	 */
	public Cancel cancelAuthorization(String paymentId) throws HttpCommunicationException {
		return paymentService.cancelAuthorization(paymentId);
	}

	/**
	 * Cancel partial amount of Authorization. As there is only one Authorization
	 * for a Payment id you only need to provide a paymentId
	 * 
	 * @param paymentId
	 * @param amount
	 * @return Cancel with id
	 * @throws HttpCommunicationException
	 */
	public Cancel cancelAuthorization(String paymentId, BigDecimal amount) throws HttpCommunicationException {
		return paymentService.cancelAuthorization(paymentId, amount);
	}

	/**
	 * Cancel (Refund) full Charge
	 * 
	 * @param paymentId
	 * @param chargeId
	 * @return Cancel with id
	 * @throws HttpCommunicationException
	 */
	public Cancel cancelCharge(String paymentId, String chargeId) throws HttpCommunicationException {
		return paymentService.cancelCharge(paymentId, chargeId);
	}

	/**
	 * Cancel partial Charge
	 * 
	 * @param paymentId
	 * @param chargeId
	 * @param amount
	 * @return Cancel with id
	 * @throws HttpCommunicationException
	 */
	public Cancel cancelCharge(String paymentId, String chargeId, BigDecimal amount) throws HttpCommunicationException {
		return paymentService.cancelCharge(paymentId, chargeId, amount);
	}

	/**
	 * Inform about a shipment of goods. From this time the insurance start.
	 * 
	 * @param paymentId
	 * @return Shipment with id
	 * @throws HttpCommunicationException
	 */
	public Shipment shipment(String paymentId) throws HttpCommunicationException {
		return paymentService.shipment(paymentId, null);
	}

	/**
	 * Inform about a shipment of goods and provide invoiceId. From this time the insurance start.
	 * 
	 * @param paymentId
	 * @return Shipment with id
	 * @throws HttpCommunicationException
	 */
	public Shipment shipment(String paymentId, String invoiceId) throws HttpCommunicationException {
		return paymentService.shipment(paymentId, invoiceId);
	}

	/**
	 * Load the whole Payment Object for the given paymentId
	 * 
	 * @param paymentId
	 * @return Payment object
	 * @throws HttpCommunicationException
	 */
	public Payment fetchPayment(String paymentId) throws HttpCommunicationException {
		return paymentService.fetchPayment(paymentId);
	}

	/**
	 * Load the Authorization for the given paymentId. As there is only one
	 * Authorization for a Payment the Authorization id is not needed.
	 * 
	 * @param paymentId
	 * @return Authorization object
	 * @throws HttpCommunicationException
	 */
	public Authorization fetchAuthorization(String paymentId) throws HttpCommunicationException {
		return paymentService.fetchAuthorization(paymentId);
	}

	/**
	 * Load the Charge for the given paymentId and charge Id.
	 * 
	 * @param paymentId
	 * @param chargeId
	 * @return Charge
	 * @throws HttpCommunicationException
	 */
	public Charge fetchCharge(String paymentId, String chargeId) throws HttpCommunicationException {
		return paymentService.fetchCharge(paymentId, chargeId);
	}

	/**
	 * Load the Cancel for the given paymentId and cancelId
	 * 
	 * @param paymentId
	 * @param cancelId
	 * @return Cancel
	 * @throws HttpCommunicationException
	 */
	public Cancel fetchCancel(String paymentId, String cancelId) throws HttpCommunicationException {
		return paymentService.fetchCancel(paymentId, cancelId);
	}

	/**
	 * Load the Cancel for the given paymentId, chargeId and cancelId
	 * 
	 * @param paymentId
	 * @param chargeId
	 * @param cancelId
	 * @return Cancel
	 * @throws HttpCommunicationException
	 */
	public Cancel fetchCancel(String paymentId, String chargeId, String cancelId) throws HttpCommunicationException {
		return paymentService.fetchCancel(paymentId, chargeId, cancelId);
	}

	/**
	 * Load the Customer data for the given customerId
	 * 
	 * @param customerId
	 * @return
	 * @throws HttpCommunicationException
	 */
	public Customer fetchCustomer(String customerId) throws HttpCommunicationException {
		return paymentService.fetchCustomer(customerId);
	}

	/**
	 * Load the PaymentType
	 * 
	 * @param typeId
	 * @return
	 * @throws HttpCommunicationException
	 */
	public PaymentType fetchPaymentType(String typeId) throws HttpCommunicationException {
		return paymentService.fetchPaymentType(typeId);
	}

	public String getPrivateKey() {
		return privateKey;
	}

	private Charge getCharge(BigDecimal amount, Currency currency, String typeId, URL returnUrl, String customerId, String basketId, Boolean card3ds) {
		Charge charge = new Charge();
		charge
		.setAmount(amount)
		.setCurrency(currency)
		.setTypeId(typeId)
		.setCustomerId(customerId)
		.setBasketId(basketId)
		.setReturnUrl(returnUrl)
		.setCard3ds(card3ds);
		return charge;
	}

	private String getCustomerId(Customer customer) {
		if (customer == null)
			return null;
		return customer.getId();
	}

	private Authorization getAuthorization(BigDecimal amount, Currency currency, String paymentTypeId, URL returnUrl,
			String customerId, Boolean card3ds) {
		Authorization authorization = new Authorization(this);
		authorization
		.setAmount(amount)
		.setCurrency(currency)
		.setReturnUrl(returnUrl)
		.setTypeId(paymentTypeId)
		.setCustomerId(customerId)
		.setCard3ds(card3ds);
		return authorization;
	}


}
