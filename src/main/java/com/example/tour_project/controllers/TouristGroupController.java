package com.example.tour_project.controllers;

import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.TouristGroup;
import com.example.tour_project.utils.HibernateUtil;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TouristGroupController implements Initializable {
    @FXML
    private TableView<TouristGroup> tableListGroup;

    @FXML
    private TableColumn<TouristGroup, String> madoan;

    @FXML
    private TableColumn<TouristGroup, String> matour;

    @FXML
    private TableColumn<TouristGroup, String> ngaykhoihanh;

    @FXML
    private TableColumn<TouristGroup, String> ngayketthuc;

//    @FXML
//    private TableColumn<TouristGroup, Float> doanhthu;

    @FXML
    private ObservableList<TouristGroup> touristGroup;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();

        madoan.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMadoan()));
        matour.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMatour()));
        ngaykhoihanh.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNgaykhoihanh())));
        ngayketthuc.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNgayketthuc())));

//        doanhthu.setCellValueFactory(new PropertyValueFactory<TouristGroup, Float>("doanhthu"));
        loadData();
    }

    private void loadData() {
        touristGroup = FXCollections.observableArrayList(TouristGroupDAO.listTourGroup()); //listen change in DB
        tableListGroup.getItems().clear();
        tableListGroup.setItems(touristGroup);
    }

    @FXML
    private void gotoDetails (ActionEvent e) throws IOException {
        //lấy stage hiện tại
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/tour_project/tourist-group-details.fxml"));
        Parent tourGroupDetailsParent = loader.load();
        Scene scene = new Scene(tourGroupDetailsParent);

        TouristGroupDetailsController controller = loader.getController();
        TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
        controller.setView(selected);

        stage.setScene(scene);
    }
}
