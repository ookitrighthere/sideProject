package com.side.first_side.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.side.first_side.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
