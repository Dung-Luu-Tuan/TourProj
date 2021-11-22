package com.example.tour_project.controllers;

import com.example.tour_project.dao.StaffDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.models.Staff;
import com.example.tour_project.models.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;

import javafx.scene.input.MouseEvent;

import javafx.scene.input.MouseEvent;
import com.example.tour_project.utils.HibernateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffListsController implements Initializable {
    @FXML
    private TableView<Staff> tableListStaffs;

    @FXML
    private TableColumn<Staff, String> manhanvien, tennhanvien, sodienthoai, diachi;

    @FXML
    private TextField tennhanvientf, sodienthoaitf, diachitf, manhanvientf;

    @FXML
    private ObservableList<Staff> staffList;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        manhanvien.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getManhanvien())));
        tennhanvien.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTennhanvien()));
        sodienthoai.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSodienthoai()));
        diachi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiachi()));
        loadData();
        tableListStaffs.setOnMouseClicked((MouseEvent e) -> {
            Staff selected = tableListStaffs.getSelectionModel().getSelectedItem();
            if (selected != null) {
                manhanvientf.setText(Integer.toString(selected.getManhanvien()));
                manhanvientf.setEditable(false);
                tennhanvientf.setText(selected.getTennhanvien());
                sodienthoaitf.setText(selected.getSodienthoai());
                diachitf.setText(selected.getDiachi());
            }
        });
    }


    private void loadData() {
        staffList = FXCollections.observableArrayList(StaffDAO.listStaff());
        tableListStaffs.getItems().clear();
        tableListStaffs.setItems(staffList);
        manhanvientf.clear();
        tennhanvientf.clear();
        sodienthoaitf.clear();
        diachitf.clear();
    }

    public void handleRefresh() {
        try{
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Không có dữ liệu nào được làm mới")
                    .showWarning();
        }
    }

    public void handleUpdateStaff() {
        try{
            Staff staff = new Staff(Integer.parseInt(manhanvientf.getText()), tennhanvientf.getText(), sodienthoaitf.getText(), diachitf.getText());
            StaffDAO.update(staff);
            loadData();
        } catch (Exception e){
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleInsertStaff(){
        Staff staff = new Staff();
        try{
            staff.setTennhanvien(tennhanvientf.getText());
            staff.setSodienthoai(sodienthoaitf.getText());
            staff.setDiachi(diachitf.getText());
            if ((manhanvientf.getText()) == "") {
                StaffDAO.insert(staff);
                loadData();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Nhân viên đã tồn tại")
                        .showWarning();
            }
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng nhập dữ liệu cần thêm")
                    .showWarning();
        }
    }

    public void handleDeleteStaff() {
        try {
            Staff staff = new Staff(Integer.parseInt(manhanvientf.getText()), tennhanvientf.getText(), sodienthoaitf.getText(), diachitf.getText());
            StaffDAO.delete(staff);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }
}