package com.example.tour_project.controllers;

import com.example.tour_project.dao.PriceDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TourPrice;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class TouristGroupController implements Initializable {

    @FXML
    private TableColumn<TouristGroup, String> madoan, tengoi, matour, ngaykhoihanh, ngayketthuc;

    @FXML
    private TextField madoantf, matourtf, ngaykhoihanhtf, ngayketthuctf;

    @FXML
    private TableColumn<Tour, String> tourID, tourName ;

    @FXML
    private  TableColumn<TourPrice, String> startDay, endDay;
//    @FXML
//    private TableColumn<TouristGroup, Float> doanhthu;
    @FXML
    private TableView<TouristGroup> tableListGroup;

    @FXML
    private ObservableList<TouristGroup> touristGroup;

    @FXML
    private TableView<Tour> tableTour;

    @FXML
    private ObservableList<Tour> tour;

    @FXML
    private ObservableList<TourPrice> tourPrices;

    private static SessionFactory factory;





    SimpleDateFormat formatToDate = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();

        madoan.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMadoan())));
        matour.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMatour())));
        tengoi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTour().getTengoi()));
        ngaykhoihanh.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNgaykhoihanh())));
        ngayketthuc.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getNgayketthuc())));

        tourID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMatour())));
        tourName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTengoi()));
//        startDay.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDateStart())));
//        endDay.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getDateEnd())));
//        doanhthu.setCellValueFactory(new PropertyValueFactory<TouristGroup, Float>("doanhthu"));
        loadData();

        tableListGroup.setOnMouseClicked((MouseEvent e) -> {
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            if (selected != null) {
                madoantf.setText(Integer.toString(selected.getMadoan()));
                madoantf.setEditable(false);
                matourtf.setText(Integer.toString(selected.getMatour()));
                ngaykhoihanhtf.setText(String.valueOf(selected.getNgaykhoihanh()));
                ngayketthuctf.setText(String.valueOf(selected.getNgayketthuc()));
                //doanhthu
            }
        });
    }

    private void loadData() {
        touristGroup = FXCollections.observableArrayList(TouristGroupDAO.listTourGroup()); //listen change in DB
        tableListGroup.getItems().clear();
        tableListGroup.setItems(touristGroup);

        tour = FXCollections.observableArrayList(TourDAO.listTour());
        tableTour.getItems().clear();
        tableTour.setItems(tour);

//        tourPrices = FXCollections.observableArrayList(PriceDAO.listLocation());
//        tableTour.getItems().clear();
//        tableTour.setItems(tourPrices);

        madoantf.clear();
        matourtf.clear();
        ngaykhoihanhtf.clear();
        ngayketthuctf.clear();
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

    @FXML
    private void gotoDetails (ActionEvent e) throws IOException {
       try {
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
       } catch (Exception e1) {
           Notifications.create()
                   .title("Thông báo")
                   .text("Vui lòng chọn dữ liệu cần xem chi tiết")
                   .showWarning();
       }
    }

    public void handleUpdateTouristGroup() {
        try {

            TouristGroup tourGroup = new TouristGroup(Integer.parseInt(madoantf.getText()), Integer.parseInt(matourtf.getText()), formatToDate.parse(ngaykhoihanhtf.getText()), formatToDate.parse(ngayketthuctf.getText()));
            TouristGroupDAO.update(tourGroup);
            loadData();

        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleInsertTouristGroup() {
        TouristGroup tourGroup = new TouristGroup();
        try {
            tourGroup.setMatour(Integer.parseInt(matourtf.getText()));
            tourGroup.setNgaykhoihanh(formatToDate.parse(ngaykhoihanhtf.getText()));
            tourGroup.setNgayketthuc(formatToDate.parse(ngayketthuctf.getText()));
            if ((madoantf.getText()) == "") {
                TouristGroupDAO.insert(tourGroup);
                loadData();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Mã đoàn tự cập nhật")
                        .showWarning();
            }
        }
        catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng nhập dữ liệu cần thêm")
                    .showWarning();
        }
    }

    public void handleDeleteTouristGroup() {
        try {
            TouristGroup tourGroup = new TouristGroup(Integer.parseInt(madoantf.getText()), Integer.parseInt(matourtf.getText()), formatToDate.parse(ngaykhoihanhtf.getText()), formatToDate.parse(ngayketthuctf.getText()));
            TouristGroupDAO.delete(tourGroup);
            loadData();
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }
}
