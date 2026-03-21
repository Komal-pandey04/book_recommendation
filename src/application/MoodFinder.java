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

public class MoodFinder {

    // GUI display method
    public void display() {
        Stage moodStage = new Stage();
        moodStage.setTitle("Find Book by Mood");

        Label label = new Label("Enter Mood:");
        TextField moodField = new TextField();
        Button searchButton = new Button("Search");

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);

        searchButton.setOnAction(e -> {
            String mood = moodField.getText();
            String result = searchBooksByMood(mood);
            resultArea.setText(result);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, moodField, searchButton, resultArea);

        Scene scene = new Scene(layout, 400, 400);
        moodStage.setScene(scene);
        moodStage.show();
    }

    // Backend method to search by mood
    public static String searchBooksByMood(String mood) {
        StringBuilder result = new StringBuilder();

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/Users/LENOVO/Desktop/BookRecommendation.db");

            String query = "SELECT * FROM books WHERE mood1 = ? OR mood2 = ? OR mood3 = ? OR mood4 = ? OR mood5 = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, mood);
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
                result.append("No books found for the mood: ").append(mood);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while searching for books by mood.";
        }

        return result.toString();
    }
}
