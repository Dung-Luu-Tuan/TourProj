package com.example.tour_project.controllers;

import java.io.IOException;
import java.util.List;

import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.dao.TypeTourDAO;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TypeTour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.example.tour_project.utils.HibernateUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.hibernate.Transaction;

import java.net.URL;
import java.util.ResourceBundle;

public class TourListsController implements Initializable {
    @FXML
    private TableView<Tour> tableListTours;

    @FXML
    private TableColumn<Tour, String> matour, tengoi, maloaihinh, dacdiem;

    @FXML
    private TextField tengoitf, maloaihinhtf, dacdiemtf, matourtf;

    @FXML
    private TableView<TypeTour> tableListTypeTour;

    @FXML
    private TableColumn<TypeTour, String> maloaihinhtb, tenloaihinhtb;

    @FXML
    private ObservableList<TypeTour> typeTourList;

    @FXML
    private ObservableList<Tour> tourList;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();
        matour.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMatour())));
        tengoi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTengoi()));
        maloaihinh.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMaloaihinh()));
        dacdiem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDacdiem()));
        loadData();
        tableListTours.setOnMouseClicked((MouseEvent e) -> {
            Tour selected = tableListTours.getSelectionModel().getSelectedItem();
            if (selected != null) {
                matourtf.setText(Integer.toString(selected.getMatour()));
                matourtf.setEditable(false);
                tengoitf.setText(selected.getTengoi());
                maloaihinhtf.setText(selected.getMaloaihinh());
                dacdiemtf.setText(selected.getDacdiem());
            }
        });

        maloaihinhtb.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMaloaihinh())));
        tenloaihinhtb.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenloaihinh()));
        typeTourList = FXCollections.observableArrayList(TypeTourDAO.listType());
        tableListTypeTour.getItems().clear();
        tableListTypeTour.setItems(typeTourList);
    }

    private void loadData() {
        tourList = FXCollections.observableArrayList(TourDAO.listTour());
        tableListTours.getItems().clear();
        tableListTours.setItems(tourList);
        matourtf.clear();
        tengoitf.clear();
        maloaihinhtf.clear();
        dacdiemtf.clear();
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

    public void gotoDetails(ActionEvent e) throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/tour-details.fxml"));
            Parent tourDetailsParent = loader.load();
            Scene scene = new Scene(tourDetailsParent);

            TourDetailsController controller = loader.getController();
            Tour selected = tableListTours.getSelectionModel().getSelectedItem();
            controller.setView(selected);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e1) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xem chi tiết")
                    .showWarning();
        }

    }

    public void handleUpdateTour() {
        try {
            Tour tour = new Tour(Integer.parseInt(matourtf.getText()), tengoitf.getText(), maloaihinhtf.getText(), dacdiemtf.getText());
            TourDAO.update(tour);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleInsertTour() {
        Tour tour = new Tour();
        try {
            tour.setTengoi(tengoitf.getText());
            tour.setDacdiem(dacdiemtf.getText());
            tour.setMaloaihinh(maloaihinhtf.getText());
            if ((matourtf.getText()) == "") {
                TourDAO.insert(tour);
                loadData();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Tour đã tồn tại")
                        .showWarning();
            }
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng nhập dữ liệu cần thêm")
                    .showWarning();
        }
    }

    public void handleDeleteTour() {
        try {
            Tour tour = new Tour(Integer.parseInt(matourtf.getText()), tengoitf.getText(), maloaihinhtf.getText(), dacdiemtf.getText());
            TourDAO.delete(tour);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }

}