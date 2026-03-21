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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenreFinder {

    // GUI display method
    public void display() {
        Stage genreStage = new Stage();
        genreStage.setTitle("Find Book by Genre");

        Label label = new Label("Enter Genre:");
        TextField genreField = new TextField();
        Button searchButton = new Button("Search");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

        searchButton.setOnAction(e -> {
            String genre = genreField.getText();
            String result = searchBooksByGenre(genre);
            resultArea.setText(result);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, genreField, searchButton, resultArea);

        Scene scene = new Scene(layout, 400, 400);
        genreStage.setScene(scene);
        genreStage.show();
    }

    // Backend method to search by genre
    public static String searchBooksByGenre(String genre) {
        StringBuilder result = new StringBuilder();

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/LENOVO/Desktop/BookRecommendation.db");

            String query = "SELECT * FROM books WHERE genre1 = ? OR genre2 = ? OR genre3 = ? OR genre4 = ? OR genre5 = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, genre);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("authorname");
                String genres = rs.getString("genre1") + ", " + rs.getString("genre2") + ", " + rs.getString("genre3")
                        + ", " + rs.getString("genre4") + ", " + rs.getString("genre5");
                String moods = rs.getString("mood1") + ", " + rs.getString("mood2") + ", " + rs.getString("mood3")
                        + ", " + rs.getString("mood4") + ", " + rs.getString("mood5");

                result.append("Book Name: ").append(name).append("\n")
                      .append("Author: ").append(author).append("\n")
                      .append("Genres: ").append(genres).append("\n")
                      .append("Moods: ").append(moods).append("\n\n");
            }

            if (result.length() == 0) {
                result.append("No books found for the genre: ").append(genre);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while searching for books by genre.";
        }

        return result.toString();
    }
}
