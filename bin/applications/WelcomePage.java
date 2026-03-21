package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class WelcomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load images
        Image loginImage = new Image(getClass().getResourceAsStream("/Login.jpg"));
        Image signUpImage = new Image(getClass().getResourceAsStream("/Signup.jpg"));

        // Create ImageViews for resizing
        ImageView loginImageView = new ImageView(loginImage);
        loginImageView.setFitWidth(150); // Set width
        loginImageView.setFitHeight(50); // Set height

        ImageView signUpImageView = new ImageView(signUpImage);
        signUpImageView.setFitWidth(150); // Set width
        signUpImageView.setFitHeight(50); // Set height

        // Create buttons with images
        Button loginButton = new Button();
        loginButton.setGraphic(loginImageView);
        loginButton.setStyle("-fx-padding: 10; -fx-pref-width: 200; -fx-pref-height: 60;");
        
        Button signUpButton = new Button();
        signUpButton.setGraphic(signUpImageView);
        signUpButton.setStyle("-fx-padding: 10; -fx-pref-width: 200; -fx-pref-height: 60;");

        // Action for Login button
        loginButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage();
            Stage loginStage = new Stage();
            loginPage.start(loginStage);
            primaryStage.close();
        });

        // Action for Sign Up button
        signUpButton.setOnAction(e -> {
            SignUpPage signUpPage = new SignUpPage();
            Stage signUpStage = new Stage();
            signUpPage.start(signUpStage);
            primaryStage.close();
        });

        // Layout for Welcome Page
        VBox welcomeLayout = new VBox(15); // Adjust spacing between buttons
        welcomeLayout.setAlignment(Pos.CENTER); // Center the buttons
        welcomeLayout.getChildren().addAll(loginButton, signUpButton);

        // Set the scene and show the stage
        Scene welcomeScene = new Scene(welcomeLayout, 300, 200);
        primaryStage.setHeight(600);
        primaryStage.setWidth(500);
        primaryStage.setTitle("Welcome to Book Recommendation System");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
