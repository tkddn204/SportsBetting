package com.ssanggland.controllers;

import com.ssanggland.controllers.Datas.ResultTableData;
import com.ssanggland.models.Betting;
import com.ssanggland.models.Dividend;
import com.ssanggland.models.PlayMatch;
import com.ssanggland.models.Team;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.getResultBettingList;

public class ResultViewController implements Initializable {

    ObservableList<ResultTableData> resultObservableList;

    @FXML
    private TableView<ResultTableData> resultTableView;

    @FXML
    private TableColumn<ResultTableData, Number> resultIdColumn;
    @FXML
    private TableColumn<ResultTableData, String> resultMatchColumn;
    @FXML
    private TableColumn<ResultTableData, String> resultMatchDateColumn;
    @FXML
    private TableColumn<ResultTableData, String> resultMatchVerseColumn;
    @FXML
    private TableColumn<ResultTableData, String> resultBettingStateColumn;
    @FXML
    private TableColumn<ResultTableData, Number> resultMoneyColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingResultTable();
    }

    private void loadingResultTable() {
        List<Betting> bettingList = getResultBettingList();

        resultObservableList = FXCollections.observableArrayList();
        if (bettingList.isEmpty()) {
            return;
        }
        for (Betting betting : bettingList) {
            Dividend dividend = betting.getDividend();
            PlayMatch playMatch = dividend.getPlayMatch();
            Team homeTeam = playMatch.getHomeTeam();
            Team awayTeam = playMatch.getAwayTeam();

            resultObservableList.add(new ResultTableData(
                    new SimpleLongProperty(betting.getId()),
                    new SimpleStringProperty(homeTeam.getName() + " vs " + awayTeam.getName()),
                    new SimpleStringProperty(new SimpleDateFormat("YYYY-MM-dd")
                            .format(playMatch.getKickoffDate())),
                    new SimpleStringProperty(playMatch.getPlayMatchResult().toString()),
                    new SimpleStringProperty(betting.getState().getDescription()),
                    new SimpleLongProperty(betting.getBettingResult().getResultMoney())));
        }
        resultIdColumn.setCellValueFactory(cellData -> cellData.getValue().resultIdColumnProperty());
        resultMatchColumn.setCellValueFactory(cellData -> cellData.getValue().resultMatchColumnProperty());
        resultMatchDateColumn.setCellValueFactory(cellData -> cellData.getValue().resultMatchDateColumnProperty());
        resultMatchVerseColumn.setCellValueFactory(cellData -> cellData.getValue().resultMatchVerseColumnProperty());
        resultBettingStateColumn.setCellValueFactory(cellData -> cellData.getValue().resultBettingStateColumnProperty());
        resultMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().resultMoneyColumnProperty());

        resultTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        resultTableView.setItems(resultObservableList);
    }
}
