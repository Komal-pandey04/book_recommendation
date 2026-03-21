package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NameFinder {

    public void display() {
        Stage window = new Stage();
        window.setTitle("Find Book by Name");

        Label headingLabel = new Label("Find a Book by Name");
        headingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter Book Name");

        Button searchButton = new Button("Search");

        Label resultLabel = new Label();

        searchButton.setOnAction(e -> {
            String bookName = nameInput.getText();
            if (!bookName.isEmpty()) {
                String result = searchBookByName(bookName);
                resultLabel.setText(result);
            } else {
                resultLabel.setText("Please enter a book name.");
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(headingLabel, nameInput, searchButton, resultLabel);

        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    // Backend method to search a book by its name
    public static String searchBookByName(String bookName) {
        String result = "Book not found";  // Default message if not found

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/LENOVO/Desktop/BookRecommendation.db");

            String query = "SELECT * FROM books WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, bookName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("authorname");
                String genres = rs.getString("genre1") + ", " + rs.getString("genre2") + ", " + rs.getString("genre3")
                        + ", " + rs.getString("genre4") + ", " + rs.getString("genre5");
                String moods = rs.getString("mood1") + ", " + rs.getString("mood2") + ", " + rs.getString("mood3")
                        + ", " + rs.getString("mood4") + ", " + rs.getString("mood5");

                result = "Book Name: " + name + "\n" +
                         "Author: " + author + "\n" +
                         "Genres: " + genres + "\n" +
                         "Moods: " + moods;
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = "An error occurred while searching for the book.";
        }

        return result;
    }
}
