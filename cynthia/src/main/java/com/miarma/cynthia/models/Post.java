package com.miarma.cynthia.models;

import com.miarma.cynthia.users.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity @Table(name="posts")
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String title;

    private String text;

    private String document;

    private boolean isPrivate;

    @ManyToOne
    private UserEntity user;

    //HELPER
    public void addUSer(UserEntity u){
        user = u;
        u.getPosts().add(this);
    }
}