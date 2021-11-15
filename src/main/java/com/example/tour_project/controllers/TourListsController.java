package com.example.tour_project.controllers;

import java.io.IOException;
import java.util.List;

import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.models.Tour;
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
        tableListTours.setOnMouseClicked((MouseEvent e) ->{
            Tour selected = tableListTours.getSelectionModel().getSelectedItem();
            if (selected != null) {
                matourtf.setText(Integer.toString(selected.getMatour()));
                matourtf.setEditable(false);
                tengoitf.setText(selected.getTengoi());
                maloaihinhtf.setText(selected.getMaloaihinh());
                dacdiemtf.setText(selected.getDacdiem());
            }
        });
    }

    private void loadData() {
        tourList = FXCollections.observableArrayList(TourDAO.listTour());
        tableListTours.getItems().clear();
        tableListTours.setItems(tourList);
    }

    public void gotoDetails(ActionEvent e) throws IOException {
        //lấy stage hiện tại
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/tour_project/tour-details.fxml"));
        Parent tourDetailsParent = loader.load();
        Scene scene = new Scene(tourDetailsParent);

        TourDetailsController controller = loader.getController();
        Tour selected = tableListTours.getSelectionModel().getSelectedItem();
        controller.setView(selected);

        stage.setScene(scene);
    }

    public void handleUpdateTour(ActionEvent e1) {
        Tour tour = new Tour(Integer.parseInt(matourtf.getText()), tengoitf.getText(),  maloaihinhtf.getText(),dacdiemtf.getText());
        try {
            TourDAO.update(tour);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleInsertTour(ActionEvent e1) {
        Tour tour = new Tour();
        try {
            tour.setTengoi(tengoitf.getText());
            tour.setDacdiem(dacdiemtf.getText());
            tour.setMaloaihinh(maloaihinhtf.getText());
            TourDAO.insert(tour);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteTour(ActionEvent e1) {
        Tour tour = new Tour(Integer.parseInt(matourtf.getText()), tengoitf.getText(),  maloaihinhtf.getText(),dacdiemtf.getText());
        try {
            TourDAO.delete(tour);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}