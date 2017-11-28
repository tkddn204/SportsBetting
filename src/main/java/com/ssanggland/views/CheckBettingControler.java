package com.ssanggland.views;

import com.ssanggland.models.Dividend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.bettingMoney;

public class CheckBettingControler implements Initializable{

    @FXML
    private TextField textfieldBet;

    private Dividend clickedDividend;

    public void betCheckBtnClickEvent(ActionEvent actionEvent) {
        try {
            boolean isBettingSuccess = bettingMoney(clickedDividend,
                    Long.parseLong(textfieldBet.getText()));
            if(isBettingSuccess) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("배팅 완료");
                alert.setHeaderText(null);
                alert.setContentText("배팅이 정상적으로 완료되었습니다.");
                alert.show();
            } {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("현금 부족");
                alert.setHeaderText(null);
                alert.setContentText("돈이 충분하지 않습니다! 돈좀 가지고 다니세요");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            System.out.println(textfieldBet.getText());
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
        Stage stage = (Stage) textfieldBet.getScene().getWindow();
        stage.close();
    }

    public void setDividend(Dividend dividend) {
        this.clickedDividend = dividend;
    }
}
