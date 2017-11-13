package com.ssanggland.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //creating label ID
        Text text_id = new Text("ID");

        //creating label password
        Text text_pwd = new Text("Password");

        //creating text field ID
        TextField textfield_ID = new TextField();

        //creating password field password
        PasswordField passwordfield_pwd = new PasswordField();

        //creating button login
        Button button_login = new Button("Login");

        //creating button exit
        Button button_exit = new Button("Exit");

        //creating button sign up
        Button button_signup = new Button("Sign up");

        //creating GridPane
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(400, 200);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Setting the Grid Alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(text_id, 0, 0);
        gridPane.add(textfield_ID, 1, 0);
        gridPane.add(text_pwd, 0, 1);
        gridPane.add(passwordfield_pwd, 1, 1);
        gridPane.add(button_signup, 0, 2);
        gridPane.add(button_login, 1, 2);
        // gridPane.add(button_exit,2,2);

        //Styling nodes
        //button_exit.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_login.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_signup.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        //Creating a scene object
        Scene scene = new Scene(gridPane);

        //Setting title to the Stage
        stage.setTitle("Login");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
}
