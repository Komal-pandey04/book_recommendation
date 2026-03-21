package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title label for Login page
        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // TextFields for user input
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        // Log In button
        Button loginButton = new Button("Log In");

        // Log In button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean isLoginSuccessful = DatabaseHelper.checkLogin(username, password);
            if (isLoginSuccessful) {
                System.out.println("Logged in successfully");
                
                // Close the current Login Window
                primaryStage.close();

                // Open the Main window (your main class)
                Main mainPage = new Main();
                Stage mainStage = new Stage();
                mainPage.start(mainStage);  // Open the main page
            } else {
                System.out.println("Invalid username or password");
                // Close the current Login Window
                primaryStage.close();

                // Open the Welcome page again
                WelcomePage welcomePage = new WelcomePage();
                Stage welcomeStage = new Stage();
                welcomePage.start(welcomeStage);
            }
        });

        // Layout for Login Page
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton);

        Scene loginScene = new Scene(loginLayout, 400, 300);
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
