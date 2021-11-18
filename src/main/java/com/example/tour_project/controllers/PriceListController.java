package com.example.tour_project.controllers;

import com.example.tour_project.dao.LocationDAO;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PriceListController {
    @FXML
    private TableView<TourPrice> tablePrice;

    @FXML
    private TableColumn<TourPrice, String> magia, thanhtien, thoigianbatdau, thoigianketthuc;

    @FXML
    private ObservableList<TourPrice> PriceList;

    @FXML
    private TextField magiatf, thanhtientf, starttf, endtf;

    @FXML
    private Button addBtn;

    Tour tourSend;

    public void setView(Tour tour) {
        magia.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((data.getValue().getMagia()))));
        thanhtien.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.priceWithoutDecimal(data.getValue().getThanhtien())));
        thoigianbatdau.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getDateStart())));
        thoigianketthuc.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getDateEnd())));

        PriceList = FXCollections.observableArrayList(tour.getPrices());
        tablePrice.setItems(PriceList);

        tourSend = tour;

        tablePrice.setOnMouseClicked((MouseEvent e) -> {
            TourPrice selected = tablePrice.getSelectionModel().getSelectedItem();
            if (selected != null) {
                magiatf.setText(Integer.toString(selected.getMagia()));
                thanhtientf.setText(Float.toString(selected.getThanhtien()));
                starttf.setText(String.valueOf(selected.getDateStart()));
                endtf.setText(String.valueOf(selected.getDateEnd()));
                addBtn.setVisible(false);
                magiatf.setEditable(false);
            }
        });
    }

    public void goBack(ActionEvent e) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/tour_project/tour-details.fxml"));
        Parent tourDetailsParent = loader.load();
        Scene scene = new Scene(tourDetailsParent);

        TourDetailsController controller = loader.getController();
        controller.setView(tourSend);

        stage.setScene(scene);
    }

    public void loadData(int matour) {
        Tour tour;
        tour = TourDAO.getDetail(matour);
        tablePrice.setItems(FXCollections.observableArrayList(tour.getPrices()));
    }

    public void handleInsertPrice() {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        TourPrice tourPrice = new TourPrice();
        try {
            tourPrice.setMatour(tourSend.getMatour());
            tourPrice.setThanhtien(Float.parseFloat(thanhtientf.getText()));
            tourPrice.setDateStart(formatter1.parse(starttf.getText()));
            tourPrice.setDateEnd(formatter1.parse(endtf.getText()));
            if(tourPrice.getDateEnd().before(tourPrice.getDateStart())){
                Notifications.create()
                        .title("Thông báo")
                        .text("Thời gian kết thúc không hợp lệ")
                        .showWarning();
            }
            else if((magiatf.getText()) == ""){
                PriceDAO.insert(tourPrice);
                loadData(tourSend.getMatour());
                clearTextField();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Giá đã tồn tại")
                        .showWarning();
            }
        } catch (Exception e){
            Notifications.create()
                    .title("Title Text")
                    .text("Vui lòng nhập dữ liệu")
                    .showWarning();
        }
    }

    public void handleDeletePrice() {
        TourPrice selected = tablePrice.getSelectionModel().getSelectedItem();
        try {
            if(selected != null){
                PriceDAO.delete(selected);
                loadData(tourSend.getMatour());
                clearTextField();
            } else {
                Notifications.create()
                        .title("Title Text")
                        .text("Vui lòng chọn dữ liệu cần xóa")
                        .showWarning();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleUpdatePrice() {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            TourPrice tourPrice = new TourPrice(Integer.parseInt(magiatf.getText()), tourSend.getMatour(), Float.parseFloat(thanhtientf.getText()), formatter1.parse(starttf.getText()), formatter1.parse(endtf.getText()));
            TourPrice selected = tablePrice.getSelectionModel().getSelectedItem();
            if (tourPrice.getThanhtien() == selected.getThanhtien() &&
                    tourPrice.getDateStart() == selected.getDateStart() &&
                    tourPrice.getDateEnd() == selected.getDateEnd()) {
                Notifications.create()
                        .title("Title Text")
                        .text("Giá tour đã tồn tại")
                        .showWarning();
            } else {
                PriceDAO.update(tourPrice);
                clearTextField();
                loadData(tourSend.getMatour());
            }
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleRefreshPrice() {
        loadData(tourSend.getMatour());
        clearTextField();
        addBtn.setVisible(true);
    }

    public void clearTextField() {
        magiatf.clear();
        thanhtientf.clear();
        starttf.clear();
        endtf.clear();
    }
}
