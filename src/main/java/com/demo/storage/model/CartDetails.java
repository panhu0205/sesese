package com.demo.storage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cart_details")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CartDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "cart")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "cart")
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;
}
