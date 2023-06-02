package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity(name = "Invoice")
@Table(name = "invoices")
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
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

}
