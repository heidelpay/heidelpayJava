package com.heidelpay.payment.business.paymenttypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;

import org.junit.Test;

import com.heidelpay.payment.Authorization;
import com.heidelpay.payment.Charge;
import com.heidelpay.payment.business.AbstractPaymentTest;
import com.heidelpay.payment.communication.HttpCommunicationException;
import com.heidelpay.payment.paymenttypes.Applepay;
import com.heidelpay.payment.paymenttypes.ApplepayHeader;

public class ApplepayTest extends AbstractPaymentTest {

	
	@Test
	public void testCreateApplepayType() throws HttpCommunicationException {
		Applepay applepay = getApplePay();
		
		Applepay response = getHeidelpay().createPaymentType(applepay);
		assertNotNull(response.getId());
		assertNotNull(response.getExpiryDate());
		assertNotNull(response.getNumber());
		assertEquals("520424******1982", response.getNumber());
		assertEquals("07/2019", response.getExpiryDate());
		
	}

	@Test
	public void testAuthorizeApplePayType() throws HttpCommunicationException, MalformedURLException {
		Applepay applepay = getHeidelpay().createPaymentType(getApplePay());
		Authorization authorization = applepay.authorize(BigDecimal.ONE, Currency.getInstance("EUR"), new URL("https://www.meinShop.de"));
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
		assertEquals(Authorization.Status.SUCCESS, authorization.getStatus());
	}

	@Test
	public void testAuthorizeApplePayTypeId() throws HttpCommunicationException, MalformedURLException {
		Applepay applepay = getHeidelpay().createPaymentType(getApplePay());
		Authorization authorization = getHeidelpay().authorize(BigDecimal.ONE, Currency.getInstance("EUR"), applepay.getId(), new URL("https://www.meinShop.de"));
		assertNotNull(authorization);
		assertNotNull(authorization.getId());
		assertEquals(Authorization.Status.SUCCESS, authorization.getStatus());
	}

	@Test
	public void testChargeApplePayType() throws HttpCommunicationException, MalformedURLException {
		Applepay applepay = getHeidelpay().createPaymentType(getApplePay());
		Charge charge = applepay.charge(BigDecimal.ONE, Currency.getInstance("EUR"), new URL("https://www.meinShop.de"));
		assertNotNull(charge);
		assertNotNull(charge.getId());
		assertEquals(Authorization.Status.SUCCESS, charge.getStatus());
	}

	@Test
	public void testChargeApplePayTypeId() throws HttpCommunicationException, MalformedURLException {
		Applepay applepay = getHeidelpay().createPaymentType(getApplePay());
		Charge charge = getHeidelpay().charge(BigDecimal.ONE, Currency.getInstance("EUR"), applepay.getId(), new URL("https://www.meinShop.de"));
		assertNotNull(charge);
		assertNotNull(charge.getId());
		assertEquals(Authorization.Status.SUCCESS, charge.getStatus());
	}


	private Applepay getApplePay() {
		Applepay applepay = new Applepay();
		applepay.setVersion("EC_v1");
		applepay.setData("CDNa1nRTdo4G7efZwAnmWFXHe8AddpxKPQtSVUl/7RBweAeJkqFD49rr4IxeeWfgNsbTEabKaUEkGxut9Rr8vJxNJ0OVDuZRQLueJFFFwTAxBIwqRCxGWqOEdP7WfPGoYibOG43r2kj0MjMDtkD7tVt+wZwLQeaLSprXJzvHVphtuZz/NH0Bl7U2TWy4wB3qvSbUSqqqPsF84sOwCKTvLYbN+yEKOT5dLcSKOiY9v3XasaqjEXLSn5FjHW49nFrg4W2M57LD7LlhHd15ihPBxoTBZBaA37N/23APUdPyv25qPy1QojUehYHGJAmEV0bKIf4kY/uBcGNMbPtmYTveq5MJVrEXcQFll1EOR3daQEi+jAH84ZYBvdpANF6KXas6E/Tf36+hXKDfA2p1");
		applepay.setSignature("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");
		ApplepayHeader header = new ApplepayHeader();
		header.setEphemeralPublicKey("MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEM/wYg/LmVp2e+TqXsUAIFlzK02Rwm9PAWiu6d3z+iQf8oLvqKUzf1OpjsJeGZfYriTIFe4H9EP6QWxMjoyIs5w==");
		header.setPublicKeyHash("M2yzlpBsH3GwH5jTV9GgKC7bAUdeIOIfjwQhoKjg5+s=");
		header.setTransactionId("d518ad5c087011e44149b4e74c6a7021ab24cf3d01887efde7694f6a04bda238");
		applepay.setHeader(header);
		return applepay;
	}
	
}