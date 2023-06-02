package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "addresses")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE address SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address extends BaseEntity {

    private String publicPlace;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

    @OneToOne(mappedBy = "address")
    private Client client;

}
