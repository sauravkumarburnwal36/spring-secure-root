package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.dto.PostDTO;
import com.example.SecurityApp.SecurityApplication.entities.PostEntity;
import com.example.SecurityApp.SecurityApplication.entities.User;
import com.example.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.example.SecurityApp.SecurityApplication.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(
                postEntity -> modelMapper.map(
                        postEntity, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity=postRepository.findById(postId).orElseThrow(()->new
                ResourceNotFoundException("Post with id: "+postId+" not found"));
        return modelMapper.map(postEntity,PostDTO.class);
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity),PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(PostDTO inputPost, Long postId) {
        PostEntity olderPost=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException(
                "Post with id: "+postId+" not found"));
        inputPost.setPostId(postId);
        modelMapper.map(inputPost,olderPost);
        return modelMapper.map(postRepository.save(olderPost),PostDTO.class);
    }
}
