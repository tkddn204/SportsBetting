package com.ssanggland.views;

import com.ssanggland.models.Dividend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.bettingMoney;
import static com.ssanggland.DatabaseDAO.getUser;

public class CheckBettingController implements Initializable {

    @FXML
    private AnchorPane checkBettingPane;
    @FXML
    private TextField textfieldBet;
    @FXML
    private Button betCheckBtn;

    private Dividend clickedDividend;

    public void betCheckBtnClickEvent(ActionEvent actionEvent) {
        try {
            if(betCheckBtn.getText().toString().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("금액 빈칸");
                alert.setHeaderText(null);
                alert.setContentText("배당금을 입력해주세요.");
            }
            boolean isBettingSuccess = bettingMoney(clickedDividend,
                    Long.parseLong(textfieldBet.getText()));
            if (isBettingSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("배팅 완료");
                alert.setHeaderText(null);
                alert.setContentText("배팅이 정상적으로 완료되었습니다.");
                alert.setOnHiding(event -> {
                    updateMainWindow();
                    closeWindow();
                });
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("현금 부족");
                alert.setHeaderText(null);
                alert.setContentText("현재 가지고 계신 금액보다 높게 배당하실 수 없습니다.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            System.out.println(textfieldBet.getText());
        }
    }

    private void updateMainWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR).show();
        }
        Controller controller = fxmlLoader.getController();
        controller.updateUserInfo(
                getUser(LoginSession.getInstance().getSessionUserId()));
        controller.loadingMatchTable();
        controller.loadingResultTable();
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
