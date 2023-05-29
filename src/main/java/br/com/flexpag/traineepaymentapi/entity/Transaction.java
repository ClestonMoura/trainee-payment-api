package br.com.flexpag.traineepaymentapi.entity;

import br.com.flexpag.traineepaymentapi.entity.enums.PaymentTypeEnum;
import br.com.flexpag.traineepaymentapi.entity.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity(name = "Transaction")
@Table(name = "transaction")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE invoice SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private PaymentTypeEnum paymentType;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    private Long authorizationCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

}
