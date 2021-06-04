package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;
import model.game.GameBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseController {
    // users ArrayList is written to users.json and static gameBoards in staticGameBoards.json
    public ArrayList<User> importUsers() {
        File file = new File("src/main/resources/controller/database/users.json");
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException ignored) {
        }
        String json = fileScanner.nextLine();

        Gson gson = new Gson();
        return gson.fromJson(json,
                new TypeToken<ArrayList<User>>() {
                }.getType());
    }

    public void exportUsers() {
        ArrayList<User> users = User.getAllUsers();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        try {
            FileWriter writer = new FileWriter("src/main/resources/controller/database/users.json");
            writer.write(json);
            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();

        }
    }

    public ArrayList<GameBoard> getStaticGameBoards() {
        File file = new File("src/main/resources/controller/database/staticGameBoards.json");
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException ignored) {
        }
        String json = fileScanner.nextLine();

        Gson gson = new Gson();
        return gson.fromJson(json,
                new TypeToken<ArrayList<GameBoard>>() {
                }.getType());
    }
}
