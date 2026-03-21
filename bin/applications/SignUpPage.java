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

public class SignUpPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title label for Sign Up page
        Label titleLabel = new Label("Sign Up");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        // TextFields for user input
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter your phone number");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");

        // Sign Up button
        Button signUpButton = new Button("Sign Up");

        // Sign Up button action
        signUpButton.setOnAction(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (password.equals(confirmPassword)) {
                boolean isSuccess = DatabaseHelper.insertUser(name, username, phone, email, password);
                if (isSuccess) {
                    System.out.println("Signed up successfully");

                    // Close the signup window
                    primaryStage.close();

                    // Open Welcome window
                    Stage welcomeStage = new Stage();
                    welcomeStage.setTitle("Welcome");

                    Label welcomeLabel = new Label("Welcome, " + name + "!");
                    welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

                    VBox welcomeLayout = new VBox(20);
                    welcomeLayout.setPadding(new Insets(20));
                    welcomeLayout.setAlignment(Pos.CENTER);
                    welcomeLayout.getChildren().add(welcomeLabel);

                    Scene welcomeScene = new Scene(welcomeLayout, 400, 300);
                    welcomeStage.setScene(welcomeScene);
                    welcomeStage.show();

                } else {
                    System.out.println("Sign up failed");
                }
            } else {
                System.out.println("Passwords do not match!");
            }
        });

        // Layout for Sign Up Page
        VBox signUpLayout = new VBox(10);
        signUpLayout.setPadding(new Insets(20));
        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.getChildren().addAll(titleLabel, nameField, usernameField, emailField, phoneField, passwordField, confirmPasswordField, signUpButton);

        Scene signUpScene = new Scene(signUpLayout, 400, 500);
        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(signUpScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
