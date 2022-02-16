package com.miarma.cynthia.models;

import com.miarma.cynthia.users.model.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="posts")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    private String document;

    private String documentResized;

    private boolean privacy;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    private UserEntity user;

    //HELPER
    public void addUSer(UserEntity u){
        user = u;
        u.getPosts().add(this);
    }
}