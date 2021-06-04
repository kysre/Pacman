package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//from  ww  w.  j a  va2  s  .  co m
public class Form extends Menu {

//    public Form(Stage stage) {
//        super(null, "Form");
//    }

    private GridPane createGridPane() {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);

        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);

        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label fNameLbl = new Label("First Name");
        TextField fNameFld = new TextField();

        Label lNameLbl = new Label("Last Name");
        TextField lNameFld = new TextField();

        Button saveButton = new Button("Save");

        GridPane.setHalignment(fNameLbl, HPos.RIGHT);
        GridPane.setHalignment(lNameLbl, HPos.RIGHT);

        GridPane.setHalignment(fNameFld, HPos.LEFT);
        GridPane.setHalignment(lNameFld, HPos.LEFT);

        GridPane.setHalignment(saveButton, HPos.RIGHT);

        gridpane.add(fNameLbl, 0, 0);
        gridpane.add(fNameFld, 1, 0);

        gridpane.add(lNameLbl, 0, 1);
        gridpane.add(lNameFld, 1, 1);

        gridpane.add(saveButton, 1, 2);

        return gridpane;

    }

    private FlowPane createTopBanner() {
        FlowPane topBanner = new FlowPane();
        topBanner.setPrefHeight(40);

        Font serif = Font.font("Dialog", 30);

        Text contactText = new Text("Contacts");
        contactText.setFill(Color.BLACK);
        contactText.setFont(serif);

        topBanner.getChildren().addAll(contactText);

        return topBanner;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 380, 150, Color.BLACK);

        GridPane gridpane = createGridPane();
        FlowPane topBanner = createTopBanner();

        root.setTop(topBanner);
        root.setCenter(gridpane);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}