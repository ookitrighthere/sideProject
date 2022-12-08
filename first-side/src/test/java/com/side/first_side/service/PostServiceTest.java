package com.side.first_side.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.side.first_side.domain.Post;
import com.side.first_side.repository.PostRepository;
import com.side.first_side.request.post.PostCreate;
import com.side.first_side.response.post.PostResponse;


@SpringBootTest
class PostServiceTest {

	@Autowired
	PostService postService;

	@Autowired
	PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

	@Test
	@DisplayName("�� �����ϱ�")
	//given
	void test1() {
		PostCreate postCreate = PostCreate.builder()
										  .title("�� ���� �׽�Ʈ")
									      .content("�� �����ϱ�")
									      .build();
	//when
		postService.write(postCreate);
	//then
		Post post = postRepository.findAll().get(0);
		assertEquals(postCreate.getTitle(), post.getTitle());
		assertEquals(postCreate.getContent(), post.getContent());
	}

	@Test
	@DisplayName("�� 1�� ��ȸ")
	void test2() {
		//given
		//Long postId = 1L;
		Post writePost = Post.builder()
						.title("�����Դϴ�")
						.content("�����Դϴ�")
						.build();
		postRepository.save(writePost);

		//when
		PostResponse postResponse = postService.get(writePost.getId());

		//then
		assertNotNull(postResponse);
		assertEquals("�����Դϴ�", postResponse.getTitle());
		assertEquals("�����Դϴ�", postResponse.getContent());
	}



}
