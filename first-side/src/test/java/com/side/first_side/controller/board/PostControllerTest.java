package com.side.first_side.controller.board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import com.side.first_side.request.post.PostEdit;

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
	@DisplayName("글 여러개 조회 5개씩")
	void test6() throws Exception {
		//given
		List<Post> requestPost = IntStream.range(1, 20)
										  .mapToObj(i -> Post.builder()
													     	  .title("제목 " + i)
													          .content("내용 " + i)
													          .build())
										  .collect(Collectors.toList());
		postRepository.saveAll(requestPost);
		//expected
		mockMvc.perform(get("/posts?page=1&size=5")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()",is(5)))
				.andExpect(jsonPath("$[0].title").value("제목 19"))
				.andExpect(jsonPath("$[4].title").value("제목 15"))
				.andExpect(jsonPath("$[4].content").value("내용 15"))
				.andDo(print());

	}
	@Test
	@DisplayName("글 여러개 조회 10개씩")
	void test7() throws Exception {
		//given
		List<Post> requestPost = IntStream.range(1, 21)
										  .mapToObj(i -> Post.builder()
															 .title("제목 " + i)
															 .content("내용 " + i)
															 .build())
										   .collect(Collectors.toList());
		postRepository.saveAll(requestPost);
		//expected
		mockMvc.perform(get("/posts?page=2&size=10")
						.contentType(APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.length()",is(10)))
			   .andExpect(jsonPath("$[0].title").value("제목 10"))
			   .andExpect(jsonPath("$[4].title").value("제목 6"))
			   .andExpect(jsonPath("$[4].content").value("내용 6"))
			   .andDo(print());

	}

	@Test
	@DisplayName("제목만 수정하기")
	void test8() throws Exception {
		//given\
		Post post = Post.builder()
				.title("글 수정 테스트")
				.content("글수정 내용")
				.build();
		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
				.title("글 수정하기")
				.content("글수정 내용")
				.build();
		String json = objectMapper.writeValueAsString(postEdit);
		//expected
		mockMvc.perform(patch("/posts/{postId}",post.getId())
						.contentType(APPLICATION_JSON)
						.content(json))
			    .andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("게시글 삭제")
	void test9() throws Exception {
		//given
		Post post = Post.builder()
					   .title("게시글1")
					   .content("게시글 삭제하기")
					   .build();
		postRepository.save(post);
		//expected
		mockMvc.perform(delete("/posts/{postId}", post.getId())
						.contentType(APPLICATION_JSON))
			   .andExpect(status().isOk())
			   .andDo(print());
	}

	@Test
	@DisplayName("존재하지 않는 글 조회")
	void test10() throws Exception {

		//expected
		mockMvc.perform(delete("/posts/{postId}", 2L)
				.contentType(APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(print());
	}

	@Test
	@DisplayName("존재하지 않는 글 수정")
	void test11() throws Exception {
		//given
		Post post = Post.builder()
				.title("글 수정 테스트")
				.content("글수정 내용")
				.build();
		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
				.title("글 수정하기")
				.content("글수정 내용")
				.build();
		String json = objectMapper.writeValueAsString(postEdit);

		//expected
		mockMvc.perform(patch("/posts/{postId}",post.getId()+2L)
						.contentType(APPLICATION_JSON)
						.content(json))
			   .andExpect(status().isNotFound())
			   .andDo(print());
	}

	@Test
	@DisplayName("존재하지 않는 글 삭제")
	void test12() throws Exception {
		 //expected
		mockMvc.perform(delete("/posts/{postId}",2L)
				  .contentType(APPLICATION_JSON))
			   .andExpect(status().isNotFound())
			   .andDo(print());
	}

	@Test
	@DisplayName("금지어 포함된 게시글 작성")
	void test13() throws Exception {
		PostCreate postCreate = PostCreate.builder()
										  .title("바보입니까")
										  .content("내용")
										  .build();

		String json = objectMapper.writeValueAsString(postCreate);
		//expected
		mockMvc.perform(post("/posts")
						.contentType(APPLICATION_JSON)
						.content(json))
			   .andExpect(status().isBadRequest())
			   .andDo(print());
	}

}
