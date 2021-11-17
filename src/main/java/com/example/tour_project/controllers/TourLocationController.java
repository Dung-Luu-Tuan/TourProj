package com.example.tour_project.controllers;

import com.example.tour_project.dao.LocationDAO;
import com.example.tour_project.dao.PlaceDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.List;

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

    @FXML
    private Button addBtn;

    Tour tourSend;

    public void setView(Tour tour) {
        stt.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getThutu())));
        matour.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMatour())));
        madiadiem.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMadiadiem())));

        locationList = FXCollections.observableArrayList(tour.getPlaceOrders());
        tableLocation.setItems(locationList);

        madiadiemtb.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMadiadiem())));
        tendiadiemtb.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTendiadiem()));

        placeList = FXCollections.observableArrayList(PlaceDAO.listPlace());
        tablePlace.setItems(placeList);
        matourtf.setText(Integer.toString(tour.getMatour()));
        tourSend = tour;

        tableLocation.setOnMouseClicked((MouseEvent e) -> {
            PlaceOrder selected = tableLocation.getSelectionModel().getSelectedItem();
            if (selected != null) {
                stttf.setText(Integer.toString(selected.getThutu()));
                madiadiemtf.setText(Integer.toString(selected.getMadiadiem()));
                matourtf.setText(Integer.toString(selected.getMatour()));
                addBtn.setVisible(false);
            }
        });
    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/tour_project/tour-details.fxml"));
        Parent tourDetailsParent = loader.load();
        Scene scene = new Scene(tourDetailsParent);

        TourDetailsController controller = loader.getController();
        controller.setView(tourSend);

        stage.setScene(scene);
    }

    public void handleInsertLocation() {
        PlaceOrder placeOrder = new PlaceOrder();
        try {
            placeOrder.setThutu(Integer.parseInt(stttf.getText()));
            placeOrder.setMatour(Integer.parseInt(matourtf.getText()));
            placeOrder.setMadiadiem(Integer.parseInt(madiadiemtf.getText()));
            LocationDAO.insert(placeOrder);
            clearTextField();
            loadData(Integer.parseInt(matourtf.getText()));
        } catch (Exception e){
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng nhập dữ liệu")
                    .showWarning();
        }
    }

    public void handleUpdateLocation() {
        try {
            PlaceOrder placeOrder = new PlaceOrder(Integer.parseInt(matourtf.getText()), Integer.parseInt(madiadiemtf.getText()), Integer.parseInt((stttf.getText())));
            PlaceOrder selected = tableLocation.getSelectionModel().getSelectedItem();
            if (placeOrder.getThutu() == selected.getThutu() &&
                    placeOrder.getMadiadiem() == selected.getMadiadiem()) {
                Notifications.create()
                        .title("Title Text")
                        .text("Lộ trình đã tồn tại")
                        .showWarning();
            } else {
                LocationDAO.update(placeOrder);
                LocationDAO.delete(selected);
                clearTextField();
                loadData(Integer.parseInt(matourtf.getText()));
            }
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void loadData(int matour) {
        Tour tour;
        tour = TourDAO.getDetail(matour);
        tableLocation.setItems(FXCollections.observableArrayList(tour.getPlaceOrders()));
    }

    public void handleDeleteLocation() {
        PlaceOrder selected = tableLocation.getSelectionModel().getSelectedItem();
        try {
            LocationDAO.delete(selected);
            loadData(selected.getMatour());
            clearTextField();
        } catch (Exception e) {
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }

    public void handleRefreshLocation() {
        loadData(tourSend.getMatour());
        clearTextField();
        addBtn.setVisible(true);
    }

    public void clearTextField() {
        stttf.clear();
        madiadiemtf.clear();
    }
}
