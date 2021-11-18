package com.example.tour_project.controllers;

import com.example.tour_project.dao.CustomerDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.models.Customer;
import com.example.tour_project.models.Tour;
import com.example.tour_project.utils.HibernateUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;

import java.io.IOException;
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
        makhachhang.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMakhachhang())));
        hoten.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHoten()));
        cmnd.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCmnd()));
        diachi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiachi()));
        gioitinh.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGioitinh()));
        sdt.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSdt()));
        quoctich.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuoctich()));
        loadData();
    }
    private void loadData() {
        customersList = FXCollections.observableArrayList(CustomerDAO.listTour());
        tableCustomersList.getItems().clear();
        tableCustomersList.setItems(customersList);
    }
    public void addCustomer(ActionEvent e) throws IOException {
        //lấy stage hiện tại

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/tour_project/customer-add.fxml"));
            Parent tourDetailsParent = loader.load();
            Scene scene = new Scene(tourDetailsParent);
            stage.setScene(scene);


    }
}
