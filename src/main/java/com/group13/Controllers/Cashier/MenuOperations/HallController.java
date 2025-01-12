package com.group13.Controllers.Cashier.MenuOperations;

import com.group13.Models.Model;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class HallController {
    @FXML
    private GridPane seatGrid;

    @FXML
    private Label hallLabel;

    @FXML
    private Button nextStageButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        createSeatLayout(2);

    }

    private void createSeatLayout(int hallNo) {

        if(hallNo == 1) {
            hallLabel.setText("Hall A Seat Selection");
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 8; col++) {
                    Button seatButton = new Button();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BED);
                    icon.getStyleClass().add("menu-icon");
                    seatButton.setGraphic(icon);
                    seatButton.getStyleClass().add("seat-button");
                    seatButton.setOnAction(event -> toggleSeatSelection(seatButton));
                    seatGrid.add(seatButton, col, row);
                }
            }
        } else {
            hallLabel.setText("Hall B Seat Selection");
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 8; col++) {
                    Button seatButton = new Button();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BED);
                    icon.getStyleClass().add("menu-icon");
                    seatButton.setGraphic(icon);
                    seatButton.getStyleClass().add("seat-button");
                    seatButton.setOnAction(event -> toggleSeatSelection(seatButton));
                    seatGrid.add(seatButton, col, row);
                }
            }
        }
    }

    private void toggleSeatSelection(Button seatButton) {
        if (seatButton.getStyleClass().contains("selected")) {
            seatButton.getStyleClass().remove("selected");
        } else {
            seatButton.getStyleClass().add("selected");
        }
    }
}
