package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Recommend {

    public static void start(Stage primaryStage) {
        // Label for the top (Recommendations)
        Label recommendationsLabel = new Label("Recommendations");
        recommendationsLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // TextFields for entering book details
        TextField nameField = new TextField();
        nameField.setPromptText("Enter book name");
        
        TextField authorField = new TextField();
        authorField.setPromptText("Enter author name");

        TextField genre1Field = new TextField();
        genre1Field.setPromptText("Enter genre 1");
        
        TextField genre2Field = new TextField();
        genre2Field.setPromptText("Enter genre 2");
        
        TextField genre3Field = new TextField();
        genre3Field.setPromptText("Enter genre 3");
        
        TextField genre4Field = new TextField();
        genre4Field.setPromptText("Enter genre 4");
        
        TextField genre5Field = new TextField();
        genre5Field.setPromptText("Enter genre 5");

        TextField mood1Field = new TextField();
        mood1Field.setPromptText("Enter mood 1");

        TextField mood2Field = new TextField();
        mood2Field.setPromptText("Enter mood 2");

        TextField mood3Field = new TextField();
        mood3Field.setPromptText("Enter mood 3");

        TextField mood4Field = new TextField();
        mood4Field.setPromptText("Enter mood 4");

        TextField mood5Field = new TextField();
        mood5Field.setPromptText("Enter mood 5");

        // Label to display result
        Label resultLabel = new Label();

        // Button to recommend a new book
        Button recommendButton = new Button("Recommend Book");

        // Button action to add book to the database
        recommendButton.setOnAction(e -> {
            // Get values from text fields
            String name = nameField.getText();
            String author = authorField.getText();
            String genre1 = genre1Field.getText();
            String genre2 = genre2Field.getText();
            String genre3 = genre3Field.getText();
            String genre4 = genre4Field.getText();
            String genre5 = genre5Field.getText();
            String mood1 = mood1Field.getText();
            String mood2 = mood2Field.getText();
            String mood3 = mood3Field.getText();
            String mood4 = mood4Field.getText();
            String mood5 = mood5Field.getText();

            // Call Recommend to add the book to the database
            String result = Recommend.addBookToDatabase(name, author, genre1, genre2, genre3, genre4, genre5,
                                                        mood1, mood2, mood3, mood4, mood5);
            resultLabel.setText(result);  // Display the result in the label
        });

        // Layout for Recommend page
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(recommendationsLabel, nameField, authorField, genre1Field, genre2Field, genre3Field, genre4Field, genre5Field,
                                    mood1Field, mood2Field, mood3Field, mood4Field, mood5Field, recommendButton, resultLabel);

        // Set the window size to be larger (600x600)
        Scene recommendScene = new Scene(layout, 600, 600);
        recommendScene.getStylesheets().add(Recommend.class.getResource("/application/rec.css").toExternalForm());

        primaryStage.setScene(recommendScene);

        primaryStage.setTitle("Recommendations");
        primaryStage.setScene(recommendScene);
        primaryStage.show();
    }

    // Method to add a book to the database (already defined in the previous step)
    public static String addBookToDatabase(String name, String authorname, String genre1, String genre2, String genre3,
                                           String genre4, String genre5, String mood1, String mood2, String mood3,
                                           String mood4, String mood5) {
        String result = "Book recommendation failed.";
        
        try {
            // Establish connection to the SQLite database
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/LENOVO/Desktop/BookRecommendation.db");

            // SQL query to insert a new book into the books table
            String query = "INSERT INTO books (name, authorname, genre1, genre2, genre3, genre4, genre5, "
                    + "mood1, mood2, mood3, mood4, mood5) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, authorname);
            pstmt.setString(3, genre1);
            pstmt.setString(4, genre2);
            pstmt.setString(5, genre3);
            pstmt.setString(6, genre4);
            pstmt.setString(7, genre5);
            pstmt.setString(8, mood1);
            pstmt.setString(9, mood2);
            pstmt.setString(10, mood3);
            pstmt.setString(11, mood4);
            pstmt.setString(12, mood5);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                result = "Book successfully added to the database!";
            }

            // Close the resources
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = "An error occurred while adding the book.";
        }
        
        return result;
    }
}
