package com.example.tour_project.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnTour;

    @FXML
    private Label lbTittle;

    @FXML
    private Pane pnTittle;


    @FXML
    private StackPane contentArena;

    @FXML
    public void customer(javafx.event.ActionEvent actionEvent) throws IOException{
        lbTittle.setText("Khách hàng");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/customer-list.fxml"));
        Parent root = loader.load();
        contentArena.getChildren().removeAll();
        contentArena.getChildren().setAll(root);
        btnCustomer.getStyleClass().add("active");
    }

    @FXML
    public void tour(javafx.event.ActionEvent actionEvent) throws IOException{
        lbTittle.setText("Tour du lịch");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/tour-lists.fxml"));
        Parent root = loader.load();
        contentArena.getChildren().removeAll();
        contentArena.getChildren().setAll(root);
    }
    @FXML
    public void touristGroup(javafx.event.ActionEvent actionEvent) throws IOException{
        lbTittle.setText("Đoàn khách");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/tourist-group.fxml"));
        Parent root = loader.load();
        contentArena.getChildren().removeAll();
        contentArena.getChildren().setAll(root);
    }
    @FXML
    public void tourLocationAndTypeTour(javafx.event.ActionEvent actionEvent) throws IOException{
        lbTittle.setText("Địa điểm và loại chi phí");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/place-and-type.fxml"));
        Parent root = loader.load();
        contentArena.getChildren().removeAll();
        contentArena.getChildren().setAll(root);
    }
    @FXML
    public void staff(javafx.event.ActionEvent actionEvent) throws IOException{
        lbTittle.setText("Nhân viên");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/staff-lists.fxml"));
        Parent root = loader.load();
        contentArena.getChildren().removeAll();
        contentArena.getChildren().setAll(root);
    }
    @FXML
    public void typeCost(javafx.event.ActionEvent actionEvent) throws IOException{
        lbTittle.setText("Nhân viên");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/typecost-list.fxml"));
        Parent root = loader.load();
        contentArena.getChildren().removeAll();
        contentArena.getChildren().setAll(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
