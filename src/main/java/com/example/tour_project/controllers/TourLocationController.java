package com.example.tour_project.controllers;

import com.example.tour_project.dao.LocationDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.models.Place;
import com.example.tour_project.models.PlaceOrder;
import com.example.tour_project.models.Tour;
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
import javafx.stage.Stage;

import java.io.IOException;

public class TourLocationController {
    @FXML
    private TableView<PlaceOrder> tableLocation;

    @FXML
    private TableColumn<PlaceOrder, String> stt, matour, madiadiem;

    @FXML
    private ObservableList<PlaceOrder> locationList;

    @FXML
    private TableView<Place> tablePlace;

    @FXML
    private TableColumn<Place, String> madiadiemtb, tendiadiemtb;

    @FXML
    private ObservableList<Place> placeList;

    @FXML
    private TextField stttf, madiadiemtf, matourtf;

    public void setView(Tour tour){
        stt.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getThutu())));
        matour.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMatour())));
        madiadiem.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMadiadiem())));

        locationList = FXCollections.observableArrayList(tour.getPlaceOrders());
        tableLocation.setItems(locationList);

        madiadiemtb.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMadiadiem())));
        tendiadiemtb.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTendiadiem()));

        placeList = FXCollections.observableArrayList(LocationDAO.listPlace());
        tablePlace.setItems(placeList);
        matourtf.setText(Integer.toString(tour.getMatour()));
    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/tour_project/tour-details.fxml"));
        Parent tourdetailParent = loader.load();
        Scene scene = new Scene(tourdetailParent);

        stage.setScene(scene);
    }

    public void handleInsertLocation(ActionEvent e1) {
        PlaceOrder placeOrder = new PlaceOrder();
        try {
            placeOrder.setThutu(Integer.parseInt(stttf.getText()));
            placeOrder.setMatour(Integer.parseInt(matourtf.getText()));
            placeOrder.setMadiadiem(Integer.parseInt(madiadiemtf.getText()));
            LocationDAO.insert(placeOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
