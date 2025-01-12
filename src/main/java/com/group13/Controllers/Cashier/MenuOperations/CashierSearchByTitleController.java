package com.group13.Controllers.Cashier.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.Model;
import com.group13.Models.MovieModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CashierSearchByTitleController {
   @FXML
   private TextField titleField;
   @FXML
   private TableView<MovieModel> moviesTable;
   @FXML
   private TableColumn<MovieModel, String> movieNameColumn;
   @FXML
   private TableColumn<MovieModel, String> genreColumn;
   @FXML
   private TableColumn<MovieModel, ImageView> movieImageColumn;
   @FXML
   private TableColumn<MovieModel, String> summaryColumn;
   @FXML
   private Button nextStageButton;

   private final ObservableList<MovieModel> moviesList = FXCollections.observableArrayList();

   @FXML
   public void initialize() {
      movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
      genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
      movieImageColumn.setCellValueFactory(new PropertyValueFactory<>("movieImage"));
      summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));

      moviesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

      nextStageButton.setDisable(true);

      loadAllMovies();

      moviesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         boolean isItemSelected = newValue != null;
         nextStageButton.setDisable(!isItemSelected);
         Model.getInstance().setMovieModel(newValue);
      });

      moviesTable.setItems(moviesList);
      nextStageButton.setOnAction(event -> Model.getInstance().getViewManager().getCashierSelectedMenuItem().set("Second Stage"));
   }

   @FXML
   private void onSearchByGenre() {
      String title = titleField.getText().trim();
      if (title.isEmpty()) {
         loadAllMovies();
      } else {
         searchMoviesByTitle(title);
      }
   }

   private void loadAllMovies() {
      ObservableList<MovieModel> movies = FXCollections.observableArrayList();

      String query = "SELECT * FROM movies ORDER BY movie_name ASC";

      try (Connection conn = ConnectionModel.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(query)) {

         while (rs.next()) {
            movies.add(new MovieModel(
                    rs.getString("movie_name"),
                    rs.getString("img_path"),
                    rs.getString("genre"),
                    rs.getString("summary")
            ));
         }

         moviesTable.setItems(movies);

      } catch (SQLException e) {
         System.err.println("Error while loading all movies: " + e.getMessage());
      }
   }

   private void searchMoviesByTitle(String movieName) {
      ObservableList<MovieModel> movies = FXCollections.observableArrayList();
      try (Connection conn = ConnectionModel.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM movies WHERE movie_name LIKE '%" + movieName + "%'")) {

         while (rs.next()) {
            movies.add(new MovieModel(
                    rs.getString("movie_name"),
                    rs.getString("img_path"),
                    rs.getString("genre"),
                    rs.getString("summary")
            ));
         }

         moviesTable.setItems(movies);

      } catch (SQLException e) {
         System.err.println("Error while searching movies by title: " + e.getMessage());
      }
   }
}