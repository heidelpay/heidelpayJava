package com.heidelpay.payment.business.paymenttypes;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;

import org.junit.Test;

import com.heidelpay.payment.Authorization;
import com.heidelpay.payment.Charge;
import com.heidelpay.payment.Heidelpay;
import com.heidelpay.payment.Payment;
import com.heidelpay.payment.PaymentException;
import com.heidelpay.payment.business.AbstractPaymentTest;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.communication.impl.HttpClientBasedRestCommunication;
import com.heidelpay.payment.paymenttypes.Card;

public class CardTest extends AbstractPaymentTest {

	
	@Test(expected=PaymentException.class)
	public void testCreateCardWithMerchantNotPCIDSSCompliant() throws HttpCommunicationException {
		Card card = new Card("4444333322221111", "03/20");
		card.setCvc("123");
		Heidelpay heidelpay = new Heidelpay(new HttpClientBasedRestCommunication(), "s-priv-2a107CYZMp3UbyVPAuqWoxQHi9nFyeiW");
		card = heidelpay.createPaymentType(card);
		assertNotNull(card.getId());
	}

	@Test
	public void testCreateCardType() throws HttpCommunicationException {
		Card card = new Card("4444333322221111", "03/20");
		card.setCvc("123");
		card = getHeidelpay().createPaymentType(card);
		assertNotNull(card.getId());
	}

	@Test
	public void testCreateCardTypeWith3DSFlag() throws HttpCommunicationException {
		Card card = new Card("4444333322221111", "03/20");
		card.setCvc("123");
		card.set3ds(false);
		card = getHeidelpay().createPaymentType(card);
		assertNotNull(card.getId());
		assertFalse(card.get3ds());
		card = (Card)getHeidelpay().fetchPaymentType(card.getId());
		assertNotNull(card.getId());
		assertFalse(card.get3ds());
	}


	@Test
	public void testAuthorizeCardType() throws HttpCommunicationException, MalformedURLException {
		Card card = new Card("4444333322221111", "03/20");
		card.setCvc("123");
		card = getHeidelpay().createPaymentType(card);
		Authorization authorization = card.authorize(BigDecimal.ONE, Currency.getInstance("EUR"), new URL("https://www.meinShop.de"));
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
	}

	@Test
	public void testAuthorizeAndPaymentCardType() throws HttpCommunicationException, MalformedURLException {
		Card card = new Card("4444333322221111", "03/20").setCvc("123");
		card.setCvc("123");
		card = getHeidelpay().createPaymentType(card);
		Authorization authorization = card.authorize(BigDecimal.ONE, Currency.getInstance("EUR"), new URL("https://www.meinShop.de"));
		Payment payment = authorization.getPayment();
		assertNotNull(authorization);
		assertNotNull(payment);
		assertNotNull(authorization.getId());
	}

	@Test
	public void testChargeCardType() throws HttpCommunicationException, MalformedURLException {
		Card card = new Card("4444333322221111", "03/20");
		card.setCvc("123");
		card = getHeidelpay().createPaymentType(card);
		Charge charge = card.charge(BigDecimal.ONE, Currency.getInstance("EUR"), new URL("https://www.google.at"));
		assertNotNull(charge);
	}
	
	@Test
	public void testFetchCardType() throws HttpCommunicationException {
		Card card = new Card("4444333322221111", "03/2020");
		card.setCvc("123");
		
		Card createdCard = getHeidelpay().createPaymentType(card);
		assertNotNull(createdCard.getId());
		assertNotNull(createdCard.getId());
		assertEquals(maskString(card.getNumber(), 6, card.getNumber().length()-4, '*'), createdCard.getNumber());
		assertEquals(card.getExpiryDate(), createdCard.getExpiryDate());
		assertNotNull(createdCard.getCvc());

		Card fetchedCard = (Card)getHeidelpay().fetchPaymentType(createdCard.getId());
		assertNotNull(fetchedCard.getId());
		assertEquals(maskString(card.getNumber(), 6, card.getNumber().length()-4, '*'), fetchedCard.getNumber());
		assertEquals(card.getExpiryDate(), fetchedCard.getExpiryDate());
		assertNotNull(fetchedCard.getCvc());
	}

}
