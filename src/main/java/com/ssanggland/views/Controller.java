package com.ssanggland.views;

import com.ssanggland.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TableView<TableDataMatch> matchTableView;
    @FXML
    private TableColumn<TableDataMatch, String> matchColumn;
    @FXML
    private TableColumn<TableDataMatch, String> homeColumn;
    @FXML
    private TableColumn<TableDataMatch, String> drawColumn;
    @FXML
    private TableColumn<TableDataMatch, String> awayColumn;
    @FXML
    protected Label userName;
    @FXML
    protected Label userMoney;

    User user;

    //DB 데이터 동기화 : 배열에다 데이터 넣으면 됨
    private String[] homeTeam  = {"KOR", "COR"};
    private String[] awayTeam = {"JPN", "SER"};
    private String[] homeDividend = {"1.89","2.22"};
    private String[] awayDividend = {"1.76","1.58"};
    private String[] drawDividend = {"2.66","2.34"};
    private String[] bettingMatchId;

    public void infoBtnAction(ActionEvent ae) {
        matchList = FXCollections.observableArrayList(
                (new TableDataMatch(new SimpleStringProperty(homeTeam[0] + " vs " + awayTeam[0]),
                        new SimpleStringProperty(homeDividend[0]), new SimpleStringProperty(drawDividend[0]), new SimpleStringProperty(awayDividend[0])))
        );
        for (int i = 1; i < homeTeam.length; i++) {
            matchList.add((new TableDataMatch(new SimpleStringProperty(homeTeam[i] + " vs " + awayTeam[i]),
                    new SimpleStringProperty(homeDividend[i]), new SimpleStringProperty(drawDividend[i]), new SimpleStringProperty(awayDividend[i]))));
        }
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());
        matchTableView.setItems(matchList);

        matchTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(matchTableView.getSelectionModel().getSelectedItem().matchProperty().getValue().toString().equals(""))
                    return;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("DoBettingView.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    Alert.AlertType.INFORMATION.toString();
                }
                DoBettingViewControler doBettingViewControler = fxmlLoader.getController();
                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
                doBettingViewControler.setData(matchTableView.getSelectionModel().getSelectedItem().matchProperty().getValue().toString());
            }
        });
    }



    public void resultBtnAction(ActionEvent ae) {
        matchTableView.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                System.out.println(matchTableView.getSelectionModel().getSelectedItem());
            }
        });
    }

    ObservableList<TableDataMatch> matchList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        // matchColumn.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());

        matchTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        matchTableView.setItems(matchList);
    }

    public void setUser(User user) {
        this.user = user;
        userName.setText(user.getName());
        userMoney.setText(Long.toString(user.getMoney()));
    }
}

