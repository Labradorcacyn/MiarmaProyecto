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

    public void addSeguidoSeguidor(UserEntity Pseguido, UserEntity Pseguidor) {
        seguido = Pseguido;
        seguidor = Pseguidor;
        Pseguido.getSeguido().add(this);
    }
    public void addSeguidorSeguido(UserEntity Pseguidor, UserEntity Pseguido) {
        seguidor = Pseguidor;
        seguido = Pseguido;
        Pseguidor.getFollowers().add(this);
    }

    public void removeSeguidoSeguidor(UserEntity Pseguidor) {
        Pseguidor.getFollowers().remove(seguido);
        seguido = null;
        seguidor = null;
    }
}