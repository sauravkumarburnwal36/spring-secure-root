package com.example.SecurityApp.SecurityApplication.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long postId;
    private String title;
    private String description;
}
