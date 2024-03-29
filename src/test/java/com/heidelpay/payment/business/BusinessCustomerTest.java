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

import java.text.ParseException;

import org.junit.Ignore;
import org.junit.Test;

import com.heidelpay.payment.Customer;
import com.heidelpay.payment.CustomerCompanyData;
import com.heidelpay.payment.PaymentException;
import com.heidelpay.payment.communication.HttpCommunicationException;

public class BusinessCustomerTest extends AbstractPaymentTest {

	
	@Test
	public void testCreateRegisteredBusinessCustomer() throws HttpCommunicationException {
		Customer customer = getHeidelpay().createCustomer(getRegisterdMinimumBusinessCustomer());
		assertNotNull(customer);
		assertNotNull(customer.getId());
		assertCustomerEquals(getRegisterdMinimumBusinessCustomer(), customer);
	}

	@Test
	public void testCreateRegisteredMaximumBusinessCustomer() throws HttpCommunicationException, ParseException {
		Customer maxCustomer = getRegisterdMaximumBusinessCustomer(getRandomId());
		Customer customer = getHeidelpay().createCustomer(maxCustomer);
		assertNotNull(customer);
		assertNotNull(customer.getId());
		assertCustomerEquals(maxCustomer, customer);
	}
	
	@Test
	public void testFetchRegisteredMaximumBusinessCustomer() throws HttpCommunicationException, ParseException {
		Customer customer = getRegisterdMaximumBusinessCustomer(getRandomId());
		customer = getHeidelpay().createCustomer(customer);
		assertNotNull(customer);
		assertNotNull(customer.getId());
		
		Customer fetchedCustomer = getHeidelpay().fetchCustomer(customer.getId());
		assertCustomerEquals(customer, fetchedCustomer);
	}

	@Test
	@Ignore("Bug https://heidelpay.atlassian.net/browse/AHC-1644")
	public void testCreateUnRegisteredBusinessCustomer() throws HttpCommunicationException, ParseException {
		Customer customer = getHeidelpay().createCustomer(getUnRegisterdMinimumBusinessCustomer());
		assertNotNull(customer);
		assertNotNull(customer.getId());
		assertCustomerEquals(getRegisterdMinimumBusinessCustomer(), customer);
	}

	@Test
	@Ignore("Bug https://heidelpay.atlassian.net/browse/AHC-1644")
	public void testCreateUnRegisteredMaximumBusinessCustomer() throws HttpCommunicationException, ParseException {
		Customer maxCustomer = getUnRegisterdMaximumBusinessCustomer();
		Customer customer = getHeidelpay().createCustomer(maxCustomer);
		assertNotNull(customer);
		assertNotNull(customer.getId());
		assertCustomerEquals(maxCustomer, customer);
	}
	
	@Test
	@Ignore("Bug https://heidelpay.atlassian.net/browse/AHC-1644")
	public void testFetchUnRegisteredMaximumBusinessCustomer() throws HttpCommunicationException, ParseException {
		Customer customer = getUnRegisterdMaximumBusinessCustomer();
		customer = getHeidelpay().createCustomer(customer);
		assertNotNull(customer);
		assertNotNull(customer.getId());
		
		Customer fetchedCustomer = getHeidelpay().fetchCustomer(customer.getId());
		assertCustomerEquals(customer, fetchedCustomer);
	}

	@Test
	@Ignore ("Bug https://heidelpay.atlassian.net/browse/AHC-1645")
	public void testUpdateCustomer() throws HttpCommunicationException, ParseException {
		Customer customer = getHeidelpay().createCustomer(getRegisterdMaximumBusinessCustomer(getRandomId()));
		assertNotNull(customer);
		assertNotNull(customer.getId());
		Customer customerToUpdate = new Customer("FelderIT GmbH");
		CustomerCompanyData company = getRegisteredCompanyData();
		company.setCommercialRegisterNumber("ABC993448 DÜSSELDORF");
		customerToUpdate.setCompanyData(company);
		Customer updatedCustomer = getHeidelpay().updateCustomer(customer.getId(), customerToUpdate);
		assertEquals("FelderIT GmbH", updatedCustomer.getCompany());
		Customer fetchedCustomer = getHeidelpay().fetchCustomer(customer.getId());
		assertEquals("FelderIT GmbH", fetchedCustomer.getCompany());
		assertEquals("ABC993448 DÜSSELDORF", fetchedCustomer.getCompanyData().getCommercialRegisterNumber());
	}
	
	@Test(expected=PaymentException.class)
	public void testDeleteCustomer() throws HttpCommunicationException, ParseException {
		Customer customer = getHeidelpay().createCustomer(getRegisterdMaximumBusinessCustomer(getRandomId()));
		assertNotNull(customer);
		assertNotNull(customer.getId());
		getHeidelpay().deleteCustomer(customer.getId());
		getHeidelpay().fetchCustomer(customer.getId());
	}

}
