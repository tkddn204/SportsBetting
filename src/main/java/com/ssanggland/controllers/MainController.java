package com.ssanggland.controllers;

import com.ssanggland.DatabaseDAO;
import com.ssanggland.models.Betting;
import com.ssanggland.models.PlayMatch;
import com.ssanggland.models.User;
import com.ssanggland.controllers.Datas.BettingTableData;
import com.ssanggland.controllers.Datas.MatchTableData;
import com.ssanggland.models.enumtypes.BettingState;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.*;
import static com.ssanggland.Main.cal;

public class MainController implements Initializable {

    @FXML
    protected Text userName;
    @FXML
    protected Text userMoney;

    ObservableList<MatchTableData> matchObservableList;
    ObservableList<BettingTableData> bettingObservableList;

    @FXML
    private TableView<MatchTableData> matchTableView;

    @FXML
    private TableColumn<MatchTableData, Number> matchIdColumn;
    @FXML
    private TableColumn<MatchTableData, String> matchColumn;
    @FXML
    private TableColumn<MatchTableData, String> matchStateColumn;
    @FXML
    private TableColumn<MatchTableData, String> homeColumn;
    @FXML
    private TableColumn<MatchTableData, String> drawColumn;
    @FXML
    private TableColumn<MatchTableData, String> awayColumn;

    @FXML
    private TableView<BettingTableData> bettingTableView;

    @FXML
    private TableColumn<BettingTableData, Number> bettingIdColumn;
    @FXML
    private TableColumn<BettingTableData, String> bettingMatchColumn;
    @FXML
    private TableColumn<BettingTableData, String> bettingMatchStateColumn;
    @FXML
    private TableColumn<BettingTableData, String> bettingStateColumn;
    @FXML
    private TableColumn<BettingTableData, String> bettingMatchDateColumn;
    @FXML
    private TableColumn<BettingTableData, Number> bettingMoneyColumn;
    @FXML
    private TableColumn<BettingTableData, Number> expectMoneyColumn;
    @FXML
    private Text timeText;

    //DB 데이터 동기화 : 배열에다 데이터 넣으면 됨
//    private List<PlayMatch> playMatchList;
//    private List<Betting> bettingList;

    public void infoBtnAction(ActionEvent ae) {
        loadingMatchTable();
        changeTableView(true, false);
    }

    public void bettingBtnAction(ActionEvent ae) {
        loadingResultTable();
        changeTableView(false, true);
    }

    public void changeTableView(boolean match, boolean result) {
        matchTableView.setVisible(match);
        bettingTableView.setVisible(result);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // loadingProgress();
        setTimeText(cal.get(Calendar.YEAR) + "년 "
                + (cal.get(Calendar.MONTH) + 1) + "월 "
                + cal.get(Calendar.DATE) + "일");
                new Thread(() -> {
//            try {
//                Thread.sleep(500);
//                if (getPlayMatchList(cal).isEmpty()) {
//                    getRandomPlayMatchList(cal);
//                }
                updateUserInfo(getUser());
                loadingResultTable();
                loadingMatchTable();
//            }
//            } catch (InterruptedException ie) {
//                ie.printStackTrace();
//            }
            //loadingDialog.close();
        }).start();
        loadingOnClick();
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
        List<PlayMatch> playMatchList = getPlayMatchList(cal);

        matchObservableList = FXCollections.observableArrayList();
        for (PlayMatch playMatch : playMatchList) {
            matchObservableList.add((new MatchTableData(
                    new SimpleLongProperty(playMatch.getId()),
                    new SimpleStringProperty(playMatch.getHomeTeam().getName()
                            + " vs " + playMatch.getAwayTeam().getName()),
                    new SimpleStringProperty(playMatch.getState().getDescription()),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(0).getDividendRate())),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(1).getDividendRate())),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(2).getDividendRate())))));
        }
        matchIdColumn.setCellValueFactory(cellData -> cellData.getValue().matchIdProperty());
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        matchStateColumn.setCellValueFactory(cellData -> cellData.getValue().matchStateProperty());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());

        matchTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        matchTableView.setItems(matchObservableList);
    }

    public void loadingResultTable() {
        List<Betting> bettingList = getBettingList();

        bettingObservableList = FXCollections.observableArrayList();
        if (!bettingList.isEmpty()) {
            for (Betting betting : bettingList) {
                if(betting.getState().equals(BettingState.YET)) {
                    bettingObservableList.add((new BettingTableData(
                            new SimpleLongProperty(betting.getId()),
                            new SimpleStringProperty(betting.getDividend().getPlayMatch().getHomeTeam().getName()
                                    + " vs " + betting.getDividend().getPlayMatch().getAwayTeam().getName()),
                            new SimpleStringProperty(betting.getDividend().getPlayMatch().getState().getDescription()),
                            new SimpleStringProperty(new SimpleDateFormat("YYYY-MM-dd").format(
                                    betting.getDividend().getPlayMatch().getKickoffDate())),
                            new SimpleStringProperty(betting.getState().getDescription()),
                            new SimpleLongProperty(betting.getBettingMoney()),
                            new SimpleLongProperty((long) (betting.getBettingMoney()
                                    * betting.getDividend().getDividendRate())))));
                }
            }
        }
        bettingIdColumn.setCellValueFactory(cellData -> cellData.getValue().bettingIdProperty());
        bettingMatchColumn.setCellValueFactory(cellData -> cellData.getValue().bettingMatchProperty());
        bettingMatchStateColumn.setCellValueFactory(cellData -> cellData.getValue().resultMatchStateProperty());
        bettingMatchDateColumn.setCellValueFactory(cellData -> cellData.getValue().matchDateProperty());
        bettingStateColumn.setCellValueFactory(cellData -> cellData.getValue().bettingStateProperty());
        bettingMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().bettingMoneyProperty());
        expectMoneyColumn.setCellValueFactory(cellData -> cellData.getValue().bettingResultMoneyProperty());

        bettingTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        bettingTableView.setItems(bettingObservableList);
    }

    @FXML
    public void loadingOnClick() {

        matchTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (matchTableView.getSelectionModel().getSelectedItem()
                        .matchProperty().getValue().equals("")) {
                    return;
                }

                if (DatabaseDAO.isAlreadyBet(
                        matchTableView.getSelectionModel().getSelectedItem()
                                .matchIdProperty().getValue())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("오류");
                    alert.setHeaderText(null);
                    alert.setContentText("이미 투자한 경기입니다.");
                    alert.show();
                    return;
                }

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getClassLoader().
                        getResource("fxmls/DoBettingView.fxml"));
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
                                .matchProperty().getValue());

                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
            }
        });

        bettingTableView.setOnMouseClicked((event) -> {
                if (bettingTableView.getSelectionModel().getSelectedItem()
                        .bettingMatchProperty().getValue().equals(""))
                    return;
                // 결과 테이블 클릭했을 때
        });
    }

    @FXML
    public void chargeBtnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().
                getResource("fxmls/ChargeMoneyScene.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Alert.AlertType.INFORMATION.toString();
        }
