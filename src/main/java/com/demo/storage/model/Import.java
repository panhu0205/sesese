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
@Table(name = "import")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Import {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "vendor")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vendor vendor;
}
