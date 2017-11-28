package com.ssanggland.views;

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

public class DoBettingViewControler implements Initializable{

    @FXML
    private Label matchLineUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchLineUp.setPrefWidth(218.0);
        matchLineUp.setAlignment(Pos.CENTER);
    }

    public void BetBtnAction1(ActionEvent actionEvent) {
        CreateCheckBettingScene();
    }

    public void setData(String lineUp) {
        matchLineUp.setText(lineUp);
    }

    public void BetBtnAction2(ActionEvent actionEvent) {
        CreateCheckBettingScene();
    }


    public void BetBtnAction3(ActionEvent actionEvent) {
        CreateCheckBettingScene();
    }

    protected void CreateCheckBettingScene() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("CheckBetting.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
        }
        Stage stage = new Stage();
        Parent parent = fxmlLoader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}

