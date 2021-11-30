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
    private void handleClick(ActionEvent event) {
        if (event.getSource() == btnCustomer) {
            lbTittle.setText("Customer");
            pnTittle.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnTour) {
            lbTittle.setText("Tour");
            pnTittle.setBackground(new Background(new BackgroundFill(Color.rgb(3, 3, 8), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
