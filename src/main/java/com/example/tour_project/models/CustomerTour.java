package com.example.tour_project.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "customer_tour")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CustomerTour implements Serializable {
    @Id
    @Column(name = "madoan")
    private int madoan;

    @Id
    @Column(name = "makhachhang")
    private int makhachhang;

    @ManyToOne
    @JoinColumn(name = "madoan", nullable = false, insertable = false, updatable = false)
    private TouristGroup touristGroup;
}
