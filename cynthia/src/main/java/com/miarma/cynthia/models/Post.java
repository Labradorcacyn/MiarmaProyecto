package com.miarma.cynthia.models;

import com.miarma.cynthia.users.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity @Table(name="posts")
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    private String document;

    private String documentResized;

    private boolean privacy;

    @ManyToOne
    private UserEntity user;

    //HELPER
    public void addUSer(UserEntity u){
        user = u;
        u.getPosts().add(this);
    }
}