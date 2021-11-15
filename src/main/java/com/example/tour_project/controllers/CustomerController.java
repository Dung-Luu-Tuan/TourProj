package com.example.tour_project.controllers;

import com.example.tour_project.models.Customer;
import com.example.tour_project.utils.HibernateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private TableView<Customer> tableCustomersList;
    @FXML
    private TableColumn<Customer, String> makhachhang, hoten, cmnd, diachi, gioitinh, sdt, quoctich;
    @FXML
    private ObservableList<Customer> customersList;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMakhachhang()));
    }
}
