package com.workplace.support.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "opportunity")
@Data
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opId;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000 , nullable = false)
    private String description;

    @Column(nullable = false)
    private String skills ;

    private LocalDateTime applyBefore;

    @ManyToOne()
    @JoinColumn(name = "createdBy", nullable = false)
    private Employee createdBy;
}
