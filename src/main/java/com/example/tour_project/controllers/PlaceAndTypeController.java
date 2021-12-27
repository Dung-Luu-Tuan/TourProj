package com.example.tour_project.controllers;

import com.example.tour_project.dao.PlaceDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.dao.TypeTourDAO;
import com.example.tour_project.models.Place;
import com.example.tour_project.models.PlaceOrder;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TypeTour;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaceAndTypeController implements Initializable {
    @FXML
    private TableView<Place> placeTable;

    @FXML
    private TableColumn<Place, String> madiadiem, tendiadiem;

    @FXML
    private ObservableList<Place> placeList;

    @FXML
    private TextField madiadiemtf, tendiadiemtf, maloaihinhtf, tenloaihinhtf;

    @FXML
    private TableView<TypeTour> typeTable;

    @FXML
    private TableColumn<TypeTour, String> maloaihinh, tenloaihinh;

    @FXML
    private ObservableList<TypeTour> typeList;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        madiadiem.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMadiadiem())));
        tendiadiem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTendiadiem()));
        loadDataPlace();

        maloaihinh.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMaloaihinh())));
        tenloaihinh.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenloaihinh()));
        loadDataType();

        placeTable.setOnMouseClicked((MouseEvent e) -> {
            Place selected = placeTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                madiadiemtf.setText(String.valueOf(selected.getMadiadiem()));
                tendiadiemtf.setText(selected.getTendiadiem());
            }
        });

        typeTable.setOnMouseClicked((MouseEvent e) -> {
            TypeTour selected = typeTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                maloaihinhtf.setText(String.valueOf(selected.getMaloaihinh()));
                tenloaihinhtf.setText(selected.getTenloaihinh());
            }
        });
    }

    public void handleInsertPlace(){
        try{
            Place place = new Place();
            if ((madiadiemtf.getText()) == "") {
                place.setTendiadiem(tendiadiemtf.getText());
                PlaceDAO.insert(place);
                handleRefreshPlace();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Địa điểm đã tồn tại")
                        .showWarning();
            }
        } catch (Exception e){
            e.printStackTrace();
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng nhập dữ liệu cần thêm")
                    .showWarning();
        }
    }

    public void handleDeletePlace() {
        try {
            Place place = new Place(Integer.parseInt(madiadiemtf.getText()), tendiadiemtf.getText());
            PlaceDAO.delete(place);
            handleRefreshPlace();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }

    public void handleUpdatePlace() {
        try {
            Place place = new Place(Integer.parseInt(madiadiemtf.getText()), tendiadiemtf.getText());
            PlaceDAO.update(place);
            handleRefreshPlace();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleRefreshPlace() {
        try {
            loadDataPlace();
            madiadiemtf.clear();
            tendiadiemtf.clear();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Không có dữ liệu nào được làm mới")
                    .showWarning();
        }
    }

    public void handleInsertType(){
        try{
            TypeTour typeTour = new TypeTour();
            if ((maloaihinhtf.getText()) == "") {
                typeTour.setTenloaihinh(tenloaihinhtf.getText());
                TypeTourDAO.insert(typeTour);
                handleRefreshType();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Loại hình đã tồn tại")
                        .showWarning();
            }
        } catch (Exception e){
            e.printStackTrace();
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng nhập dữ liệu cần thêm")
                    .showWarning();
        }
    }

    public void handleDeleteType() {
        try {
            TypeTour typeTour = new TypeTour(Integer.parseInt(maloaihinhtf.getText()), tenloaihinhtf.getText());
            TypeTourDAO.delete(typeTour);
            handleRefreshType();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }

    public void handleUpdateType() {
        try {
            TypeTour typeTour = new TypeTour(Integer.parseInt(maloaihinhtf.getText()), tenloaihinhtf.getText());
            TypeTourDAO.update(typeTour);
            handleRefreshType();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleRefreshType() {
        try {
            loadDataType();
            maloaihinhtf.clear();
            tenloaihinhtf.clear();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Không có dữ liệu nào được làm mới")
                    .showWarning();
        }
    }

    public void loadDataPlace(){
        placeList = FXCollections.observableArrayList(PlaceDAO.listPlace());
        placeTable.getItems().clear();
        placeTable.setItems(placeList);
    }

    public void loadDataType(){
        typeList = FXCollections.observableArrayList(TypeTourDAO.listType());
        typeTable.getItems().clear();
        typeTable.setItems(typeList);
    }
}
