package com.side.first_side.controller.board;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.first_side.request.post.PostCreate;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;



	@Test
	@DisplayName("/posts 요청 테스트")
	void test() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
							           .title("테스트입니다.")
							           .content("테스트내용@@@@@")
							           .build();
		String json = objectMapper.writeValueAsString(request);

		//expected
		mockMvc.perform(post("/posts")
						.contentType(APPLICATION_JSON)
						.content(json))
				.andExpect(status().isOk())
				.andExpect(content().string(""))
				.andDo(print());
	}

}
