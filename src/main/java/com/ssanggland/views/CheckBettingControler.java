package com.ssanggland.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckBettingControler implements Initializable{

    @FXML
    private TextField textfieldBet;

    public void betCheckBtnClickEvent(ActionEvent actionEvent) {
        try {
            Integer.parseInt(textfieldBet.getText());
            /**textfieldBet.getText() 배팅 성공시 현재 금액에서 - 해서 DB저장
             *
             * BettingProcess();
             *
             */

        } catch (NumberFormatException e) {
            System.out.println(textfieldBet.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    @FXML
    public void betCancelBtnClickEvent(ActionEvent actionEvent) {
        Stage stage = (Stage) textfieldBet.getScene().getWindow();
        stage.close();
    }
   /**
    private void BettingProcess{

    }
    */
}
