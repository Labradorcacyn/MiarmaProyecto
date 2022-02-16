package com.miarma.cynthia.models;

import com.miarma.cynthia.users.model.UserEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Follow {

    @Builder.Default
    @EmbeddedId
    private FollowPK id = new FollowPK();

    @ManyToOne
    @MapsId("seguidor_id")
    @JoinColumn(name="seguidor_id")
    private UserEntity seguidor;

    @ManyToOne
    @MapsId("seguido_id")
    @JoinColumn(name="seguido_id")
    private UserEntity seguido;

    private boolean isFollow;

    public void addSeguidorSeguido(UserEntity Pseguidor, UserEntity Pseguido) {
        seguido = Pseguido;
        seguidor = Pseguidor;
        Pseguidor.getFollowers().add(this);
        Pseguido.getFollowers().add(this);
    }

    public void removeSeguidorSeguido(UserEntity Pseguidor, UserEntity Pseguido) {
        Pseguido.getFollowers().remove(seguidor);
        Pseguidor.getFollowers().remove(seguido);
        seguidor = null;
        seguido = null;
    }
}