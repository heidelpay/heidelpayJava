package com.heidelpay.payment.business;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Currency;

import org.junit.Test;

import com.heidelpay.payment.AbstractInitPayment;
import com.heidelpay.payment.Authorization;
import com.heidelpay.payment.Basket;
import com.heidelpay.payment.Customer;
import com.heidelpay.payment.MarketplaceAuthorization;
import com.heidelpay.payment.Payment;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.paymenttypes.Card;

public class AuthorizationTest extends AbstractPaymentTest {

	@Test
	public void testAuthorizeWithAuthorizationObject() throws MalformedURLException, HttpCommunicationException {
		Authorization authorize = getHeidelpay().authorize(getAuthorization(createPaymentTypeCard().getId(), false));
		assertNotNull(authorize.getId());
		assertNotNull(authorize);
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}

	@Test
	public void testAuthorizeWithTypeId() throws MalformedURLException, HttpCommunicationException {
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), createPaymentTypeCard().getId(), new URL("https://www.google.at"), false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}
	
	@Test
	public void testAuthorizeSuccess() throws MalformedURLException, HttpCommunicationException {
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), createPaymentTypeCard().getId(), new URL("https://www.google.at"), false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
		assertEquals(com.heidelpay.payment.AbstractInitPayment.Status.SUCCESS, authorize.getStatus());
	}
	
	@Test
	public void testAuthorizeWithPaymentType() throws MalformedURLException, HttpCommunicationException {
		LocalDate locaDateNow = LocalDate.now();
		Card card = new Card("4444333322221111", "12/" + (locaDateNow.getYear() + 1));
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), card, new URL("https://www.google.at"), false);
		assertNotNull(authorize);
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
		assertNotNull(authorize.getId());
	}

	@Test
	public void testAuthorizeReturnPaymentTypeAndCustomer() throws MalformedURLException, HttpCommunicationException {
		LocalDate locaDateNow = LocalDate.now();
		Card card = new Card("4444333322221111", "12/" + (locaDateNow.getYear() + 1));
		Customer customer = new Customer("Rene", "Felder");
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), card, new URL("https://www.google.at"), customer, false);
		Payment payment = authorize.getPayment();
		assertNotNull(payment.getPaymentType());
		assertNotNull(payment.getCustomer());
		assertNotNull(authorize);
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}
	
	@Test
	public void testAuthorizeWithCustomerId() throws MalformedURLException, HttpCommunicationException, ParseException {
		Customer customer = getHeidelpay().createCustomer(getMaximumCustomer(getRandomId()));
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), createPaymentTypeCard().getId(), new URL("https://www.google.at"), customer.getCustomerId(), false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}

	@Test
	public void testAuthorizeWithReturnUrl() throws MalformedURLException, HttpCommunicationException {
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), createPaymentTypeCard().getId(), new URL("https://www.google.at"), false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}

	@Test
	public void testAuthorizeWithCustomerTypeReturnUrl() throws MalformedURLException, HttpCommunicationException {
		LocalDate locaDateNow = LocalDate.now();
		Card card = new Card("4444333322221111", "12/" + (locaDateNow.getYear() + 1));
		Customer customer = new Customer("Rene", "Felder");
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), card, new URL("https://www.google.at"), customer, false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}

	@Test
	public void testAuthorizeWithCustomerIdReturnUrl() throws MalformedURLException, HttpCommunicationException, ParseException {
		Customer maxCustomer = getMaximumCustomer(getRandomId());
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), createPaymentTypeCard().getId(), new URL("https://www.google.at"), maxCustomer.getId(), false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}
	
	@Test
	public void testFetchAuthorization() throws MalformedURLException, HttpCommunicationException {
		Authorization authorize = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), createPaymentTypeCard().getId(), new URL("https://www.google.at"), false);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		Authorization authorization = getHeidelpay().fetchAuthorization(authorize.getPaymentId());
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
	}
	
	@Test
	public void testAuthorizeWithOrderId() throws MalformedURLException, HttpCommunicationException {
		String orderId = getRandomId();
		Authorization authorize = getHeidelpay().authorize(getAuthorization(createPaymentTypeCard().getId(), null, orderId, null, null, false));
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertNotNull(authorize.getMessage().getCustomer());
		Authorization authorization = getHeidelpay().fetchAuthorization(orderId);
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
		assertEquals(orderId, authorization.getOrderId());
		
	}

	@Test
	public void testAuthorizeCard3dsFalse() throws MalformedURLException, HttpCommunicationException {
		String orderId = getRandomId();
		Authorization authorize = getHeidelpay().authorize(getAuthorization(createPaymentTypeCard().getId(), null, orderId, null, null, false));
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.100.112", authorize.getMessage().getCode());
		assertEquals(Authorization.Status.SUCCESS, authorize.getStatus());
//		assertTrue(authorize.getCard3ds());
		Authorization authorization = getHeidelpay().fetchAuthorization(orderId);
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
		assertEquals(orderId, authorization.getOrderId());
//		assertTrue(authorization.getCard3ds());
	}

	@Test
	public void testAuthorizeCard3dsTrue() throws MalformedURLException, HttpCommunicationException {
		String orderId = getRandomId();
		Authorization authorize = getHeidelpay().authorize(getAuthorization(createPaymentTypeCard().getId(), null, orderId, null, null, true));
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("COR.000.200.000", authorize.getMessage().getCode());
		assertEquals(Authorization.Status.PENDING, authorize.getStatus());
//		assertTrue(authorize.getCard3ds());
		Authorization authorization = getHeidelpay().fetchAuthorization(orderId);
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
		assertEquals(orderId, authorization.getOrderId());
//		assertTrue(authorization.getCard3ds());
	}

	@Test
	public void testAuthorizeWithPaymentReference() throws MalformedURLException, HttpCommunicationException {
		Authorization authorization = getAuthorization(createPaymentTypeCard().getId());
		authorization.setPaymentReference("pmt-ref");
		Authorization authorize = getHeidelpay().authorize(authorization);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("pmt-ref", authorize.getPaymentReference());
	}

	@Test
	public void testAuthorizeWithAuthorizeObject() throws MalformedURLException, HttpCommunicationException {
		Authorization authorization = getAuthorization(createPaymentTypeCard().getId());
		authorization.setPaymentReference("pmt-ref");
		authorization.setAmount(new BigDecimal(1.0));
		Authorization authorize = getHeidelpay().authorize(authorization);
		assertNotNull(authorize);
		assertNotNull(authorize.getId());
		assertEquals("pmt-ref", authorize.getPaymentReference());
		assertEquals(new BigDecimal(1.0000).setScale(4), authorize.getAmount());
	}

	@Test
	public void testMarketplaceAuthorize() throws MalformedURLException, HttpCommunicationException {
		String participantId_1 = "31HA07BC814FC247577B195E59A99FC6";
		String participantId_2 = "31HA07BC814FC247577B309FF031D3F0";
		
		//create basket
		Basket maxBasket = getMaxTestBasket();
		maxBasket.setAmountTotalDiscount(null);
		
		maxBasket.getBasketItems().get(0).setParticipantId(participantId_1);
		maxBasket.getBasketItems().get(1).setParticipantId(participantId_2);
		
		int basketItemCnt = maxBasket.getBasketItems().size();
		for(int i=0; i<basketItemCnt; i++) {
			maxBasket.getBasketItems().get(i).setAmountDiscount(null);
		}

		Basket basket = getHeidelpay(privatekey4).createBasket(maxBasket);	
		
		//create card
		Card card = getPaymentTypeCard("4012888888881881");
		card = (Card)getHeidelpay(privatekey4).createPaymentType(card);
		
		//marketplace authorization
		MarketplaceAuthorization authorizeRequest = getMarketplaceAuthorization(card.getId(), null, null, null, basket.getId(), null);
		authorizeRequest.setAmount(maxBasket.getAmountTotalGross());
		
		Authorization authorize = getHeidelpay(privatekey4).authorize(authorizeRequest);
		assertNotNull(authorize.getId());
		assertNotNull(authorize);
		assertEquals(AbstractInitPayment.Status.PENDING, authorize.getStatus());
		assertEquals(participantId_2, authorize.getProcessing().getParticipantId());
		
		//get marketplace payment
		Payment payment = getHeidelpay(privatekey4).fetchMarketplacePayment(authorize.getPayment().getId());
		assertNotNull(payment);
		assertNotNull(payment.getId());
		assertNotNull(payment.getAuthorizationsList());
		assertEquals(1, payment.getAuthorizationsList().size());
		assertEquals(AbstractInitPayment.Status.PENDING, authorize.getStatus());
	}
}
