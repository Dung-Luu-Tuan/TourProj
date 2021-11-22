package com.example.tour_project.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tourist_group")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TouristGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "madoan")
    private int madoan;

    @Column(name = "matour")
    private int matour;

    @Column(name = "ngaykhoihanh")
    private Date ngaykhoihanh;

    @Column(name = "ngayketthuc")
    private Date ngayketthuc;

//    @Column(name = "doanhthu")
//    private float doanhthu;

    public TouristGroup(int madoan, int matour, Date ngaykhoihanh, Date ngayketthuc) {
        this.madoan = madoan;
        this.matour = matour;
        this.ngaykhoihanh = ngaykhoihanh;
        this.ngayketthuc = ngayketthuc;
    }

    @OneToOne(mappedBy = "touristGroup")
    @LazyCollection(LazyCollectionOption.FALSE)
    private DetailTourGroup detailTourGroup;

    @ManyToOne
    @JoinColumn(name = "matour", nullable = false, insertable = false, updatable = false)
    private Tour tour;
//    @OneToMany(mappedBy = "touristGroup")
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<TourPrice> tourPrice;

    @OneToMany(mappedBy = "touristGroup")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CustomerTour> customerTour;


}
