package com.example.tour_project.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cost")
@RequiredArgsConstructor
public class Cost {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "machiphi", unique = true, nullable = false, length = 45)
    private int machiphi;

    @Column(name = "madoan")
    private int madoan;

    @Column(name = "sotien")
    private float sotien;

    @Column(name = "maloaichiphi")
    private int maloaichiphi;

    @ManyToOne
    @JoinColumn(name = "maloaichiphi", nullable = false, insertable = false, updatable = false)
    private TypeCost typeCost;

    @ManyToOne
    @JoinColumn(name = "madoan", nullable = false, insertable = false, updatable = false)
    private TouristGroup touristGroup;

    public Cost(int machiphi,int madoan, float sotien, int maloaichiphi){
        this.machiphi = machiphi;
        this.madoan = madoan;
        this.sotien = sotien;
        this.maloaichiphi = maloaichiphi;
    }
}
