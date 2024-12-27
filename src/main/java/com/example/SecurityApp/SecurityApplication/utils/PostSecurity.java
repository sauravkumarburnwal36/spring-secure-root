package com.example.SecurityApp.SecurityApplication.utils;

import com.example.SecurityApp.SecurityApplication.dto.PostDTO;
import com.example.SecurityApp.SecurityApplication.entities.User;
import com.example.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;

    public boolean isOwnerOfThePost(Long postId){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO postDTO=postService.getPostById(postId);
        return postDTO.getAuthor().getId().equals(user.getId());
    }
}
