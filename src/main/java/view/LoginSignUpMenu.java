package view;

import controller.ProfileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSignUpMenu extends Menu {

    private static Menu parentMenu;
    private static Stage stage;
    @FXML
    public Button loginButton;
    public Button backButton;
    public Button signUpButton;
    public TextField usernameTextBox;
    public TextField passwordTextBox;


    @Override
    public void start(Stage stage) throws IOException {
        LoginSignUpMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("LoginSignUpMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));
        stage.setScene(scene);
        stage.show();
    }

    public void setParentMenu(Menu parentMenu) {
        LoginSignUpMenu.parentMenu = parentMenu;
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();
        loginHandler(username, password);
    }

    public void signUp(MouseEvent mouseEvent) {
        String username = usernameTextBox.getText();
        String password = passwordTextBox.getText();
        signUpHandler(username, password);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        parentMenu.start(stage);
    }

    private void loginHandler(String username, String password) throws IOException {
        if (isUserPassFormatCorrect(username, password)) {
            if (!ProfileController.doesUserExist(username)) {
                ShowOutput.showOutput("Error", "User with this username doesn't exist!");
            } else if (!ProfileController.isUserPassCorrect(username, password)) {
                ShowOutput.showOutput("Error", "The entered password is incorrect!");
            } else {
                ShowOutput.showOutput("Success", "Logged in successfully " + username + "!");
                MainMenu mainMenu = new MainMenu();
                mainMenu.setParentMenu(this);
                mainMenu.setUsername(username);
                mainMenu.start(stage);
            }
        }
    }

    private void signUpHandler(String username, String password) {
        if (isUserPassFormatCorrect(username, password)) {
            if (ProfileController.doesUserExist(username)) {
                ShowOutput.showOutput("Error", "User with this username already exists!");
            } else {
                ShowOutput.showOutput("Success", "User created successfully!");
                ProfileController.createUser(username, password);
            }
        }
    }

    private boolean isUserPassFormatCorrect(String username, String password) {
        if (username.matches("\\W") || username.equals("")) {
            ShowOutput.showOutput("Error", "The entered username's format is incorrect!");
            return false;
        } else if (password.matches("\\W") || password.equals("")) {
            ShowOutput.showOutput("Error", "The entered password's format is incorrect!");
            return false;
        }
        return true;
    }
}
