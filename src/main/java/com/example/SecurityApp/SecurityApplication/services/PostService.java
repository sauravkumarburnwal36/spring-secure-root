package com.example.SecurityApp.SecurityApplication.services;


import com.example.SecurityApp.SecurityApplication.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    PostDTO createNewPost(PostDTO PostDTO);

    PostDTO updatePostById(PostDTO inputPost, Long postId);
}
