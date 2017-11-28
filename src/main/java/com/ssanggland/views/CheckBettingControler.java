package com.ssanggland.views;

import com.ssanggland.models.Dividend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
                    Integer.parseInt(textfieldBet.getText()));
            if(isBettingSuccess) {
                // TODO: 배당성공(배당금이 가진 돈보다 적을 경우)
            } {
                // TODO: 배당실패(배당금이 가진 돈보다 많을 경우)
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
        Stage stage = (Stage) textfieldBet.getScene().getWindow();
        stage.close();
    }

    public void setDividend(Dividend dividend) {
        this.clickedDividend = dividend;
    }
}
