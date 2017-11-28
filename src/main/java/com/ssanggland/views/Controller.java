package com.ssanggland.views;

import com.ssanggland.DatabaseDAO;
import com.ssanggland.models.Dividend;
import com.ssanggland.models.PlayMatch;
import com.ssanggland.models.User;
import javafx.beans.property.SimpleLongProperty;
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
import java.util.List;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.getLeagueCount;
import static com.ssanggland.DatabaseDAO.getRandomDividendList;
import static com.ssanggland.DatabaseDAO.getRandomPlayMatchList;

public class Controller implements Initializable {

    ObservableList<TableDataMatch> matchList;

    @FXML
    private TableView<TableDataMatch> matchTableView;

    @FXML
    private TableColumn<TableDataMatch, Number> idColumn;
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
    private List<PlayMatch> playMatchList;
    private List<Dividend> dividendList;

    public void infoBtnAction(ActionEvent ae) {
        // TODO: REFRESH
    }

    private void setTableCellValues() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        // matchColumn.addEventFilter(MouseEvent.MOUSE_CLICKED, new MyEventHandler());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());

        matchTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        matchTableView.setItems(matchList);
    }

    public void resultBtnAction(ActionEvent ae) {
        matchTableView.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                System.out.println(matchTableView.getSelectionModel().getSelectedItem());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playMatchList = getRandomPlayMatchList();
        dividendList = getRandomDividendList();

        matchList = FXCollections.observableArrayList();
        for (PlayMatch playMatch : playMatchList) {
            matchList.add((new TableDataMatch(
                    new SimpleLongProperty(playMatch.getId()),
                    new SimpleStringProperty(playMatch.getHomeTeam().getName()
                            + " vs " + playMatch.getAwayTeam().getName()),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(0).getDividendRate())),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(1).getDividendRate())),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(2).getDividendRate())))));
        }
        setTableCellValues();

        matchTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(matchTableView.getSelectionModel().getSelectedItem()
                        .matchProperty().getValue().toString().equals(""))
                    return;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("DoBettingView.fxml"));
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    Alert.AlertType.INFORMATION.toString();
                }

                DoBettingViewControler doBettingViewControler = fxmlLoader.getController();
                doBettingViewControler.setPlayMatch(
                        matchTableView.getSelectionModel().getSelectedItem()
                        .idProperty().getValue());
                doBettingViewControler.setData(
                        matchTableView.getSelectionModel().getSelectedItem()
                                .matchProperty().getValue().toString());

                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
        userName.setText(user.getName());
        userMoney.setText(Long.toString(user.getMoney()));
    }

    public void loadingInformation() {
        if(getLeagueCount() < 0) {
            DatabaseDAO.loadLeagueTeamSQL();
        }
    }
}

