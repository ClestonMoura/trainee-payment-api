package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity(name = "Invoice")
@Table(name = "invoice")
@Where(clause = "deleted='false'")
@SQLDelete(sql = "UPDATE invoice SET deleted = true WHERE id = ?")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Invoice extends BaseEntity {

    private LocalDate dueDate;

    private String barcode;

    private Double amount;

    private Boolean paid;

    private Long contractNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

}
