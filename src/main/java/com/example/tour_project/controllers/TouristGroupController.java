package com.example.tour_project.controllers;

import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TouristGroup;
import com.example.tour_project.utils.HibernateUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class TouristGroupController implements Initializable {
    @FXML
    private TableView<TouristGroup> tableListGroup;

    @FXML
    private TableColumn<TouristGroup, String> madoan;

    @FXML
    private TableColumn<TouristGroup, String> matour;

    @FXML
    private TableColumn<TouristGroup, Date> ngaykhoihanh;

    @FXML
    private TableColumn<TouristGroup, Date> ngayketthuc;

    @FXML
    private TableColumn<TouristGroup, Float> doanhthu;

    @FXML
    private ObservableList<TouristGroup> touristGroup;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        madoan.setCellValueFactory(new PropertyValueFactory<TouristGroup, String>("madoan"));
        matour.setCellValueFactory(new PropertyValueFactory<TouristGroup, String>("matour"));
        ngaykhoihanh.setCellValueFactory(new PropertyValueFactory<TouristGroup, Date>("ngaykhoihanh"));
        ngayketthuc.setCellValueFactory(new PropertyValueFactory<TouristGroup, Date>("ngayketthuc"));
        doanhthu.setCellValueFactory(new PropertyValueFactory<TouristGroup, Float>("doanhthu"));
        loadData();
    }

    private void loadData() {
        touristGroup = FXCollections.observableArrayList(TouristGroupDAO.listTourGroup()); //listen change in DB
        tableListGroup.getItems().clear();
        tableListGroup.setItems(touristGroup);
    }
}
