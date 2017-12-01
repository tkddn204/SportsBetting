package com.ssanggland.controllers;

import com.ssanggland.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.chargeMoney;
import static com.ssanggland.DatabaseDAO.getUser;

public class ChargeMoneySceneController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField chargeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText("");
    }

    public void moneyChargeBtn(ActionEvent actionEvent) {
        if(chargeText.getText().toString().equals("")) {
            errorLabel.setText("충전할 금액을 입력해주세요.");
            return;
        }

        try{
            long money = Long.parseLong(chargeText.getText());
            Alert al;
            if(chargeMoney(money)) {
                updateUserMoney();
                al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText(null);
                al.setContentText(money + "원이" + "\n충전 되었습니다.");
                al.show();
            } else {
                al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText(null);
                al.setContentText("오류로 돈이 충전되지 않았습니다!");
                al.showAndWait();
            }
            closeWindow(actionEvent);
        }
        catch (NumberFormatException e) {
            errorLabel.setText("숫자만 입력가능합니다.");
            e.printStackTrace();
        }
    }

    private void updateUserMoney() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().
                    getResource("fxmls/MainController.fxml"));
            Parent parent = loader.load();
            MainController mainController = loader.getController();
//            mainController.updateUserInfo(getUser());

            Scene main_scene = new Scene(parent);
            Main.window.setScene(main_scene);
            Main.window.setTitle("스포츠 배팅");
            Main.window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void chargeCancelBtn(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }
}
