package view;

import controller.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileMenu extends Menu {

    private static String username;
    private static Menu parentMenu;
    private static Menu exitMenu;
    private static Stage stage;

    public TextField oldPasswordTextBox;
    public TextField newPasswordTextBox;
    public TextField reNewPasswordTextBox;
    public Button changePasswordButton;
    public Button deleteUser;
    public Button backButton;

    @Override
    public void start(Stage stage) throws IOException {
        ProfileMenu.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("ProfileMenu.fxml"));
        Scene scene = new Scene(root, 1080, 720);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("style/style.css")));
        stage.setScene(scene);
        stage.setTitle("Pacman");
        stage.show();
    }


    public void setParentMenu(Menu parentMenu) {
        ProfileMenu.parentMenu = parentMenu;
    }

    public void setExitMenu(Menu exitMenu) {
        ProfileMenu.exitMenu = exitMenu;
    }

    public void setUsername(String username) {
        ProfileMenu.username = username;
    }

    public void changePassword(MouseEvent mouseEvent) {
        String oldPassword = oldPasswordTextBox.getText();
        String newPassword = newPasswordTextBox.getText();
        String reNewPassword = reNewPasswordTextBox.getText();

        if (isPasswordFormatCorrect(newPassword)) {
            if (!newPassword.equals(reNewPassword)) {
                ShowOutput.showOutput("Error", "The entered new passwords don't match!");
            } else if (!ProfileController.isUserPassCorrect(username, oldPassword)) {
                ShowOutput.showOutput("Error", "The entered old password is incorrect!");
            } else {
                ProfileController.changePassword(username, newPassword);
                ShowOutput.showOutput("Change Password", "Password changed successfully.");
            }
        }

    }

    public void deleteUser(MouseEvent mouseEvent) throws Exception {
        boolean isSure = GetInput.getYesNoAnswer("Delete User", "Do you want to delete your user?");
        if (isSure) {
            ProfileController.deleteUser(username);
            ShowOutput.showOutput("Delete User", "User deleted successfully.");
            exitMenu.start(stage);
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        parentMenu.start(stage);
    }

    private boolean isPasswordFormatCorrect(String password) {
        if (password.matches("\\W") || password.equals("")) {
            ShowOutput.showOutput("Error", "The entered password's format is incorrect!");
            return false;
        }
        return true;
    }
}
