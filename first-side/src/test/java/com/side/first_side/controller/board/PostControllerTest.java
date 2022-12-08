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
	@DisplayName("/posts ��û �׽�Ʈ")
	void test() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
							           .title("�׽�Ʈ�Դϴ�.")
							           .content("�׽�Ʈ����@@@@@")
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
	@DisplayName("title�� �ʼ�")
	void test2() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
									   .content("�����Դϴ�")
									   .build();
		String json = objectMapper.writeValueAsString(request);

		//expected
		mockMvc.perform(post("/posts")
			            .contentType(APPLICATION_JSON)
			            .content(json))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.validation.title").value("������ �Է��ϼ���."))
				.andDo(print());

	}

	@Test
	@DisplayName("content�� �ʼ�")
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
				.andExpect(jsonPath("$.validation.content").value("������ �Է��ϼ���."))
				.andDo(print());
	}

	@Test
	@DisplayName("/posts �� �����ϱ�")
	void test4() throws Exception {
		//given
		PostCreate request = PostCreate.builder()
								       .title("�׽�Ʈ post")
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
	@DisplayName("post 1�� ��ȸ�ϱ�")
	void test5() throws Exception {
		//given
		Post post = Post.builder()
					    .title("�� 1�� ��ȸ �׽�Ʈ")
					    .content("�� 1�� ��ȸ�ϱ�")
					    .build();
		postRepository.save(post);
		//expected
		mockMvc.perform(get("/posts/{postId}", post.getId())
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("�� 1�� ��ȸ �׽�Ʈ"))
				.andExpect(jsonPath("$.content").value("�� 1�� ��ȸ�ϱ�"))
				.andDo(print());
	}

	@Test
	@DisplayName("�� ������ ��ȸ")
	void test6() throws Exception {
		//given
		Post post1 = Post.builder()
					    .title("ù��°")
					    .content("ù��° ��")
					    .build();
		postRepository.save(post1);


		Post post2 = Post.builder()
				.title("�ι�°")
				.content("�ι�° ��")
				.build();
		postRepository.save(post2);

		//expected
		mockMvc.perform(get("/posts")
						.contentType(APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.length()",is(2)))
			   .andExpect(jsonPath("$[0].title").value("ù��°"))
			   .andExpect(jsonPath("$[0].content").value("ù��° ��"))
			   .andExpect(jsonPath("$[1].title").value("�ι�°"))
			   .andExpect(jsonPath("$[1].content").value("�ι�° ��"));
	}
}
