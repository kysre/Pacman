package controller;

import model.User;

public class ProfileController {
    public static boolean doesUserExist(String username) {
        return User.doesUserExist(username);
    }

    public static boolean isUserPassCorrect(String username, String password) {
        User user = User.getUserByUsername(username);
        return user.isPasswordCorrect(password);
    }

    public static void createUser(String username, String password) {
        new User(username, password);
    }

    public static void deleteUser(String username) {
        User.deleteUser(username);
    }

    public static void changePassword(String username, String newPassword) {
        User user = User.getUserByUsername(username);
        user.setPassword(newPassword);
    }
}
