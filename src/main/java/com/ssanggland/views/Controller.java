package com.ssanggland.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    protected Label userId;
    @FXML
    protected Label userMoney;

    public void setUserId(String userId) {
        this.userId.setText(userId);
    }

    public void setUserMoney(String userMoney) {
        this.userMoney.setText(userMoney);
    }

    //DB 데이터 동기화 : 배열에다 데이터 넣으면 됨
    private String[] homeTeam;
    private String[] awayTeam;
    private String[] homeDividend;
    private String[] awayDividend;
    private String[] drawDividend;
    private String[] bettingMatchId;
    //

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
        // matchColumn.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());
        matchTableView.setItems(matchList);
    }

    public void resultBtnAction(ActionEvent ae) {
        //bettingMatchId = matchId
    }

    ObservableList<TableDataMatch> matchList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        // matchColumn.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());
        matchTableView.setItems(matchList);
    }
}

/*class MyEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent t) {
        TableColumn c = (TableColumn) t.getSource();
        int index = c.get

    }
}*/
