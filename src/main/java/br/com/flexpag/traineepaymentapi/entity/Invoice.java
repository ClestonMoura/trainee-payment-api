package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Invoice extends BaseEntity {

    private LocalDate dueDate;

    private String barcode;

    private Long amount;

    private Boolean paid = false;

    private Long contractNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Purchase purchase;

}
