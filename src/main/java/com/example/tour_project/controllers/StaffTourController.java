package com.example.tour_project.controllers;

import com.example.tour_project.dao.LocationDAO;
import com.example.tour_project.dao.StaffAllocationDAO;
import com.example.tour_project.dao.StaffDAO;
import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class StaffTourController {
    @FXML
    private TableView<Staff> staffTable;

    @FXML
    private TableColumn<Staff, String> msnvtb, tennhanvientb;

    @FXML
    private TableView<StaffAllocation> misstionTable;

    @FXML
    private TableColumn<StaffAllocation, String> msnv, nhiemvu, tennhanvien, madoan;

    @FXML
    private ObservableList<Staff> staffTableList;

    @FXML
    private ObservableList<StaffAllocation> misstionTableList;

    @FXML
    private TextField msnvtf, nhiemvutf;

    @FXML
    private Label label;

    TouristGroup touristGroupSend;
    public void setView(TouristGroup touristGroup) {
        touristGroupSend = touristGroup;
        label.setText(String.valueOf(touristGroup.getMadoan()));

        msnv.setCellValueFactory(data ->new SimpleStringProperty(String.valueOf(data.getValue().getManhanvien())));
        tennhanvien.setCellValueFactory(data ->new SimpleStringProperty(data.getValue().getStaff().getTennhanvien()));
        nhiemvu.setCellValueFactory(data ->new SimpleStringProperty(data.getValue().getNhiemvu()));

        misstionTableList = FXCollections.observableArrayList(touristGroup.getStaffAllocations());
        misstionTable.setItems(misstionTableList);

        msnvtb.setCellValueFactory(data ->new SimpleStringProperty(String.valueOf(data.getValue().getManhanvien())));
        tennhanvientb.setCellValueFactory(data ->new SimpleStringProperty(data.getValue().getTennhanvien()));
        staffTableList = FXCollections.observableArrayList(StaffDAO.listStaff());
        staffTable.setItems(staffTableList);

        misstionTable.setOnMouseClicked((MouseEvent e) -> {
            StaffAllocation selected = misstionTable.getSelectionModel().getSelectedItem();
            if(selected != null){
                msnvtf.setText(String.valueOf(selected.getManhanvien()));
                nhiemvutf.setText(selected.getNhiemvu());
            }
        });
    }

    public void handleInsertLocation() {
        StaffAllocation staffAllocation = new StaffAllocation();
        try {
            staffAllocation.setMadoan(touristGroupSend.getMadoan());
            staffAllocation.setManhanvien(Integer.parseInt(msnvtf.getText()));
            staffAllocation.setNhiemvu(nhiemvutf.getText());
            StaffAllocationDAO.insert(staffAllocation);
            clearTextField();
            loadData();
        } catch (Exception e){
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng nhập dữ liệu")
                    .showWarning();
        }
    }

    public void handleDeleteLocation() {
        StaffAllocation staffAllocation = new StaffAllocation();
        try {
            staffAllocation.setMadoan(touristGroupSend.getMadoan());
            staffAllocation.setManhanvien(Integer.parseInt(msnvtf.getText()));
            staffAllocation.setNhiemvu(nhiemvutf.getText());
            StaffAllocationDAO.delete(staffAllocation);
            loadData();
            clearTextField();
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }

    public void handleUpdateStaffAllocation(){
        try{
            StaffAllocation staffAllocation = new StaffAllocation(Integer.parseInt(msnvtf.getText()), touristGroupSend.getMadoan(), nhiemvutf.getText());
            StaffAllocationDAO.update(staffAllocation);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void clearTextField(){
        msnvtf.clear();
        nhiemvutf.clear();
    }

    public void loadData(){
        TouristGroup touristGroup = TouristGroupDAO.getDetails(touristGroupSend.getMadoan());
        misstionTableList = FXCollections.observableArrayList(touristGroup.getStaffAllocations());
        misstionTable.setItems(misstionTableList);
    }

    public void handleRefreshLocation() {
        loadData();
        clearTextField();
    }
}
