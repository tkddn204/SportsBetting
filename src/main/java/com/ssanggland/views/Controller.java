package com.ssanggland.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
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

    ObservableList<TableDataMatch> matchList = FXCollections.observableArrayList(
            (new TableDataMatch(new SimpleStringProperty("ManU vs ManCity"),
                    new SimpleStringProperty("2.23"), new SimpleStringProperty("2.01"), new SimpleStringProperty("1.88"))),
            (new TableDataMatch(new SimpleStringProperty("Watford vs Brighton"),
                    new SimpleStringProperty("1.97"), new SimpleStringProperty("2.51"), new SimpleStringProperty("4.76"))),
            (new TableDataMatch(new SimpleStringProperty("정영길 vs 심철수"),
                    new SimpleStringProperty("13.85"), new SimpleStringProperty("4.11"), new SimpleStringProperty("1.25")))
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());
        matchTableView.setItems(matchList);
    }
}
