package com.wanted.preonboarding.performance.framwork.presentation;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.wanted.preonboarding.performance.application.PerformService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(QueryController.class)
@ActiveProfiles(profiles = "test")
@DisplayName("유효성: 공연 및 전시 - 목록조회")
public class QueryControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private PerformService performService;

	@Test
	public void 공연_및_전시_정보_목록_조회_잘못된_파라미터_예외발생() throws Exception {
		//given
		String wrongParam = "trueeee";

		// when //then
		mockMvc.perform(
				get("/query/all/performance")
					.queryParam("isReserve", wrongParam)
					.contentType(MediaType.APPLICATION_JSON_VALUE)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("message").value("잘못된 parameter 입니다. 문서를 참고하여 올바른 데이터를 첨부해주세요."))
			.andReturn();
	}

}