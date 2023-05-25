package br.com.flexpag.traineepaymentapi.entity;

import br.com.flexpag.traineepaymentapi.entity.enums.ContractTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity(name = "Client")
@Table(name = "client")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client extends BaseEntity {

    private String name;

    private String identity;

    @Enumerated(value = EnumType.STRING)
    private ContractTypeEnum contractType;

    private String email;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Long contractNumber;

    @OneToMany
    @JoinTable(
            name = "client_purchases",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_id"))
    private Set<Purchase> purchases;

}
