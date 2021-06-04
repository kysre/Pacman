module pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;

    opens view to javafx.fxml;
    exports view;
}