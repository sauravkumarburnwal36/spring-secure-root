package com.example.SecurityApp.SecurityApplication.controllers;

import com.example.SecurityApp.SecurityApplication.dto.PostDTO;
import com.example.SecurityApp.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<PostDTO> getAllPosts(){

        return postService.getAllPosts();
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO postDTO){
        return postService.createNewPost(postDTO);
    }

    @GetMapping(path="/{postId}")
    @PreAuthorize("@postSecurity.isOwnerOfThePost(#postId)")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PutMapping(path ="/{postId}")
    public PostDTO updatePostById(@RequestBody PostDTO inputPost,@PathVariable Long postId){
        return postService.updatePostById(inputPost,postId);
    }


}
