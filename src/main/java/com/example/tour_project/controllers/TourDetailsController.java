package com.example.tour_project.controllers;

import com.example.tour_project.dao.PriceDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.models.PlaceOrder;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TourPrice;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class TourDetailsController{

    @FXML
    Label matour;

    @FXML
    Label tengoi;

    @FXML
    Label maloaihinh;

    @FXML
    Label dacdiem;

    @FXML
    private TableColumn<TourPrice, String> thanhtien;

    @FXML
    private TableColumn<TourPrice, String> magia;

    @FXML
    private TableColumn<TourPrice, String> thoigianbatdau;

    @FXML
    private TableColumn<TourPrice, String> thoigianketthuc;

    @FXML
    private TableColumn<PlaceOrder, String> stt;

    @FXML
    private TableColumn<PlaceOrder, String> madiadiem;

    @FXML
    private TableColumn<PlaceOrder, String> tendiadiem;


    @FXML
    private TableView<TourPrice> tableListDetailTour;

    @FXML
    private ObservableList<TourPrice> tourDetailList;

    @FXML
    private TableView<PlaceOrder> tablePlace;

    @FXML
    private ObservableList<PlaceOrder> placeList;

    Tour tour2 = null;
    public void setView(Tour tour) {
        tour2 = TourDAO.getDetail(tour.getMatour());

        matour.setText(String.valueOf(tour.getMatour()));
        tengoi.setText(String.valueOf(tour.getTengoi()));
        maloaihinh.setText(String.valueOf(tour2.getLoaihinh().getTenloaihinh()));
        dacdiem.setText((String.valueOf(tour.getDacdiem())));

        magia.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((data.getValue().getMagia()))));
        thanhtien.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.priceWithoutDecimal(data.getValue().getThanhtien())));
        thoigianbatdau.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getDateStart())));
        thoigianketthuc.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getDateEnd())));

        tourDetailList = FXCollections.observableArrayList(tour2.getPrices());
        tableListDetailTour.setItems(tourDetailList);

        stt.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((data.getValue().getThutu()))));
        tendiadiem.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((data.getValue().getPlace().getTendiadiem()))));
        madiadiem.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((data.getValue().getMadiadiem()))));

        placeList = FXCollections.observableArrayList(tour2.getPlaceOrders());
        tablePlace.setItems(placeList);
        tablePlace.getSortOrder().add(stt);
    }

    public void gotoLocations(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/tour-location.fxml"));
        Parent tourLocationsParent = loader.load();
        Scene scene = new Scene(tourLocationsParent);

        TourLocationController controller = loader.getController();
        controller.setView(tour2);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void gotoPriceList(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/price-list.fxml"));
        Parent tourLocationsParent = loader.load();
        Scene scene = new Scene(tourLocationsParent);

        PriceListController controller = loader.getController();
        controller.setView(tour2);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
