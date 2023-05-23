package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "address")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address extends BaseEntity {

    private String street;

    private String number;

    private String city;

    private String state;

    private String complement;

    @OneToOne(mappedBy = "address")
    private Client client;

}
