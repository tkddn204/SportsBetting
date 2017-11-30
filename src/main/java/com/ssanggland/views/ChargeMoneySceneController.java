package com.ssanggland.views;

import com.ssanggland.DatabaseDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        try{
            int money = Integer.parseInt(chargeText.getText());
            boolean isChargedMoney = DatabaseDAO.chargeMoney(
                    LoginSession.getInstance().getSessionUserId(),
                    money);
            if(isChargedMoney) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setHeaderText(null);
                al.setContentText(money + "원이" + "\n충전 되었습니다.");
                al.show();
                updateUserMoney();
                closeWindow(actionEvent);
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setHeaderText(null);
                al.setContentText("오류로 돈이 충전되지 않았습니다!");
                al.showAndWait();
                closeWindow(actionEvent);
            }
        }
        catch (Exception e) {
            errorLabel.setText("숫자만 입력가능합니다.");
        }
    }

    private void updateUserMoney() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("sample.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR).show();
        }
        Controller controller = fxmlLoader.getController();
        controller.updateUserInfo(
                getUser(LoginSession.getInstance().getSessionUserId()));
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void chargeCancelBtn(ActionEvent actionEvent) {
        closeWindow(actionEvent);
    }
}
