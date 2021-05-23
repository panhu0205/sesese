package com.demo.storage.model;

import java.util.Date;

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
@Table(name = "bill")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "exported_date")
    private Date exportedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "customer_id")
    private Account customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "staff_id")
    private Account staff;

    @Column(name = "total")
    private Integer total;
}
