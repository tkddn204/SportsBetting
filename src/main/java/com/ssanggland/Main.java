package com.ssanggland;

import com.ssanggland.controllers.MainController;
import com.ssanggland.models.User;
import com.ssanggland.util.LoginSession;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Calendar;

import static com.ssanggland.DatabaseDAO.*;

public class Main extends Application {

    public static Stage window;
    public static Calendar cal = Calendar.getInstance();
    Scene login_scene, registration_scene;


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        //creating label ID
        Text text_id = new Text("아이디");
        Text text_registration_id = new Text("아이디");

        //creating label password
        Text text_pwd = new Text("비밀번호");
        Text text_registration_pwd = new Text("비밀번호");

        //creating label name
        Text text_name = new Text("닉네임");

        //creating label registration error
        Text text_error = new Text();
        Text text_error_login = new Text();

        //creating text field ID
        TextField textfield_ID = new TextField();
        TextField textfield_registration_id = new TextField();

        //creating password field password
        PasswordField passwordfield_pwd = new PasswordField();
        PasswordField passwordfield_registration_pwd = new PasswordField();

        //creating textfield name
        TextField textField_name = new TextField();

        //creating button login
        Button button_login = new Button("로그인");

        //creating button exit
        Button button_exit = new Button("종료");

        //creating button sign up
        Button button_signup = new Button("회원가입");

        //creating button sign up in registration scene
        Button button_registration_signup = new Button("가입하기");
        Button buttonRegistrationBack = new Button("뒤로가기");

        /* ********************This is main scene componant****************************/
        //User Information
        // Text text_userid = new Text(user_id);
        //Text text_username = new Text(user_name);

        /*  ************************************************************************* */
        //creating GridPane
        GridPane gridPane = new GridPane();
        GridPane registration_gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(600, 400);
        registration_gridPane.setMinSize(600, 400);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        registration_gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        registration_gridPane.setVgap(5);
        registration_gridPane.setHgap(5);

        //Setting the Grid Alignment
        gridPane.setAlignment(Pos.CENTER);
        registration_gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(text_id, 0, 1);
        gridPane.add(textfield_ID, 1, 1);
        gridPane.add(text_pwd, 0, 2);
        gridPane.add(passwordfield_pwd, 1, 2);
        gridPane.add(text_error_login, 0, 3);
        gridPane.add(button_signup, 0, 4);
        gridPane.add(button_login, 1, 4);

        //Arranging all the nodes in the registration grid
        registration_gridPane.add(text_name, 0, 0);
        registration_gridPane.add(textField_name, 1, 0);
        registration_gridPane.add(text_registration_id, 0, 1);
        registration_gridPane.add(textfield_registration_id, 1, 1);
        registration_gridPane.add(text_registration_pwd, 0, 2);
        registration_gridPane.add(passwordfield_registration_pwd, 1, 2);
        registration_gridPane.add(text_error, 1, 3);
        registration_gridPane.add(button_registration_signup, 0, 4);
        registration_gridPane.add(buttonRegistrationBack, 1, 4);

        //Styling nodes
        //button_exit.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_login.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_signup.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_registration_signup.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        buttonRegistrationBack.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        text_error.setStyle("-fx-text-fill: red;");

        //Creating a scene object
        login_scene = new Scene(gridPane);
        registration_scene = new Scene(registration_gridPane);

        button_signup.setOnAction(e -> {
            window.setScene(registration_scene);
            window.setTitle("회원가입");
        });

        button_login.setOnAction(e -> {
            if (textfield_ID.getText().equals("") || passwordfield_pwd.getText().equals("")) {
                text_error_login.setText("Error !");
            }

            if (loginCheck(textfield_ID.getText(), passwordfield_pwd.getText())) {
                User user = getUser(textfield_ID.getText());
                initUser(user);
            } else {
                text_error_login.setText("Error !");
            }
        });

        button_registration_signup.setOnAction(e -> {
            if (textfield_registration_id.getText().equals("")
                    || passwordfield_registration_pwd.getText().equals("")
                    || textField_name.getText().equals("")
                    || textfield_registration_id.getLength() > 15) {
                text_error.setText("Error !");
            }

            if (registerIdCheck(textfield_registration_id.getText())) {
                text_error.setText("Error !");
            } else {
                registerCommit(textfield_registration_id.getText(),
                        passwordfield_registration_pwd.getText(),
                        textField_name.getText());
                text_error.setText("");
                textfield_registration_id.setText("");
                passwordfield_registration_pwd.setText("");
                textField_name.setText("");
                window.setScene(login_scene);
                window.setTitle("스포츠 배팅 로그인");
            }
        });

        buttonRegistrationBack.setOnAction(e -> {
            text_error.setText("");
            textfield_registration_id.setText("");
            passwordfield_registration_pwd.setText("");
            textField_name.setText("");
            window.setScene(login_scene);
            window.setTitle("스포츠 배팅 로그인");
        });

        if (getLeagueCount() < 1L) {
            loadLeagueTeamSQL(getClass().getClassLoader().
                    getResource("leagueTeamList.sql").getPath());
            Calendar calendar = Calendar.getInstance();
            if (getPlayMatchList(calendar).isEmpty()) {
                getRandomPlayMatchList(calendar);
            }
        }

        // Setting Title Name
        primaryStage.setTitle("스포츠 배팅 로그인");
        primaryStage.setScene(login_scene);
        primaryStage.show();
    }

    @FXML
    public void initUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().
                    getResource("fxmls/MainController.fxml"));
            Parent parent = loader.load();
            MainController mainController = loader.getController();
            mainController.updateUserInfo(user);
            LoginSession.getInstance().setSessionUserId(user.getId());
            cal = Calendar.getInstance();

            Scene main_scene = new Scene(parent);
            window.setScene(main_scene);
            window.setTitle("스포츠 배팅");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        appOpenSession();
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        appStopSessions();
    }
}
