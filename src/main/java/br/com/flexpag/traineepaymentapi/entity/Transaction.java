package br.com.flexpag.traineepaymentapi.entity;

import br.com.flexpag.traineepaymentapi.entity.enums.PaymentTypeEnum;
import br.com.flexpag.traineepaymentapi.entity.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Transaction extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private PaymentTypeEnum paymentType;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    private Long authorizationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Purchase purchase;

}
