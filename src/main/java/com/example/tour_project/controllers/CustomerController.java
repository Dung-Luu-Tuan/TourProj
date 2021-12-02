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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private TextField makhachhangtf, hotentf, cmndtf, diachitf, gioitinhtf, sdttf, quoctichtf;
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
        tableCustomersList.setOnMouseClicked((MouseEvent e) -> {
            Customer selected = tableCustomersList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                makhachhangtf.setText(Integer.toString(selected.getMakhachhang()));
                makhachhangtf.setEditable(false);
                hotentf.setText(selected.getHoten());
                cmndtf.setText(selected.getCmnd());
                diachitf.setText(selected.getDiachi());
                gioitinhtf.setText(selected.getGioitinh());
                sdttf.setText(selected.getSdt());
                quoctichtf.setText(selected.getQuoctich());
            }
        });
        }
    private void loadData() {
        customersList = FXCollections.observableArrayList(CustomerDAO.listCustomer());
        tableCustomersList.getItems().clear();
        tableCustomersList.setItems(customersList);
        makhachhangtf.clear();
        hotentf.clear();
        cmndtf.clear();
        diachitf.clear();
        gioitinhtf.clear();
        sdttf.clear();
        quoctichtf.clear();
    }
    public void handleRefresh() {
        try {
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Không có dữ liệu nào được làm mới")
                    .showWarning();
        }

    }
    public void handleInsertCustomer() {
        Customer customer = new Customer();
        try {
            customer.setHoten(hotentf.getText());
            customer.setCmnd(cmndtf.getText());
            customer.setDiachi(diachitf.getText());
            customer.setGioitinh(gioitinhtf.getText());
            customer.setSdt(sdttf.getText());
            customer.setQuoctich(quoctichtf.getText());
            if ((makhachhangtf.getText()) == "" && hotentf.getText() != "" && cmndtf.getText() != ""
            && diachitf.getText() != "" && gioitinhtf.getText() != "" && sdttf.getText() != "" && quoctichtf.getText() != "") {
                CustomerDAO.insert(customer);
                loadData();
            } else if(makhachhangtf.getText() != ""){
                Notifications.create()
                        .title("Thông báo")
                        .text("Khách hàng đã tồn tại")
                        .showWarning();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Vui lòng nhập dữ liệu cần thêm")
                        .showWarning();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleDeleteCustomer() {
        try {
            Customer customer = new Customer(Integer.parseInt(makhachhangtf.getText()), hotentf.getText(), cmndtf.getText(), diachitf.getText(), gioitinhtf.getText(), sdttf.getText(), quoctichtf.getText());
            CustomerDAO.delete(customer);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }
    public void handleUpdateTour() {
        try {
            Customer customer = new Customer(Integer.parseInt(makhachhangtf.getText()), hotentf.getText(), cmndtf.getText(), diachitf.getText(), gioitinhtf.getText(), sdttf.getText(), quoctichtf.getText());
            CustomerDAO.update(customer);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

}

