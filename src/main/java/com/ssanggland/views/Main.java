package com.ssanggland.views;

import com.ssanggland.models.User;
import com.ssanggland.util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main extends Application {

    Stage window;
    Scene login_scene, registration_scene, main_scene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent roots = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        main_scene = new Scene(roots);

        // Text Setting
        Text text = new Text("19세 이상만 사용 가능한 프로그램입니다.");
        // Setting Font Size
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC,20));
        // Setting Text Position
        text.setX(50);
        text.setY(150);
        text.setFill(Color.BLACK);
        /*text.setStrokeWidth(2);
        text.setStroke(javafx.scene.paint.Color.RED);*/

        //creating label ID
        Text text_id = new Text("ID");
        Text text_registration_id = new Text("ID");

        //creating label password
        Text text_pwd = new Text("Password");
        Text text_registration_pwd = new Text("Password");

        //creating label name
        Text text_name = new Text("Name");

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
        Button button_login = new Button("Login");

        //creating button exit
        Button button_exit = new Button("Exit");

        //creating button sign up
        Button button_signup = new Button("Sign up");

        //creating button sign up in registration scene
        Button button_registration_signup = new Button ("Sign up(@)");

        /* ********************This is main scene componant****************************/
        //User Information
       // Text text_userid = new Text(user_id);
        //Text text_username = new Text(user_name);

        /*  ************************************************************************* */
        //creating GridPane
        GridPane gridPane = new GridPane();
        GridPane registration_gridPane = new GridPane();

        //creating MainScene(AnchorPane)
        AnchorPane main_anchorPane = new AnchorPane();

        //Setting size for the pane
        gridPane.setMinSize(400, 200);
        registration_gridPane.setMinSize(400,300);

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
        gridPane.add(text_error_login,3,0);
        gridPane.add(text_id, 2, 1);
        gridPane.add(textfield_ID, 3, 1);
        gridPane.add(text_pwd, 2, 2);
        gridPane.add(passwordfield_pwd, 3, 2);
        gridPane.add(button_signup, 2, 3);
        gridPane.add(button_login, 3, 3);

        //Arranging all the nodes in the registration grid
        registration_gridPane.add(text_registration_id,0,0);
        registration_gridPane.add(textfield_registration_id,1,0);
        registration_gridPane.add(text_registration_pwd,0,1);
        registration_gridPane.add(passwordfield_registration_pwd,1,1);
        registration_gridPane.add(text_name,0,2);
        registration_gridPane.add(textField_name,1,2);
        registration_gridPane.add(text_error,1,3);
        registration_gridPane.add(button_registration_signup,1,4);

        //Styling nodes
        //button_exit.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        gridPane.setStyle("-fx-background-color: yellow;");
        registration_gridPane.setStyle("-fx-background-color: yellow;");
        button_login.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_signup.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        button_registration_signup.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        text_error.setStyle("-fx-text-fill: red;");

        //Creating a scene object
        login_scene = new Scene(gridPane);
        registration_scene = new Scene(registration_gridPane);

        //Setting Event
        text.setOnMouseClicked(e->{
            window.setScene(login_scene);
            window.setTitle("Login");
        });

        button_signup.setOnAction(e->{
            window.setScene(registration_scene);
            window.setTitle("Registration");
        });

        button_login.setOnAction(e-> {
            if(textfield_ID.getText().equals("") | passwordfield_pwd.getText().equals("")) {
                text_error_login.setText("Error !");
            } else if (!validUserCheck(textfield_ID.getText(), passwordfield_pwd.getText())){
                textfield_ID.setText("");
                passwordfield_pwd.setText("");
                text_error_login.setText("Error !");
            } else {
                window.setScene(main_scene);
                window.setTitle("Give me Money");
            }
        });

        button_registration_signup.setOnAction(e->{
            if(textfield_registration_id.getText().equals("")
                    || passwordfield_registration_pwd.getText().equals("")
                    || textField_name.getText().equals("")
                    || textfield_registration_id.getLength()>15)
                text_error.setText("Error !");
            else {
                if(registerCommit(textfield_registration_id.getText(),
                        passwordfield_registration_pwd.getText(),
                        textField_name.getText())) {
                    text_error.setText("");
                    window.setScene(login_scene);
                    window.setTitle("Login");
                } else {
                    text_error.setText("이미 존재하는 아이디입니다!");
                }
                textfield_registration_id.setText("");
                passwordfield_registration_pwd.setText("");
                textField_name.setText("");
            }
        });

        text.setUnderline(true);

        Group root = new Group(text);
        Scene scene = new Scene(root,630, 300);

        // Setting Title Name
        primaryStage.setTitle("SSangleland");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Session session;
    private boolean registerCommit(String userId, String userPassword, String userName) {
        if (validUserCheck(userId, userPassword)) {
            return false;
        }
        Transaction transaction = session.beginTransaction();
        User user = new User(userId, userPassword, userName);
        session.save(user);
        transaction.commit();
        return true;
    }

    private boolean validUserCheck(String user_id, String password_id) {
        // 쿼리 보내서 id랑 일치하는거 하나만 받음
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User u where u.loginId = ?");
        query.setParameter(0, user_id);
        User user = (User)query.uniqueResult();
        transaction.commit();
        if(user == null || !user.getPassword().equals(password_id)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}
