package br.com.flexpag.traineepaymentapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @CreatedDate
    private LocalDate createdOn;

    @LastModifiedDate
    private LocalDate updatedOn;


    private LocalDate deletedOn;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
