package com.ssanggland.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteListViewController implements Initializable{

    public void setDlvResultLabel(String d) {
        dlvResultLabel.setText(d);
    }

    public void setBetSucLabel(String d) {
        betSucLabel.setText("배팅 결과 : " + d);
    }

    public void setBetMoneyLabel(String d) {
        betMoneyLabel.setText("배당금 : "+ d);
    }

    @FXML
    private Label dlvResultLabel;
    @FXML
    private Label betSucLabel;
    @FXML
    private Label betMoneyLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void deleteListBtnAction(ActionEvent actionEvent) {
    }

    public void cancelListBtnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) dlvResultLabel.getScene().getWindow();
        stage.close();
    }
}
