package com.heidelpay.payment;

/*-
 * #%L
 * Heidelpay Java SDK
 * %%
 * Copyright (C) 2018 - 2019 Heidelpay GmbH
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

import com.heidelpay.payment.communication.json.JsonObject;
import com.heidelpay.payment.paymenttypes.PaymentType;

public class Recurring extends AbstractTransaction<Payment> {
	
	public Recurring() {
		super();
	}

	@Override
	public String getTypeUrl() {
		return "types/<typeId>/recurring";
	}

	@Override
	public PaymentType map(PaymentType paymentType, JsonObject jsonObject) {
		return null;
	}
}
