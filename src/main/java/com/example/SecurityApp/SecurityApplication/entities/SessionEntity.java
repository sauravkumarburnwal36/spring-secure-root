package com.example.SecurityApp.SecurityApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="sessions",uniqueConstraints =@UniqueConstraint(columnNames ="user_id"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity{

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private Long userId;

    @Column(nullable = false)
    private String token;

    @Column(name="created_at",nullable = false)
    private LocalDateTime createdAt;

    public SessionEntity(Long userId,String token){
        this.userId=userId;
        this.token=token;
        this.createdAt=LocalDateTime.now();
    }
}