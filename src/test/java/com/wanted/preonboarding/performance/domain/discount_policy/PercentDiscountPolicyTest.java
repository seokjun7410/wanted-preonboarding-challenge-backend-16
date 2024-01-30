package com.wanted.preonboarding.performance.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.performance.domain.discount_policy.PercentDiscountPolicy;
import org.junit.jupiter.api.Test;

class PercentDiscountPolicyTest {

	@Test
	void calculateFee_PERCENT_DISCOUNT_POLICY() {
		//given
		int price = 20000;
		int discountPercent = 10;
		PercentDiscountPolicy discountPolicy = new PercentDiscountPolicy(discountPercent);

		//when
		int discountFee = discountPolicy.getDiscountAmount(price);

		//then
		assertThat(discountFee).isEqualTo(price * discountPercent / 100);
	}

}