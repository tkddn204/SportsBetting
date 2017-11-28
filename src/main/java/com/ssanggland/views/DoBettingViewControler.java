package com.ssanggland.views;

import com.ssanggland.DatabaseDAO;
import com.ssanggland.models.PlayMatch;
import com.ssanggland.models.enumtypes.KindOfDividend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.getPlayMatch;

public class DoBettingViewControler implements Initializable{

    @FXML
    private Label matchLineUp;
    @FXML
    private Label betHomeText;
    @FXML
    private Label betDrawText;
    @FXML
    private Label betAwayText;

    private PlayMatch playMatch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void BetBtnAction1(ActionEvent actionEvent) {
        CreateCheckBettingScene(KindOfDividend.WIN);
    }

    public void setData(String lineUp) {
        matchLineUp.setText(lineUp);
    }

    public void BetBtnAction2(ActionEvent actionEvent) {
        CreateCheckBettingScene(KindOfDividend.DRAW);
    }


    public void BetBtnAction3(ActionEvent actionEvent) {
        CreateCheckBettingScene(KindOfDividend.LOSE);
    }

    protected void CreateCheckBettingScene(KindOfDividend kindOfDividend) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("CheckBetting.fxml"));
        try {
            fxmlLoader.load();
            CheckBettingControler controller = fxmlLoader.getController();
            controller.setDividend(playMatch.getDividendList().get(kindOfDividend.ordinal()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Parent parent = fxmlLoader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void setPlayMatch(long playMatchId) {
        playMatch = getPlayMatch(playMatchId);
        betHomeText.setText(
                String.format("%.2f", playMatch.getDividendList().get(0).getDividendRate()));
        betDrawText.setText(
                String.format("%.2f", playMatch.getDividendList().get(1).getDividendRate()));
        betAwayText.setText(
                String.format("%.2f", playMatch.getDividendList().get(2).getDividendRate()));
    }
}

