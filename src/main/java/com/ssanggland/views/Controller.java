package com.ssanggland.views;

import com.ssanggland.models.Betting;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.*;

public class Controller implements Initializable {

    @FXML
    protected Label userName;
    @FXML
    protected Label userMoney;


    ObservableList<TableDataMatch> matchObservableList;
    ObservableList<BettingMatchData> resultObservableList;

    @FXML
    private TableView<TableDataMatch> matchTableView;

    @FXML
    private TableColumn<TableDataMatch, Number> matchIdColumn;
    @FXML
    private TableColumn<TableDataMatch, String> matchColumn;
    @FXML
    private TableColumn<TableDataMatch, String> homeColumn;
    @FXML
    private TableColumn<TableDataMatch, String> drawColumn;
    @FXML
    private TableColumn<TableDataMatch, String> awayColumn;


    @FXML
    private TableView<BettingMatchData> resultTableView;

    @FXML
    private TableColumn<BettingMatchData, Number> bettingIdColumn;
    @FXML
    private TableColumn<BettingMatchData, String> resultMatchColumn;
    @FXML
    private TableColumn<BettingMatchData, String> matchStateColumn;
    @FXML
    private TableColumn<BettingMatchData, String> battingStateColumn;
    @FXML
    private TableColumn<BettingMatchData, Number> getMoneyColumn;
    @FXML
    private TableColumn<BettingMatchData, Number> resultMoneyColumn;

    private User user;

    //DB 데이터 동기화 : 배열에다 데이터 넣으면 됨
    private List<PlayMatch> playMatchList;
    private List<Dividend> dividendList;

    private List<Betting> bettingList;

    public void infoBtnAction(ActionEvent ae) {
        loadingMatchTable();
        matchTableView.setVisible(true);
        resultTableView.setVisible(false);
    }

    public void resultBtnAction(ActionEvent ae) {
        loadingResultTable();
        matchTableView.setVisible(false);
        resultTableView.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // loadingProgress();
         new Thread(() -> {
             try {
                 Thread.sleep(500);
                 loadingInformation();
             } catch (InterruptedException ie) {
                 ie.printStackTrace();
             }
            //loadingDialog.close();
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

    public void updateUserInfo(User user) {
        userName.setText(user.getName());
        userMoney.setText(String.format("%d원", user.getMoney()));
    }

    public void loadingMatchTable() {
        playMatchList = getRandomPlayMatchList();
        dividendList = getRandomDividendList();

        matchObservableList = FXCollections.observableArrayList();
        for (PlayMatch playMatch : playMatchList) {
            matchObservableList.add((new TableDataMatch(
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
        matchIdColumn.setCellValueFactory(cellData -> cellData.getValue().matchIdProperty());
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());

        matchTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        matchTableView.setItems(matchObservableList);
    }

    public void loadingResultTable() {
        bettingList = getBettingList();
        resultObservableList = FXCollections.observableArrayList();
        if(!bettingList.isEmpty()) {
            for (Betting betting : bettingList) {
                resultObservableList.add((new BettingMatchData(
                        new SimpleLongProperty(betting.getId()),
                        new SimpleStringProperty(betting.getDividend().getPlayMatch().getHomeTeam().getName()
                                + " vs " + betting.getDividend().getPlayMatch().getAwayTeam().getName()),
                        new SimpleStringProperty(betting.getDividend().getPlayMatch().getState().getDescription()),
                        new SimpleStringProperty(betting.getState().getDescription()),
                        new SimpleLongProperty(betting.getBettingMoney()),
                        new SimpleLongProperty((long) (betting.getBettingMoney()
                                * betting.getDividend().getDividendRate())))));
            }
        }
        bettingIdColumn.setCellValueFactory(cellData -> cellData.getValue().bettingIdProperty());
        resultMatchColumn.setCellValueFactory(cellData -> cellData.getValue().bettingMatchProperty());
        matchStateColumn.setCellValueFactory(cellData -> cellData.getValue().matchStateProperty());
        battingStateColumn.setCellValueFactory(cellData -> cellData.getValue().bettingStateProperty());
        getMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().bettingMoneyProperty());
        resultMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().bettingResultMoneyProperty());

        resultTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        resultTableView.setItems(resultObservableList);
    }

    public void loadingInformation() {
        if(getLeagueCount() <= 0) {
            loadLeagueTeamSQL(getClass().getClassLoader()
                    .getResource("leagueTeamList.sql").getPath());
        }
        loadingResultTable();
        loadingMatchTable();

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

                DoBettingViewController doBettingViewController = fxmlLoader.getController();
                doBettingViewController.setPlayMatch(
                        matchTableView.getSelectionModel().getSelectedItem()
                                .matchIdProperty().getValue());
                doBettingViewController.setData(
                        matchTableView.getSelectionModel().getSelectedItem()
                                .matchProperty().getValue().toString());

                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
            }
        });

        resultTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(resultTableView.getSelectionModel().getSelectedItem()
                        .bettingMatchProperty().getValue().toString().equals(""))
                    return;
                // TODO : 결과 테이블 클릭했을 때
            }
        });
    }
}

