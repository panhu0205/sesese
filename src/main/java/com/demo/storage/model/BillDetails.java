package com.demo.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bill_details")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BillDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "product")
    private Product product;

    @Column(name = "bill")
    private Bill bill;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;
}
