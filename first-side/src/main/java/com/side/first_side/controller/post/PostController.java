package com.side.first_side.controller.post;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.side.first_side.request.post.PostCreate;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PostController {


	@PostMapping("/posts")
	public void posts(@RequestBody @Valid PostCreate postCreate) {
		log.info(postCreate.toString());
	}
}
