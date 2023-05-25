package br.com.flexpag.traineepaymentapi.entity;

import br.com.flexpag.traineepaymentapi.entity.enums.TokenTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "token")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE token SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token extends BaseEntity {

    @Column(unique = true)
    private String tokenName;

    @Enumerated(EnumType.STRING)
    private TokenTypeEnum tokenType = TokenTypeEnum.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
