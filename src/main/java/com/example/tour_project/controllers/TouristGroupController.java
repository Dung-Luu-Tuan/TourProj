package com.example.tour_project.controllers;

import com.example.tour_project.dao.CustomerTourDAO;
import com.example.tour_project.dao.PriceDAO;
import com.example.tour_project.dao.TourDAO;
import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.models.*;
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

import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;


public class TouristGroupController implements Initializable {

    @FXML
    private TableColumn<TouristGroup, String> madoan, tengoi, matour, ngaykhoihanh, ngayketthuc, stt;

    @FXML
    private TextField madoantf, matourtf, ngaykhoihanhtf, ngayketthuctf, doanhthutf;

    @FXML
    private TableColumn<Tour, String> tourID, tourName;

    @FXML
    private TableColumn<TourPrice, String> startDay, endDay, matourPrice, priceTour;

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

    @FXML
    private TableView<TourPrice> tablePrice;

    @FXML
    private ObservableList<TourPrice> priceTourList;

    private static SessionFactory factory;

    SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
    float doanhthu2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        factory = HibernateUtil.getSessionFactory();

        madoan.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMadoan())));
        matour.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMatour())));
        tengoi.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTour().getTengoi()));
        ngaykhoihanh.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getNgaykhoihanh())));
        ngayketthuc.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getNgayketthuc())));

        tourID.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMatour())));
        tourName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTengoi()));
        loadData();

        startDay.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getDateStart())));
        endDay.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.DateFormat((Date) data.getValue().getDateEnd())));
        matourPrice.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMatour())));
        priceTour.setCellValueFactory(data -> new SimpleStringProperty(PriceDAO.priceWithoutDecimal(data.getValue().getThanhtien())));

        tableListGroup.setOnMouseClicked((MouseEvent e) -> {
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            Tour tour = TourDAO.getDetail(selected.getMatour());
            TouristGroup touristGroup = TouristGroupDAO.getDetailsByCustomer(selected.getMadoan());
            Float price = null;
            int result;

            for (TourPrice tourPrice : tour.getPrices()) {
                if (tourPrice.getDateStart().equals(selected.getNgaykhoihanh())) {
                    price = tourPrice.getThanhtien();
                    break;
                }
            }
            if (price == null) {
                result = (int) (touristGroup.getCustomerTour().size() * 0);
            } else {
                result = (int) (touristGroup.getCustomerTour().size() * price);
            }
            if (selected != null) {
                madoantf.setText(Integer.toString(selected.getMadoan()));
                madoantf.setEditable(false);
                doanhthutf.setEditable(false);
                matourtf.setText(Integer.toString(selected.getMatour()));
                ngaykhoihanhtf.setText(PriceDAO.DateFormat2((Date) selected.getNgaykhoihanh()));
                ngayketthuctf.setText(PriceDAO.DateFormat2((Date) selected.getNgayketthuc()));

                doanhthu2 = Float.parseFloat(String.valueOf(result));
                doanhthutf.setText(String.valueOf(result));
            }
        });

        tableTour.setOnMouseClicked((MouseEvent e) -> {
            Tour selected = tableTour.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Tour tourPrice = TourDAO.getDetail(selected.getMatour());
                priceTourList = FXCollections.observableArrayList(tourPrice.getPrices());
                tablePrice.getItems().clear();
                tablePrice.setItems(priceTourList);
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
        clear();
    }

    public void clear() {
        madoantf.clear();
        matourtf.clear();
        ngaykhoihanhtf.clear();
        ngayketthuctf.clear();
        doanhthutf.clear();
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
    private void gotoDetails(ActionEvent e) throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/tourist-group-details.fxml"));
            Parent tourGroupDetailsParent = loader.load();
            Scene scene = new Scene(tourGroupDetailsParent);

            TouristGroupDetailsController controller = loader.getController();
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            controller.setView(selected);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e1) {
            e1.printStackTrace();
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xem chi tiết")
                    .showWarning();
        }
    }

    @FXML
    private void gotoCustomers(ActionEvent e) throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/customers-group-tour.fxml"));
            Parent customersParent = loader.load();
            Scene scene = new Scene(customersParent);

            CustomersGroupTourController controller = loader.getController();
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            controller.setView(selected);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e1) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn đoàn du lịch cần xem")
                    .showWarning();
        }
    }

    @FXML
    public void gotoStaffAllocation(ActionEvent e) throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/staff-tour-list.fxml"));
            Parent tourLocationsParent = loader.load();
            Scene scene = new Scene(tourLocationsParent);

            StaffTourController controller = loader.getController();
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            controller.setView(selected);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e1) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn đoàn du lịch cần xem")
                    .showWarning();
        }
    }

    @FXML
    public void gotoCost(ActionEvent e) throws IOException {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/tour_project/cost-tour-group.fxml"));
            Parent tourLocationsParent = loader.load();
            Scene scene = new Scene(tourLocationsParent);

            CostTourGroupController controller = loader.getController();
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            controller.setView(selected);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e1) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn đoàn du lịch cần xem")
                    .showWarning();
        }
    }

    public void handleUpdateTouristGroup() {
        TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
        Tour tourPrice = TourDAO.getDetail(selected.getMatour());
        try {
            boolean flag = false;
            for(TourPrice t : tourPrice.getPrices()){
                if(formatter1.parse(ngaykhoihanhtf.getText()).equals(t.getDateStart()) &&
                        formatter1.parse(ngayketthuctf.getText()).equals(t.getDateEnd())){
                    flag = true;
                    break;
                }
            }
            if(flag == true){
                TouristGroup tourGroup = new TouristGroup(Integer.parseInt(madoantf.getText()), Integer.parseInt(matourtf.getText()), formatter1.parse(ngaykhoihanhtf.getText()), formatter1.parse(ngayketthuctf.getText()), doanhthu2);
                TouristGroupDAO.update(tourGroup);
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Ngày khởi hành hoặc ngày kết thúc không hợp lệ")
                        .showWarning();
            }

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần update")
                    .showWarning();
        }
    }

    public void handleInsertTouristGroup() {
        TouristGroup tourGroup = new TouristGroup();
        try {
            TouristGroup selected = tableListGroup.getSelectionModel().getSelectedItem();
            Tour tourPrice = TourDAO.getDetail(selected.getMatour());
            boolean flag = false;
            for(TourPrice t : tourPrice.getPrices()){
                if(formatter1.parse(ngaykhoihanhtf.getText()).equals(t.getDateStart()) &&
                        formatter1.parse(ngayketthuctf.getText()).equals(t.getDateEnd())) {
                    flag = true;
                    break;
                }
            }

            if ((madoantf.getText()) == "") {
                if (flag == true) {
                    tourGroup.setMatour(Integer.parseInt(matourtf.getText()));
                    tourGroup.setNgaykhoihanh(formatter1.parse(ngaykhoihanhtf.getText()));
                    tourGroup.setNgayketthuc(formatter1.parse(ngayketthuctf.getText()));
                    tourGroup.setDoanhthu(0);
                    TouristGroupDAO.insert(tourGroup);
                    loadData();
                } else {
                    Notifications.create()
                            .title("Thông báo")
                            .text("Ngày khởi hành hoặc ngày kết thúc không hợp lệ")
                            .showWarning();
                }
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Đoàn du lịch này đã tồn tại")
                        .showWarning();
            }
        } catch (Exception e) {
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng nhập đầy đủ dữ liệu cần thêm")
                    .showWarning();
        }
    }

    public void handleDeleteTouristGroup() {
        try {
            TouristGroup tourGroup = new TouristGroup(Integer.parseInt(madoantf.getText()), Integer.parseInt(matourtf.getText()), formatter1.parse(ngaykhoihanhtf.getText()), formatter1.parse(ngayketthuctf.getText()), doanhthu2);
            TouristGroupDAO.delete(tourGroup);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            Notifications.create()
                    .title("Thông báo")
                    .text("Vui lòng chọn dữ liệu cần xóa")
                    .showWarning();
        }
    }
}
