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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.util.List;

public class TourLocationController {
    @FXML
    private TableView<PlaceOrder> tableLocation;

    @FXML
    private TableColumn<PlaceOrder, String> stt, matour, madiadiem, tendiadiem;

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
    private Label label;

    @FXML
    private Button addBtn;

    Tour tourSend;

    public void setView(Tour tour) {
        label.setText(String.valueOf(tour.getMatour()));
        stt.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getThutu())));
        matour.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMatour())));
        madiadiem.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMadiadiem())));
        tendiadiem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlace().getTendiadiem()));

        locationList = FXCollections.observableArrayList(tour.getPlaceOrders());
        tableLocation.setItems(locationList);
        tableLocation.getSortOrder().add(stt);

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
                addBtn.setDisable(true);
            }
        });
    }

    public void handleInsertLocation() {
        PlaceOrder placeOrder = new PlaceOrder();
        List<PlaceOrder> newList;
        try {
            placeOrder.setThutu(Integer.parseInt(stttf.getText()));
            placeOrder.setMatour(Integer.parseInt(matourtf.getText()));
            placeOrder.setMadiadiem(Integer.parseInt(madiadiemtf.getText()));
            boolean flag = false;
            newList = getList();
            for (int i = 0; i < newList.size(); i++) {
                if (Integer.parseInt(stttf.getText()) == newList.get(i).getThutu()) {
                    LocationDAO.insert(placeOrder);
                    newList = getList();
                    flag = true;
                    for (int j = 0; j < newList.size(); j++) {
                        if (newList.get(j).getThutu() >= Integer.parseInt(stttf.getText()) &&
                                newList.get(j).getMadiadiem() != Integer.parseInt(madiadiemtf.getText())) {
                            PlaceOrder placeOrder2 = new PlaceOrder(Integer.parseInt(matourtf.getText()),
                                    newList.get(j).getMadiadiem(),
                                    newList.get(j).getThutu() + 1);
                            LocationDAO.update(placeOrder2);
                            LocationDAO.delete(newList.get(j));
                        }
                    }
                    break;
                }
            }
            if (flag == false && Integer.parseInt(stttf.getText())-newList.size() == 1) {
                LocationDAO.insert(placeOrder);
            } else {
                Notifications.create()
                        .title("Title Text")
                        .text("Thứ tự tiếp theo không hợp lệ")
                        .showWarning();
            }
            clearTextField();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng nhập dữ liệu")
                    .showWarning();
        }
    }

    public void handleUpdateLocation() {
        try {
            List<PlaceOrder> newList;
            PlaceOrder placeOrder = new PlaceOrder(Integer.parseInt(matourtf.getText()), Integer.parseInt(madiadiemtf.getText()), Integer.parseInt((stttf.getText())));
            PlaceOrder selected = tableLocation.getSelectionModel().getSelectedItem();
            if (placeOrder.getThutu() == selected.getThutu() &&
                    placeOrder.getMadiadiem() != selected.getMadiadiem()) {
                newList= getList();
                boolean flag = false;
                for (int i = 0; i < newList.size(); i++) {
                    if(placeOrder.getMadiadiem() == newList.get(i).getMadiadiem()){
                        flag = true;
                        break;
                    }
                }
                if(flag == false){
                    LocationDAO.update(placeOrder);
                    LocationDAO.delete(selected);
                }else {
                    Notifications.create()
                            .title("Title Text")
                            .text("Địa điểm đã có trong lộ trình")
                            .showWarning();
                }
            } else if ((placeOrder.getThutu() != selected.getThutu() &&
                    placeOrder.getMadiadiem() != selected.getMadiadiem()) ||
                    (placeOrder.getThutu() != selected.getThutu() &&
                    placeOrder.getMadiadiem() == selected.getMadiadiem())) {
                newList = getList();
                for (int j = 0; j < newList.size(); j++) {
                    if (newList.get(j).getThutu() == Integer.parseInt((stttf.getText()))) {
                        PlaceOrder placeOrder2 = new PlaceOrder(Integer.parseInt(matourtf.getText()),
                                newList.get(j).getMadiadiem(),
                                selected.getThutu());
                        LocationDAO.update(placeOrder);
                        LocationDAO.delete(newList.get(j));
                        LocationDAO.update(placeOrder2);
                        LocationDAO.delete(selected);
                    }
                }
            } else {
                Notifications.create()
                        .title("Title Text")
                        .text("Lộ trình đã tồn tại")
                        .showWarning();
            }
            loadData();
            clearTextField();
    } catch(Exception e) {
        Notifications.create()
                .title("Thông báo")
                .text("Vui lòng chọn dữ liệu cần update")
                .showWarning();
    }
}

    public void loadData() {
        Tour tour;
        tour = TourDAO.getDetail(tourSend.getMatour());
        tableLocation.setItems(FXCollections.observableArrayList(tour.getPlaceOrders()));
        tableLocation.getSortOrder().add(stt);
    }

    public List<PlaceOrder> getList(){
        return TourDAO.getDetail(tourSend.getMatour()).getPlaceOrders();
    }

    public void handleDeleteLocation() {
        PlaceOrder selected = tableLocation.getSelectionModel().getSelectedItem();
        try {
            LocationDAO.delete(selected);
            List<PlaceOrder> newList = TourDAO.getDetail(tourSend.getMatour()).getPlaceOrders();
            for (int j = 0; j < newList.size(); j++) {
                if (newList.get(j).getThutu() > selected.getThutu()) {
                    PlaceOrder placeOrder2 = new PlaceOrder(Integer.parseInt(matourtf.getText()),
                            newList.get(j).getMadiadiem(),
                            newList.get(j).getThutu() - 1);
                    LocationDAO.update(placeOrder2);
                    LocationDAO.delete(newList.get(j));
                }
            }
            loadData();
            clearTextField();
        } catch (Exception e) {
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }

    public void handleRefreshLocation() {
        loadData();
        clearTextField();
        addBtn.setDisable(false);
        tableLocation.getSortOrder().add(stt);
    }

    public void clearTextField() {
        stttf.clear();
        madiadiemtf.clear();
    }
}
