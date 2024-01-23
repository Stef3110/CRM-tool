package com.example.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date date_set;

    @Column(length = 200)
    private String details;

    @ManyToOne
    @JoinColumn(name = "personnels_id", nullable = true)
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customer customer;
}
