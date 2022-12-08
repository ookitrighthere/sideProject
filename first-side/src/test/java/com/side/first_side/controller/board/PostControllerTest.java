package com.side.first_side.controller.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.side.first_side.domain.Post;
import com.side.first_side.repository.PostRepository;
import com.side.first_side.request.post.PostCreate;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}



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

	@Test
	@DisplayName("title값 필수")
	void test2() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
									   .content("내용입니다")
									   .build();
		String json = objectMapper.writeValueAsString(request);

		//expected
		mockMvc.perform(post("/posts")
			            .contentType(APPLICATION_JSON)
			            .content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.validation.title").value("제목을 입력하세요."))
				.andDo(print());

	}

	@Test
	@DisplayName("content값 필수")
	void test3() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
									   .title("title")
									   .content("")
									   .build();
		String json = objectMapper.writeValueAsString(request);

		//expected
		mockMvc.perform(post("/posts")
				        .contentType(APPLICATION_JSON)
				        .content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.validation.content").value("내용을 입력하세요."))
				.andDo(print());
	}

	@Test
	@DisplayName("/posts 값 저장하기")
	void test4() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
								       .title("테스트 post")
								       .content("test Posts")
								       .build();
		String json = objectMapper.writeValueAsString(request);
		//when
		mockMvc.perform(post("/posts")
				        .contentType(APPLICATION_JSON)
				        .content(json))
			   .andExpect(status().isOk())
			   .andDo(print());

		//then
		assertEquals(1L, postRepository.count());
		Post post = postRepository.findAll().get(0);
		assertEquals(request.getTitle(), post.getTitle());
		assertEquals(request.getContent(), post.getContent());
	}

	@Test
	@DisplayName("post 1개 조회하기")
	void test5() throws Exception {
		//given
		Post post = Post.builder()
					    .title("글 1개 조회 테스트")
					    .content("글 1개 조회하기")
					    .build();
		postRepository.save(post);
		//expected
		mockMvc.perform(get("/posts/{postId}", post.getId())
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("글 1개 조회 테스트"))
				.andExpect(jsonPath("$.content").value("글 1개 조회하기"))
				.andDo(print());
	}

	@Test
	@DisplayName("글 여러개 조회")
	void test6() throws Exception {
		//given
		Post post1 = Post.builder()
					    .title("첫번째")
					    .content("첫번째 글")
					    .build();
		postRepository.save(post1);


		Post post2 = Post.builder()
				.title("두번째")
				.content("두번째 글")
				.build();
		postRepository.save(post2);

		//expected
		mockMvc.perform(get("/posts")
						.contentType(APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.length()",is(2)))
			   .andExpect(jsonPath("$[0].title").value("첫번째"))
			   .andExpect(jsonPath("$[0].content").value("첫번째 글"))
			   .andExpect(jsonPath("$[1].title").value("두번째"))
			   .andExpect(jsonPath("$[1].content").value("두번째 글"));
	}
}
