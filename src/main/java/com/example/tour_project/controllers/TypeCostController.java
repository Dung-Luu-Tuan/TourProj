package com.example.tour_project.controllers;

import com.example.tour_project.dao.PlaceDAO;
import com.example.tour_project.dao.TypeCostDAO;
import com.example.tour_project.models.Place;
import com.example.tour_project.models.TypeCost;
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

public class TypeCostController implements Initializable {
    @FXML
    private TableView<TypeCost> typeCostTable;

    @FXML
    private TableColumn<TypeCost, String> machiphi, tenchiphi;

    @FXML
    private ObservableList<TypeCost> typeCostList;

    @FXML
    private TextField machiphitf, tenchiphitf;

    private static SessionFactory factory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        machiphi.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMaloaichiphi())));
        tenchiphi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTenchiphi()));
        loadData();

        typeCostTable.setOnMouseClicked((MouseEvent e) -> {
            TypeCost selected = typeCostTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                machiphitf.setText(String.valueOf(selected.getMaloaichiphi()));
                tenchiphitf.setText(selected.getTenchiphi());
            }
        });
    }

    public void loadData(){
        typeCostList = FXCollections.observableArrayList(TypeCostDAO.listTypeCost());
        typeCostTable.getItems().clear();
        typeCostTable.setItems(typeCostList);
    }

    public void handleInsertTypeCost(){
        TypeCost typeCost = new TypeCost();
        try{
            typeCost.setTenchiphi(tenchiphitf.getText());
            if ((machiphitf.getText()) == "") {
                TypeCostDAO.insert(typeCost);
                handleRefreshTypeCost();
            } else {
                Notifications.create()
                        .title("Th??ng b??o")
                        .text("Lo???i chi ph?? ???? t???n t???i")
                        .showWarning();
            }
        } catch (Exception e){
            e.printStackTrace();
            Notifications.create()
                    .title("Th??ng b??o")
                    .text("Vui l??ng nh???p d??? li???u c???n th??m")
                    .showWarning();
        }
    }

    public void handleDeleteTypeCost() {
        try {
            TypeCost typeCost = new TypeCost(Integer.parseInt(machiphitf.getText()), tenchiphitf.getText());
            TypeCostDAO.delete(typeCost);
            handleRefreshTypeCost();
        } catch (Exception e) {
            Notifications.create()
                    .title("Th??ng b??o")
                    .text("Vui l??ng ch???n d??? li???u c???n x??a")
                    .showWarning();
        }
    }

    public void handleUpdateTypeCost() {
        try {
            TypeCost typeCost = new TypeCost(Integer.parseInt(machiphitf.getText()), tenchiphitf.getText());
            TypeCostDAO.update(typeCost);
            handleRefreshTypeCost();
        } catch (Exception e) {
            Notifications.create()
                    .title("Th??ng b??o")
                    .text("Vui l??ng ch???n d??? li???u c???n update")
                    .showWarning();
        }
    }

    public void handleRefreshTypeCost() {
        try {
            loadData();
            machiphitf.clear();
            tenchiphitf.clear();
        } catch (Exception e) {
            Notifications.create()
                    .title("Th??ng b??o")
                    .text("Kh??ng c?? d??? li???u n??o ???????c l??m m???i")
                    .showWarning();
        }
    }
}
