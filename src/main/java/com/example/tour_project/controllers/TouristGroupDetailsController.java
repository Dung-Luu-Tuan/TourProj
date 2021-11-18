package com.example.tour_project.controllers;

import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.DetailTourGroup;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TouristGroup;
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
import javafx.stage.Stage;

import java.io.IOException;

public class TouristGroupDetailsController {
    @FXML
    Label labelTendoan;

    @FXML
    Label labelMadoan;

    @FXML
    Label labelMatour;

    @FXML
    private TableColumn<DetailTourGroup, String> hanhtrinh;

    @FXML
    private TableColumn<DetailTourGroup, String> khachsan;

    @FXML
    private TableColumn<DetailTourGroup, String> diadiemthamquan;


    @FXML
    private TableView<DetailTourGroup> tableDetailListGroup;

    @FXML
    private ObservableList<DetailTourGroup> tourObservableList;

    public void setView(TouristGroup group) {
        TouristGroup group1 = TouristGroupDAO.getDetails(group.getMadoan());

        labelMadoan.setText(String.valueOf(group.getMadoan()));
        labelMatour.setText(String.valueOf(group.getMatour()));
        labelTendoan.setText(String.valueOf(group1.getTour().getTengoi()));

        hanhtrinh.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHanhtrinh()));
        khachsan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKhachsan()));
        diadiemthamquan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiadiemthamquan()));

        tourObservableList = FXCollections.observableArrayList(group1.getDetailTourGroup());
        tableDetailListGroup.setItems(tourObservableList);
    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/tour_project/tourist-group.fxml"));
        Parent tourGroupParent = loader.load();
        Scene scene = new Scene(tourGroupParent);

        stage.setScene(scene);
    }
}
