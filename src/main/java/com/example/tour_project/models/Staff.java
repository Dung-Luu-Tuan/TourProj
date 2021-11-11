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
@Table(name = "staff")
@RequiredArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "manhanvien", unique = true, nullable = false, length = 45)
    private String manhanvien;

    @Column(name = "tennhanvien", length = 45)
    private String tennhanvien;
}
