package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Set;

@Entity(name = "Purchase")
@Table(name = "purchase")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE purchase SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Purchase extends BaseEntity {

    private Long amount;

    private Long invoiceAmount;

    private Double fee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany
    @JoinTable(
            name = "purchase_invoices",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private Set<Invoice> invoices;

    @OneToMany
    @JoinTable(
            name = "purchase_transactions",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "transaction_id"))
    private Set<Transaction> transactions;

}
