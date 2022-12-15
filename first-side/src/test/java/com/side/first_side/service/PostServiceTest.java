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
	@DisplayName("글 저장하기")
	//given
	void test1() {
		PostCreate postCreate = PostCreate.builder()
										  .title("글 저장 테스트")
									      .content("글 저장하기")
									      .build();
	//when
		postService.write(postCreate);
	//then
		Post post = postRepository.findAll().get(0);
		assertEquals(postCreate.getTitle(), post.getTitle());
		assertEquals(postCreate.getContent(), post.getContent());
	}

	@Test
	@DisplayName("글 1개 조회")
	void test2() {
		//given
		//Long postId = 1L;
		Post writePost = Post.builder()
						.title("제목입니다")
						.content("내용입니다")
						.build();
		postRepository.save(writePost);

		//when
		PostResponse postResponse = postService.get(writePost.getId());

		//then
		assertNotNull(postResponse);
		assertEquals("제목입니다", postResponse.getTitle());
		assertEquals("내용입니다", postResponse.getContent());
	}

	@Test
	@DisplayName("글 1페이지 조회")
	void test3() {
	//given
		List<Post> requestPost = IntStream.range(0,20)
										  .mapToObj(i -> Post.builder()
													     	  .title("제목 " + i)
													          .content("내용 " + i)
													          .build())
										  .collect(Collectors.toList());
		postRepository.saveAll(requestPost);

	//when

	PostSearch postSearch = PostSearch.builder()
									  .build();

	List<PostResponse> posts = postService.getList(postSearch);


	//then
	assertEquals(5L, posts.size());
	assertEquals("제목 19", posts.get(0).getTitle());
	assertEquals("내용 16", posts.get(3).getContent());
	}

	@Test
	@DisplayName("글 제목만 수정하기")
	void test4() {
		//given
		Post post = Post.builder()
						.title("수정테스트")
						.content("글 수정하기")
						.build();

		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
									.title("수정테스트 중")
									.content("글 수정하기")
									.build();

		//when
		postService.edit(post.getId(), postEdit);

		Post changePost = postRepository.findById(post.getId())
										.orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다."));

		assertEquals("수정테스트 중", changePost.getTitle());
		assertEquals("글 수정하기", changePost.getContent());
	}

	@Test
	@DisplayName("글 내용 수정하기")
	void test5() {
		//given
		Post post = Post.builder()
				.title("수정테스트")
				.content("글 수정하기")
				.build();

		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
				.title("수정테스트")
				.content("글 수정하기 완료")
				.build();

		//when
		postService.edit(post.getId(), postEdit);

		Post changePost = postRepository.findById(post.getId())
				.orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다."));

		assertEquals("수정테스트", changePost.getTitle());
		assertEquals("글 수정하기 완료", changePost.getContent());
	}
	@Test
	@DisplayName("글 제목 null")
	void test6() {
		//given
		Post post = Post.builder()
				.title("수정테스트")
				.content("글 수정하기")
				.build();

		postRepository.save(post);

		PostEdit postEdit = PostEdit.builder()
				.title(null)
				.content("글 수정하기 완료")
				.build();

		//when
		postService.edit(post.getId(), postEdit);

		Post changePost = postRepository.findById(post.getId())
				.orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다."));

		assertEquals("수정테스트", changePost.getTitle());
		assertEquals("글 수정하기 완료", changePost.getContent());
	}

	@Test
	@DisplayName("존재하지 않는 글 조회")
	void test7() {
		//given
		//Long postId = 1L;
		Post writePost = Post.builder()
						.title("제목입니다")
						.content("내용입니다")
						.build();
		postRepository.save(writePost);

		//expected
		assertThrows(PostNotFound.class, () -> {
					 	postService.get(writePost.getId() + 1 );
					 });
	}
	@Test
	@DisplayName("존재하지 않는 글 수정")
	void test8() {
		//given
		//Long postId = 1L;
		Post writePost = Post.builder()
				.title("제목입니다")
				.content("내용입니다")
				.build();
		postRepository.save(writePost);

		PostEdit postEdit = PostEdit.builder()
									.title("제목입니다")
									.content("내용 수정")
									.build();

		//expected
		assertThrows(PostNotFound.class, () -> {
						postService.edit(writePost.getId() + 1L, postEdit);
					 });
	}

	@Test
	@DisplayName("존재하지 않는 글 삭제")
	void test9() {
		//given
		Post writePost = Post.builder()
							 .title("글 제목")
							 .content("글 내용")
							 .build();
		postRepository.save(writePost);
		//expected
		assertThrows(PostNotFound.class, () -> {
						postService.delete(writePost.getId() + 2L);
					 });
	}

}
