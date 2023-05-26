package br.com.flexpag.traineepaymentapi.entity;

import br.com.flexpag.traineepaymentapi.entity.enums.ContractTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity(name = "Client")
@Table(name = "client")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Client extends BaseEntity {

    private String firstName;

    private String lastName;

    private String identity;

    @Enumerated(value = EnumType.STRING)
    private ContractTypeEnum contractType;

    private Long contractNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
