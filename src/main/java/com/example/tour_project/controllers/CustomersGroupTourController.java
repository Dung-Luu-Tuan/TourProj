package com.example.tour_project.controllers;

import com.example.tour_project.dao.CustomerDAO;
import com.example.tour_project.dao.CustomerTourDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.*;
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
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class CustomersGroupTourController implements Serializable {
    @FXML
    Label nameGroupCustomer;

    @FXML
    Button addCustomer;

    @FXML
    private TableView<Customer> tableGroupCustomers;

    @FXML
    private TableColumn<Customer, String> idCustomer, nameCustomer, cmndCustomer, addressCustomer, genderCustomer, phoneCustomer, nationCustomer;

    @FXML
    private TextField idCustomerTf;

    @FXML
    private TableView<Customer> tableCustomers;

    @FXML
    private TableColumn<Customer, String> idCustomerColumn, nameCustomerColumn, phoneCustomerColumn;

    @FXML
    private ObservableList<Customer> GroupCustomer;

    @FXML
    private

    TouristGroup touristGroupSend;
    List<Customer> listCustomers = null;

    public void setView(TouristGroup touristGroup) {
        TouristGroup groupCus = TouristGroupDAO.getDetailsByCustomer(touristGroup.getMadoan());

        nameGroupCustomer.setText(touristGroup.getTour().getTengoi());
        idCustomer.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMakhachhang())));
        nameCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHoten()));
        cmndCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCmnd()));
        addressCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDiachi()));
        genderCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGioitinh()));
        phoneCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSdt()));
        nationCustomer.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuoctich()));

        GroupCustomer = FXCollections.observableArrayList(groupCus.getCustomers());
        tableGroupCustomers.setItems(GroupCustomer);

        idCustomerColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMakhachhang())));
        nameCustomerColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getHoten())));
        phoneCustomerColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSdt()));

        GroupCustomer = FXCollections.observableArrayList(CustomerDAO.listCustomer());
        tableCustomers.setItems(GroupCustomer);

        tableGroupCustomers.setOnMouseClicked((MouseEvent e) -> {
            Customer selected = tableGroupCustomers.getSelectionModel().getSelectedItem();
            if (selected != null) {
                idCustomerTf.setText(Integer.toString(selected.getMakhachhang()));
                addCustomer.setDisable(true);
            }
        });
        touristGroupSend = touristGroup;
    }

    private void loadData() {
        TouristGroup touristGroup = TouristGroupDAO.getDetailsByCustomer(touristGroupSend.getMadoan());
        tableGroupCustomers.setItems(FXCollections.observableArrayList(touristGroup.getCustomers()));
    }

    public void clear() {
        idCustomerTf.clear();
    }

    public void handleRefresh() {
        try {
            loadData();
            clear();
            addCustomer.setDisable(false);
        } catch (Exception e) {
            Notifications.create()
                    .title("Th??ng b??o")
                    .text("Kh??ng c?? d??? li???u n??o ???????c l??m m???i" + e)
                    .showWarning();
        }
    }

    public float Revenue(int id){
        TouristGroup touristGroup = TouristGroupDAO.getTouristGroupById(id);
        Tour tour = TourDAO.getDetail(touristGroup.getMatour());
        Float price = null;
        int result;

        for (TourPrice tourPrice : tour.getPrices()) {
            if (tourPrice.getDateStart().equals(touristGroup.getNgaykhoihanh())) {
                price = tourPrice.getThanhtien();
                break;
            }
        }
        if (price == null) {
            result = (int) (touristGroup.getCustomerTour().size() * 0);
        } else {
            result = (int) (touristGroup.getCustomerTour().size() * price);
        }
        return result;
    }

    public void handleInsertGroupCustomers() {
        listCustomers = CustomerDAO.listCustomer();
        try {
            boolean flag = false;
            for(Customer c : listCustomers){
                if(idCustomerTf.getText().equals(String.valueOf(c.getMakhachhang()))){
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
                if(flag == true){
                    CustomerTour customer = new CustomerTour(touristGroupSend.getMadoan(),
                            Integer.parseInt(idCustomerTf.getText()));
                    CustomerTourDAO.insertCustomerTour(customer);
                    float revenue = Revenue(touristGroupSend.getMadoan());
                    TouristGroupDAO.update2(touristGroupSend.getMadoan(), revenue);
                    clear();
                    loadData();
                } else {
                    Notifications.create()
                            .title("Th??ng b??o")
                            .text("Kh??ch h??ng kh??ng t???n t???i")
                            .showWarning();
                }
        } catch (Exception e) {
            Notifications.create()
                    .title("Th??ng b??o")
                    .text("Kh??ch h??ng ???? c?? trong ??o??n")
                    .showWarning();
        }
    }

    public void handleDeleteGroupCustomers() {
        try {
            if(idCustomerTf.getText() != ""){
                CustomerTour customerTour = new CustomerTour(touristGroupSend.getMadoan(), Integer.parseInt(idCustomerTf.getText()));
                CustomerTourDAO.deleteCustomerTour(customerTour);
                float revenue = Revenue(touristGroupSend.getMadoan());
                TouristGroupDAO.update2(touristGroupSend.getMadoan(), revenue);
                loadData();
            } else {
                Notifications.create()
                        .title("Th??ng b??o")
                        .text("Vui l??ng ch???n d??? li???u c???n x??a")
                        .showWarning();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
