package com.demo.storage.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Group{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "kind")
    private int kind;// 1 system, 2 normal group
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "zassi_permission_group", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions = new ArrayList<>();
}
