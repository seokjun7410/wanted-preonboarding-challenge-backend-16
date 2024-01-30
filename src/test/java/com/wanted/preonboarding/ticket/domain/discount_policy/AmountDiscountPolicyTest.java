package com.wanted.preonboarding.ticket.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AmountDiscountPolicyTest {

	@Test
	void calculateFee_AMOUNT_DISCOUNT_POLICY() {
		//given
		int price = 20000;
		int discountAmount = 2000;
		AmountDiscountPolicy discountPolicy = new AmountDiscountPolicy(discountAmount);

		//when
		int discountFee = discountPolicy.getDiscountAmount(price);

		//then
		assertThat(discountFee).isEqualTo(discountAmount);
	}



}