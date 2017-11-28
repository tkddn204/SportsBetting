package com.ssanggland.views;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.*;

public class Controller implements Initializable {

    ObservableList<TableDataMatch> matchList;
    ObservableList<BattingMatchData> resultView;

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

    @FXML
    private TableView<BattingMatchData> resultTableView;
    @FXML
    private TableColumn<BattingMatchData, String> resultMatchColumn;
    @FXML
    private TableColumn<BattingMatchData, String> matchTimeColumn;
    @FXML
    private TableColumn<BattingMatchData, String> battingResultColumn;
    @FXML
    private TableColumn<BattingMatchData, String> getMoneyColumn;

    User user;

    //DB 데이터 동기화 : 배열에다 데이터 넣으면 됨
    private List<PlayMatch> playMatchList;
    private List<Dividend> dividendList;

    private String[] battingHomeTeam = {"KOR"};
    private String[] battingAwayTeam = {"영길"};
    private String[] matchState = {"경기중"};
    private String[] matchCheck = {"적중"};
    private String[] bettingMoney = {"1540000"};

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
        matchTableView.setVisible(true);
        resultTableView.setVisible(false);
    }

    private void setResultTableCellValues() {
        resultMatchColumn.setCellValueFactory(cellData -> cellData.getValue().battingResultProperty());
        matchTimeColumn.setCellValueFactory(cellData -> cellData.getValue().matchStateProperty());
        battingResultColumn.setCellValueFactory(cellData -> cellData.getValue().battingResultProperty());
        getMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().battingMoneyProperty());
        resultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultTableView.setItems(resultView);
    }

    public void resultBtnAction(ActionEvent ae) {
        resultView = FXCollections.observableArrayList(
                (new BattingMatchData(new SimpleStringProperty(battingHomeTeam[0] + " vs " + battingAwayTeam[0]),
                        new SimpleStringProperty(matchState[0]), new SimpleStringProperty(matchCheck[0]), new SimpleStringProperty(bettingMoney[0])))
        );
        for (int i = 1; i < battingHomeTeam.length; i++) {
            resultView.add((new BattingMatchData(new SimpleStringProperty(battingHomeTeam[i] + " vs " + battingAwayTeam[i]),
                    new SimpleStringProperty(matchState[i]), new SimpleStringProperty(matchCheck[i]), new SimpleStringProperty(bettingMoney[i]))));
        }
        resultMatchColumn.setCellValueFactory(cellData -> cellData.getValue().battingMatchProperty());
        matchTimeColumn.setCellValueFactory(cellData -> cellData.getValue().matchStateProperty());
        battingResultColumn.setCellValueFactory(cellData -> cellData.getValue().battingResultProperty());
        getMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().battingMoneyProperty());
        resultTableView.setItems(resultView);
        matchTableView.setVisible(false);
        resultTableView.setVisible(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingProgress();
        new Thread(() -> {
            loadingInformation();
            loadingDialog.close();
        }).start();
    }

    private Dialog<Void> loadingDialog = new Dialog<>();
    private void loadingProgress() {
        loadingDialog.initModality(Modality.WINDOW_MODAL);
//        loadingDialog.initOwner();
        loadingDialog.initStyle(StageStyle.TRANSPARENT);

        Label loader = new Label("LOADING");
        loader.setContentDisplay(ContentDisplay.BOTTOM);
        loader.setGraphic(new ProgressIndicator());
        loadingDialog.getDialogPane().setGraphic(loader);

        DropShadow ds = new DropShadow();
        ds.setOffsetX(0.0);
        ds.setOffsetY(0.0);
        ds.setColor(Color.DARKGRAY);

        loadingDialog.getDialogPane().setEffect(ds);
        loadingDialog.show();
    }

    public void setUser(User user) {
        this.user = user;
        userName.setText(user.getName());
        userMoney.setText(Long.toString(user.getMoney()));
    }

    public void loadingInformation() {
        if(getLeagueCount() <= 0) {
            loadLeagueTeamSQL(getClass().getClassLoader()
                    .getResource("leagueTeamList.sql").getPath());
        }
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
        setResultTableCellValues();

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
}

