package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private boolean sidebarVisible = false; // Sidebar visibility flag

    @Override
    public void start(Stage primaryStage) {
        // Label at the top
        Label titleLabel = new Label("Daydream Library");
        titleLabel.setStyle("-fx-font-size: 46px; -fx-font-weight: bold;");
        

        // Buttons for finding books
        Button nameButton = new Button("Name");
        Button genreButton = new Button("Genre");
        Button moodButton = new Button("Mood");
        Button authorButton = new Button("Author");

        nameButton.setPrefSize(150, 50);
        genreButton.setPrefSize(150, 50);
        moodButton.setPrefSize(150, 50);
        authorButton.setPrefSize(150, 50);

        nameButton.setOnAction(e -> System.out.println("Name search clicked"));
        genreButton.setOnAction(e -> System.out.println("Genre search clicked"));
        moodButton.setOnAction(e -> System.out.println("Mood search clicked"));
        authorButton.setOnAction(e -> System.out.println("Author search clicked"));

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(20);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.add(nameButton, 0, 0);
        buttonGrid.add(genreButton, 1, 0);
        buttonGrid.add(moodButton, 0, 1);
        buttonGrid.add(authorButton, 1, 1);

        VBox centerBox = new VBox(50);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(titleLabel, buttonGrid);

        // Sidebar VBox (ALWAYS visible — we attach/detach it)
     // Sidebar VBox (ALWAYS visible — we attach/detach it)
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.getStyleClass().add("sidebar"); // Add the 'sidebar' style class

        Button recommendButton = new Button("Recommend Book");
        Button settingsButton = new Button("Settings");
        Button logoutButton = new Button("Log Out");
        Button contactButton = new Button("Contact Us");

        recommendButton.setMaxWidth(Double.MAX_VALUE);
        settingsButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        contactButton.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(recommendButton,  logoutButton, contactButton);


        // Top HBox with Menu Button
     // Top HBox with Menu Button
        Button menuButton = new Button("Menu");
        menuButton.setPrefSize(130, 30);  // Increase the button size to ensure text fits
        menuButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");


        HBox topBox = new HBox();
        topBox.setAlignment(Pos.TOP_LEFT);
        topBox.setPadding(new Insets(10));
        topBox.getChildren().add(menuButton);

        // BorderPane Layout
        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(centerBox);
        // DO NOT add sidebar yet

        // Menu button action
        menuButton.setOnAction(e -> {
            if (sidebarVisible) {
                root.setLeft(null); // Remove sidebar
                sidebarVisible = false;
            } else {
                root.setLeft(sidebar); // Show sidebar
                sidebarVisible = true;
            }
        });
        nameButton.setOnAction(e -> {
            NameFinder nameFinder = new NameFinder();
            nameFinder.display();
        });
        authorButton.setOnAction(e -> {
            AuthorFinder authorFinder = new AuthorFinder();
            authorFinder.display();
        });
        moodButton.setOnAction(e -> {
            MoodFinder moodFinder = new MoodFinder();
            moodFinder.display();
        });
        logoutButton.setOnAction(e -> {
            WelcomePage welcomePage = new WelcomePage();
            Stage welcomeStage = new Stage();
            welcomePage.start(welcomeStage);
            primaryStage.close();
        });
        /*contactButton.setOnAction(e -> {
            ContactUs contactUsPage = new ContactUs();
            Stage contactStage = new Stage();
            contactUsPage.start(contactStage);
        });*/



        // Recommend button action to open the Recommend class
        recommendButton.setOnAction(e -> {
            Recommend.start(primaryStage); // Call the Recommend class
        });
        genreButton.setOnAction(e -> {
            GenreFinder genreFinder = new GenreFinder();
            genreFinder.display();
        });


  

        // Make sure the BorderPane resizes to fill the entire scene
        root.prefWidthProperty().bind(primaryStage.widthProperty());
        root.prefHeightProperty().bind(primaryStage.heightProperty());

        Scene mainScene = new Scene(root, 600, 450);
        mainScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle("Book Recommendation System");
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
