package com.example.tour_project.controllers;

import com.example.tour_project.dao.CostDAO;
import com.example.tour_project.dao.PriceDAO;
import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.dao.TypeCostDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class CostTourGroupController {
    @FXML
    private TableView<Cost> tableCost;

    @FXML
    private TableColumn<Cost, String> machiphi, sotien, tenchiphi;

    @FXML
    private ObservableList<Cost> tableCostList;

    @FXML
    private TableView<TypeCost> typeCostTable;

    @FXML
    private TableColumn<TypeCost, String> maloaichiphi, tenloaichiphi;

    @FXML
    private ObservableList<TypeCost> tableTypeCostList;

    @FXML
    private TextField sotientf, maloaichiphitf;

    TouristGroup touristGroup2;
    public void setView(TouristGroup touristGroup) {
        touristGroup2 = touristGroup;

        machiphi.setCellValueFactory(data ->new SimpleStringProperty(String.valueOf(data.getValue().getMachiphi())));
        sotien.setCellValueFactory(data ->new SimpleStringProperty(PriceDAO.priceWithoutDecimal(data.getValue().getSotien())));
        tenchiphi.setCellValueFactory(data ->new SimpleStringProperty((data.getValue().getTypeCost().getTenchiphi())));

        tableCostList = FXCollections.observableArrayList(touristGroup.getCosts());
        tableCost.setItems(tableCostList);

        maloaichiphi.setCellValueFactory(data ->new SimpleStringProperty(String.valueOf(data.getValue().getMaloaichiphi())));
        tenloaichiphi.setCellValueFactory(data ->new SimpleStringProperty(String.valueOf(data.getValue().getTenchiphi())));

        tableTypeCostList = FXCollections.observableArrayList(TypeCostDAO.listTypeCost());
        typeCostTable.setItems(tableTypeCostList);

        tableCost.setOnMouseClicked((MouseEvent e) -> {
            Cost selected = tableCost.getSelectionModel().getSelectedItem();
            if(selected != null){
                sotientf.setText(String.format("%.0f", selected.getSotien()));
                maloaichiphitf.setText(String.valueOf(selected.getMaloaichiphi()));
            }
        });
    }

    public void handleInsertCost(){
        Cost cost = new Cost();
        try{
            cost.setMadoan(touristGroup2.getMadoan());
            cost.setMaloaichiphi(Integer.parseInt(maloaichiphitf.getText()));
            cost.setSotien(Float.parseFloat(sotientf.getText()));
            CostDAO.insert(cost);
            loadData();
        } catch (Exception e){
            e.printStackTrace();
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng nhập dữ liệu")
                    .showWarning();
        }
    }

    public void handleUpdateCost(){
        Cost selected = tableCost.getSelectionModel().getSelectedItem();
        try {
            Cost cost = new Cost(selected.getMachiphi(),selected.getMadoan(), Float.parseFloat(sotientf.getText()), Integer.parseInt(maloaichiphitf.getText()));
            CostDAO.update(cost);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleDeleteCost(){
        Cost selected = tableCost.getSelectionModel().getSelectedItem();
        try {
            if(selected != null){
                CostDAO.delete(selected);
                loadData();
                clear();
            } else {
                Notifications.create()
                        .title("Title Text")
                        .text("Vui lòng chọn dữ liệu cần xóa")
                        .showWarning();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        sotientf.clear();
        maloaichiphitf.clear();
    }

    public void loadData(){
        tableCostList = FXCollections.observableArrayList(TouristGroupDAO.getDetails(touristGroup2.getMadoan()).getCosts());
        tableCost.setItems(tableCostList);
    }

    public void handleRefresh(){
        loadData();
        clear();
    }
}
