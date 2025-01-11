package com.group13.Controllers.Cashier.MenuOperations;

import com.group13.Models.ConnectionModel;
import com.group13.Models.MovieModel;
import com.group13.Models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CashierSearchByGenreController {
   @FXML
   private TextField genreField;
   @FXML
   private TableView<MovieModel> moviesTable;
   @FXML
   private TableColumn<MovieModel, String> movieNameColumn;
   @FXML
   private TableColumn<MovieModel, String> genreColumn;
   @FXML
   private TableColumn<MovieModel, String> imagePathColumn;
   @FXML
   private TableColumn<MovieModel, ImageView> movieImageColumn;

   private final ObservableList<MovieModel> moviesList = FXCollections.observableArrayList();

   @FXML
   public void initialize() {
      movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("movieName"));
      genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
      movieImageColumn.setCellValueFactory(new PropertyValueFactory<>("imgPath"));

      moviesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


      loadAllMovies(); // Load all movies initially
      moviesTable.setItems(moviesList);
   }

   @FXML
   private void onSearchByGenre() {
      String genre = genreField.getText().trim();
      if (genre.isEmpty()) {
         loadAllMovies();
      } else {
         searchMoviesByGenre(genre);
      }
   }

   private void loadAllMovies() {
      moviesList.clear();
      try (Connection conn = ConnectionModel.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM movies")) {

         while (rs.next()) {
            MovieModel movie = new MovieModel();
            movie.setMovieName(rs.getString("movie_name"));
            movie.setGenre(rs.getString("genre"));
            movie.setImgPath(rs.getString("img_path"));
            moviesList.add(movie);
         }
      } catch (SQLException e) {
         System.err.println("Error while loading all movies: " + e.getMessage());
      }
   }

   private void searchMoviesByGenre(String genre) {
      moviesList.clear();
      try (Connection conn = ConnectionModel.getConnection();
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery("SELECT * FROM movies WHERE genre = '" + genre + "'")) {

         while (rs.next()) {
            MovieModel movie = new MovieModel();
            movie.setMovieName(rs.getString("movie_name"));
            movie.setGenre(rs.getString("genre"));
            movie.setImgPath(rs.getString("img_path"));
            moviesList.add(movie);
         }
      } catch (SQLException e) {
         System.err.println("Error while searching movies by genre: " + e.getMessage());
      }
   }
}
