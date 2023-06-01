package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity(name = "Purchase")
@Table(name = "purchase")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE purchase SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Purchase extends BaseEntity {

    private Long amount;

    private Long invoiceAmount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "purchase")
    private List<Invoice> invoices;

    @OneToOne(mappedBy = "purchase")
    private Transaction transactions;

}
