package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class Scoreboard {
    public static LinkedHashMap<String, Integer> getScoreboardLinkedMap() {

        LinkedHashMap<String, Integer> usernameHighScore = new LinkedHashMap<>();
        ArrayList<User> users = User.getAllUsers();

        for (User user : users) {
            String username = user.getUsername();
            Integer highScore = user.getHighScore();
            usernameHighScore.put(username, highScore);
        }

        return sort(usernameHighScore);
    }

    private static LinkedHashMap<String, Integer> sort(LinkedHashMap<String, Integer> usernameHighScore) {
        ArrayList<String> keySetList = new ArrayList<>();
        keySetList.addAll(usernameHighScore.keySet());

        for (int i = 0; i < keySetList.size() - 1; i++) {
            for (int j = 0; j < keySetList.size() - 1; j++) {
                String username1 = keySetList.get(j);
                Integer highScore1 = usernameHighScore.get(username1);
                String username2 = keySetList.get(j + 1);
                Integer highScore2 = usernameHighScore.get(username2);

                if (!isOrderCorrect(username1, highScore1, username2, highScore2)) {
                    Collections.swap(keySetList, j, j + 1);
                }
            }
        }

        LinkedHashMap<String, Integer> sortedUsernameHighScore = new LinkedHashMap<>();
        for (String key : keySetList) {
            sortedUsernameHighScore.put(key, usernameHighScore.get(key));
        }

        return sortedUsernameHighScore;
    }

    private static boolean isOrderCorrect(String username1, Integer highScore1, String username2, Integer highScore2) {
        if (highScore1 > highScore2) return true;
        if (highScore2 > highScore1) return false;

        LocalDateTime highScore1DateTime = User.getUserByUsername(username1).getHighScoreDateTime();
        LocalDateTime highScore2DateTime = User.getUserByUsername(username2).getHighScoreDateTime();
        if (highScore1DateTime != null && highScore2DateTime != null) {
            if (highScore1DateTime.isBefore(highScore2DateTime)) return true;
            if (highScore2DateTime.isBefore(highScore1DateTime)) return false;
        }

        int usernameOrder = username1.compareTo(username2);
        return usernameOrder < 0;
    }
}
