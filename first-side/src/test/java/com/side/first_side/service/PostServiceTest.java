package com.side.first_side.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

//	@Test
//	@DisplayName("�� ������ ��ȸ")
//	void test3() {
		//given
//		Post writePost1 = Post.builder()
//							 .title("ù���� ��")
//							 .content("ù��° ����")
//							 .build();
//		postRepository.save(writePost1);
//
//		Post writePost2 = Post.builder()
//							  .title("�ι��� ��")
//							  .content("�ι�° ����")
//							  .build();
//		postRepository.save(writePost2);

//		postRepository.saveAll(List.of(
//				 Post.builder()
//				 	 .title("ù���� ��")
//				 	 .content("ù��° ����")
//				 	 .build(),
//				 Post.builder()
//				 	 .title("�ι��� ��")
//				 	 .content("�ι�° ����")
//				 	 .build()
//		));
//		//when
//		List<PostResponse> postList = postService.getList(1);
//
//		//then
//		assertEquals(2L, postList.size());
//		assertEquals("�ι�° ����", postList.get(1).getContent());
//		assertEquals("ù���� ��", postList.get(0).getTitle());
//	}

	@Test
	@DisplayName("�� 1������ ��ȸ")
	void test4() {
	//given
		List<Post> requestPost = IntStream.range(1, 31)
										  .mapToObj(i -> Post.builder()
													     	  .title("���� " + i)
													          .content("���� " + i)
													          .build())
										  .collect(Collectors.toList());
		postRepository.saveAll(requestPost);

	//when
	List<PostResponse> posts = postService.getList(0);

	//then
	assertEquals(2L, posts.size());

	}



}
