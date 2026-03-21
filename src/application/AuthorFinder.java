package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AuthorFinder {

    public void display() {
        Stage window = new Stage();
        window.setTitle("Find Book by Author");

        Label headingLabel = new Label("Find a Book by Author");
        headingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField authorInput = new TextField();
        authorInput.setPromptText("Enter Author Name");

        Button searchButton = new Button("Search");

        Label resultLabel = new Label();

        searchButton.setOnAction(e -> {
            String authorName = authorInput.getText();
            if (!authorName.isEmpty()) {
                try {
                    String result = searchBookByAuthor(authorName);
                    resultLabel.setText(result);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                    resultLabel.setText("Error: Unable to connect to the database.");
                }
            } else {
                resultLabel.setText("Please enter an author name.");
            }
        });

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(headingLabel, authorInput, searchButton, resultLabel);

        Scene scene = new Scene(layout, 500, 400);
        window.setScene(scene);
        window.show();
    }

    // Backend method to search a book by author name
    public static String searchBookByAuthor(String authorName) throws ClassNotFoundException, SQLException {
        // Ensure the SQLite JDBC driver is loaded
        Class.forName("org.sqlite.JDBC");

        String result = "No book found by this author.";

        // Establish connection to the SQLite database
        Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/LENOVO/Desktop/BookRecommendation.db");

        String query = "SELECT * FROM books WHERE authorname = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, authorName);

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

        return result;
    }
}
