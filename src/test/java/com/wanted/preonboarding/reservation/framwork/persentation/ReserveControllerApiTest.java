package com.wanted.preonboarding.reservation.framwork.persentation;

import com.wanted.preonboarding.ApiTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.application.PerformAdminService;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@DisplayName("API: ReserveController")
public class ReserveControllerApiTest extends ApiTest {

	@Autowired
	private PerformAdminService showingAdminService;

	@Test
	public void 공연_예약_API(){

		//given
		PerformanceRequest performanceRequest = new PerformanceRequestFactory().create();
		UUID showingId = showingAdminService.register(performanceRequest);
		ReservationRequestFactory requestFactory = new ReservationRequestFactory();
		ReservationRequest request = requestFactory.create(showingId);

		//when
		ExtractableResponse<Response> result = RestAssured.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when()
			.post("/reserve")
			.then()
			.log().all().extract();

		//then
		ReservedItemResponse data = result.response().jsonPath()
			.getObject("data", ReservedItemResponse.class);

		AssertCluster.ReservationAssert(data,request,performanceRequest);

	}
}