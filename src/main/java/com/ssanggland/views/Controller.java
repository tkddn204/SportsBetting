package com.ssanggland.views;

import com.ssanggland.DatabaseDAO;
import com.ssanggland.models.Dividend;
import com.ssanggland.models.PlayMatch;
import com.ssanggland.models.Team;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import static com.ssanggland.DatabaseDAO.getRandomDividendList;
import static com.ssanggland.DatabaseDAO.getRandomPlayMatchList;

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
    private List<PlayMatch> playMatchList;
    private List<Dividend> dividendList;
//    private ArrayList<String> homeTeam;
//    private ArrayList<String> awayTeam;

    public void infoBtnAction(ActionEvent ae) {
        playMatchList = getRandomPlayMatchList();
        dividendList = getRandomDividendList();

        matchList = FXCollections.observableArrayList();
        for (PlayMatch playMatch : playMatchList) {
            matchList.add((new TableDataMatch(
                    new SimpleStringProperty(playMatch.getHomeTeam().getName()
                            + " vs " + playMatch.getAwayTeam().getName()),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(0).getDividendRate())),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(1).getDividendRate())),
                    new SimpleStringProperty(String.format("%.2f",
                            playMatch.getDividendList().get(2).getDividendRate())))));
        }
        matchColumn.setCellValueFactory(cellData -> cellData.getValue().matchProperty());
        homeColumn.setCellValueFactory(cellData -> cellData.getValue().home_dividendProperty());
        drawColumn.setCellValueFactory(cellData -> cellData.getValue().draw_dividendProperty());
        awayColumn.setCellValueFactory(cellData -> cellData.getValue().away_dividendProperty());
        matchTableView.setItems(matchList);

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
                Parent parent = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
                doBettingViewControler.setData(
                        matchTableView.getSelectionModel().getSelectedItem()
                                .matchProperty().getValue().toString());
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

    public void loadingInformation() {
        // TODO: 엑셀파일에서 추출
    }
}

