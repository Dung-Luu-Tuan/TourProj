package com.example.tour_project.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@Entity
@Table(name = "customer")
@RequiredArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "makhachhang", unique = true, nullable = false, length = 45)
    private int makhachhang;

    @Column(name = "hoten", length = 45)
    private String hoten;

    @Column(name = "cmnd", length = 45)
    private String cmnd;

    @Column(name = "diachi", length = 45)
    private String diachi;

    @Column(name = "gioitinh", length = 45)
    private String gioitinh;

    @Column(name = "sdt", length = 45)
    private String sdt;

    @Column(name = "quoctich", length = 45)
    private String quoctich;

    public Customer(int makhachhang, String hoten, String cmnd, String diachi, String gioitinh,String sdt, String quoctich){
        this.makhachhang = makhachhang;
        this.hoten = hoten;
        this.cmnd = cmnd;
        this.diachi = diachi;
        this.gioitinh= gioitinh;
        this.sdt=sdt;
        this.quoctich=quoctich;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //JoinTable bảng trung gian
    //joinColumns: column mapping với table hiện tại
    // inverseJoinColumns: column mapping với table còn lại
    @JoinTable(name = "customer_tour",
            joinColumns = { @JoinColumn(name = "makhachhang", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "madoan", nullable = false, updatable = false) })
    private Set<TouristGroup> groupTourCus;

}
