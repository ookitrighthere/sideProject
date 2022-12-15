package com.side.first_side.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.side.first_side.domain.Post;
import com.side.first_side.exception.PostNotFound;
import com.side.first_side.repository.PostRepository;
import com.side.first_side.request.post.PostCreate;
import com.side.first_side.request.post.PostEdit;
import com.side.first_side.request.post.PostSearch;
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

	@Test
	@DisplayName("�� 1������ ��ȸ")
	void test3() {
	//given
		List<Post> requestPost = IntStream.range(0,20)
										  .mapToObj(i -> Post.builder()
													     	  .title("���� " + i)
													          .content("���� " + i)
													          .build())
										  .collect(Collectors.toList());
		postRepository.saveAll(requestPost);

	//when

	PostSearch postSearch = PostSearch.builder()
									  .build();

	List<PostResponse> posts = postService.getList(postSearch);


	//then
	assertEquals(5L, posts.size());
	assertEquals("���� 19", posts.get(0).getTitle());
	assertEquals("���� 16", posts.get(3).getContent());
	}

	@Test
	@DisplayName("�� ���� �����ϱ�")
	void test4() {
		//given
		Post post = Post.builder()
						.title("�����׽�Ʈ")
						.content("�� �����ϱ�")
						.build();

		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
									.title("�����׽�Ʈ ��")
									.content("�� �����ϱ�")
									.build();

		//when
		postService.edit(post.getId(), postEdit);

		Post changePost = postRepository.findById(post.getId())
										.orElseThrow(() -> new RuntimeException("���� �������� �ʽ��ϴ�."));

		assertEquals("�����׽�Ʈ ��", changePost.getTitle());
		assertEquals("�� �����ϱ�", changePost.getContent());
	}

	@Test
	@DisplayName("�� ���� �����ϱ�")
	void test5() {
		//given
		Post post = Post.builder()
				.title("�����׽�Ʈ")
				.content("�� �����ϱ�")
				.build();

		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
				.title("�����׽�Ʈ")
				.content("�� �����ϱ� �Ϸ�")
				.build();

		//when
		postService.edit(post.getId(), postEdit);

		Post changePost = postRepository.findById(post.getId())
				.orElseThrow(() -> new RuntimeException("���� �������� �ʽ��ϴ�."));

		assertEquals("�����׽�Ʈ", changePost.getTitle());
		assertEquals("�� �����ϱ� �Ϸ�", changePost.getContent());
	}
	@Test
	@DisplayName("�� ���� null")
	void test6() {
		//given
		Post post = Post.builder()
				.title("�����׽�Ʈ")
				.content("�� �����ϱ�")
				.build();

		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
				.title(null)
				.content("�� �����ϱ� �Ϸ�")
				.build();

		//when
		postService.edit(post.getId(), postEdit);

		Post changePost = postRepository.findById(post.getId())
				.orElseThrow(() -> new RuntimeException("���� �������� �ʽ��ϴ�."));

		assertEquals("�����׽�Ʈ", changePost.getTitle());
		assertEquals("�� �����ϱ� �Ϸ�", changePost.getContent());
	}

	@Test
	@DisplayName("�������� �ʴ� �� ��ȸ")
	void test7() {
		//given
		//Long postId = 1L;
		Post writePost = Post.builder()
						.title("�����Դϴ�")
						.content("�����Դϴ�")
						.build();
		postRepository.save(writePost);

		//expected
		assertThrows(PostNotFound.class, () -> {
					 	postService.get(writePost.getId() + 1 );
					 });
	}
	@Test
	@DisplayName("�������� �ʴ� �� ����")
	void test8() {
		//given
		//Long postId = 1L;
		Post writePost = Post.builder()
				.title("�����Դϴ�")
				.content("�����Դϴ�")
				.build();
		postRepository.save(writePost);

		PostEdit postEdit = PostEdit.builder()
									.title("�����Դϴ�")
									.content("���� ����")
									.build();

		//expected
		assertThrows(PostNotFound.class, () -> {
						postService.edit(writePost.getId() + 1L, postEdit);
					 });
	}

	@Test
	@DisplayName("�������� �ʴ� �� ����")
	void test9() {
		//given
		Post writePost = Post.builder()
							 .title("�� ����")
							 .content("�� ����")
							 .build();
		postRepository.save(writePost);
		//expected
		assertThrows(PostNotFound.class, () -> {
						postService.delete(writePost.getId() + 2L);
					 });
	}

}
