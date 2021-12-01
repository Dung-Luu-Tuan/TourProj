package com.example.tour_project.controllers;

import com.example.tour_project.dao.TouristGroupDAO;
import com.example.tour_project.dao.TouristGroupDetailDAO;
import com.example.tour_project.models.DetailTourGroup;
import com.example.tour_project.models.Tour;
import com.example.tour_project.models.TouristGroup;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class TouristGroupDetailsController {
    @FXML
    Label labelTendoan;

    @FXML
    Label labelMadoan;

    @FXML
    Label labelMatour;

    @FXML
    private TextField hanhtrinhtf, khachsantf, diadiemtf;

    TouristGroup touristGroupSend;
    public void setView(TouristGroup touristgroup) {
        touristGroupSend = touristgroup;
        TouristGroup touristgroupDetail = TouristGroupDAO.getDetails(touristgroup.getMadoan());

        labelMadoan.setText(String.valueOf(touristgroupDetail.getMadoan()));
        labelMatour.setText(String.valueOf(touristgroupDetail.getMatour()));
        labelTendoan.setText(String.valueOf(touristgroupDetail.getTour().getTengoi()));

        hanhtrinhtf.setText(touristgroupDetail.getDetailTourGroup().getHanhtrinh());
        khachsantf.setText(touristgroupDetail.getDetailTourGroup().getKhachsan());
        diadiemtf.setText(touristgroupDetail.getDetailTourGroup().getDiadiemthamquan());
    }

    public void handleUpdateDetail(){
        DetailTourGroup detailTourGroup = new DetailTourGroup(touristGroupSend.getMadoan(),
                hanhtrinhtf.getText(), khachsantf.getText(), diadiemtf.getText());
        try{
            if(String.valueOf(touristGroupSend.getMadoan()) != "" && hanhtrinhtf.getText() != "" &&
                    khachsantf.getText() != "" &&
                    diadiemtf.getText() != ""){
                TouristGroupDetailDAO.update(detailTourGroup);
                Notifications.create()
                        .title("Thông báo")
                        .text("Dữ liệu đã được thay đổi")
                        .showWarning();
            } else {
                Notifications.create()
                        .title("Thông báo")
                        .text("Dữ liệu không được để trống")
                        .showWarning();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
