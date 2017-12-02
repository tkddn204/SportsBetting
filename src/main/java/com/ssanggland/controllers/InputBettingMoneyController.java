package com.ssanggland.controllers;

import com.ssanggland.models.Dividend;
import com.ssanggland.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.bettingMoney;
import static com.ssanggland.DatabaseDAO.getUser;

public class InputBettingMoneyController implements Initializable {

    @FXML
    private AnchorPane checkBettingPane;
    @FXML
    private TextField textfieldBet;
    @FXML
    private Button betCheckBtn;

    private Dividend clickedDividend;

    public void betCheckBtnClickEvent(ActionEvent actionEvent) {
        if(betCheckBtn.getText().toString().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("오류");
            alert.setHeaderText(null);
            alert.setContentText("배당금을 입력해주세요.");
        }

        try {
            boolean isBettingSuccess = bettingMoney(clickedDividend,
                    Long.parseLong(textfieldBet.getText()));
            if (isBettingSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("배팅 완료");
                alert.setHeaderText(null);
                alert.setContentText("배팅이 정상적으로 완료되었습니다.");
                updateMainWindow();
                alert.showAndWait();
                closeWindow();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("현금 부족");
                alert.setHeaderText(null);
                alert.setContentText("현재 가지고 계신 금액보다 높게 배당하실 수 없습니다.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("숫자가 아닙니다.");
            alert.setHeaderText(null);
            alert.setContentText("숫자만 입력해주세요!");
            alert.showAndWait();
        }
    }

    private void updateMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().
                    getResource("fxmls/MainController.fxml"));
            Parent parent = loader.load();
            Scene main_scene = new Scene(parent);
            Main.window.setScene(main_scene);
            Main.window.setTitle("스포츠 배팅");
            Main.window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void betCancelBtnClickEvent(ActionEvent actionEvent) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) checkBettingPane.getScene().getWindow();
        stage.hide();
    }

    public void setDividend(Dividend dividend) {
        this.clickedDividend = dividend;
    }
}