//        ChargeMoneySceneController chargeMoneySceneController = fxmlLoader.getController();
        Parent parent = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void setTimeText(String tm) {
        timeText.setText(tm);
    }

    public void nextBtnAction(ActionEvent actionEvent) {
        updatePlayMatchState();
        makeRandomPlayMatchResult();
        refreshAndUpdateUserEarnMoney(getEarnMoneyBettingList());
        setTimeText(nextDate());
        nextSchedule();
    }

    private void refreshAndUpdateUserEarnMoney(List<Betting> earnMoneyBettingList) {
        long earnMoney = 0L;
        StringBuilder contentStringBuilder = new StringBuilder(" ");
//        if(!earnMoneyBettingList.isEmpty()) {
            for (Betting betting : earnMoneyBettingList) {
                long resultMoney = betting.getBettingResult().getResultMoney();
                earnMoney += resultMoney;
                contentStringBuilder.append(betting.getDividend().getPlayMatch().getHomeTeam().getName()
                        + " vs " + betting.getDividend().getPlayMatch().getAwayTeam().getName() + " -> "
                        + betting.getDividend().getPlayMatch().getPlayMatchResult().toString() + " = "
                        + resultMoney + "원\n");
                updateBettingResultIsPaid(betting, true);
            }
//        }

        DatabaseDAO.chargeMoney(earnMoney);
        updateUserInfo(getUser());

        if(earnMoney != 0L) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("축하합니다!");
            alert.setHeaderText("총 " + earnMoney + "원을 획득하셨습니다!!");
            alert.setContentText(contentStringBuilder.toString());
            alert.show();
        }
    }

    public static Stage resultWindow;

    public void resultBtnAction(ActionEvent actionEvent) {
        loadingResultWindow();
    }

    private void loadingResultWindow() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().
                getResource("fxmls/ResultView.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Alert.AlertType.INFORMATION.toString();
        }
        Parent parent = fxmlLoader.getRoot();
        if(resultWindow == null) {
            resultWindow = new Stage();
        }
        resultWindow.setScene(new Scene(parent));
        resultWindow.setTitle("배팅 결과");
        resultWindow.show();
    }

    private String nextDate() {
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.get(Calendar.YEAR) + "년 "
                + (cal.get(Calendar.MONTH) + 1) + "월 "
                + cal.get(Calendar.DATE) + "일";
    }

    private void nextSchedule() {
        loadingMatchTable();
        loadingResultTable();
        if(resultWindow != null && resultWindow.isShowing()) {
            loadingResultWindow();
        }
    }
}
