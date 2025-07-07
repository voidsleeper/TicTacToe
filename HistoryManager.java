import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;

public class HistoryManager {

    private static final String FILE_NAME = "game_history.txt";

    // Save game history for the player (support multiple players)
    public static void saveHistory(String player1, int wins1, String player2, int wins2) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(player1 + " - Wins: " + wins1 + " | " + player2 + " - Wins: " + wins2 + " - " + LocalDateTime.now());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Overload for single player
    public static void saveHistory(String playerName, int wins) {
        saveHistory(playerName, wins, "Computer", 0);
    }

    // Automatically save history for two player games
    public static void autoSaveIfTwoPlayer(String mode, String player1, int wins1, String player2, int wins2) {
        if (mode != null && mode.contains("2 Player")) {
            saveHistory(player1, wins1, player2, wins2);
        }
    }

    // View game history in a scrollable dialog
    public static void viewHistory() {
        StringBuilder history = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n");
            }
        } catch (IOException e) {
            history.append("No history found.");
        }

        JTextArea textArea = new JTextArea(history.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Game History", JOptionPane.INFORMATION_MESSAGE);
    }

    // Clear all history from the file
    public static void clearHistory() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.print(""); // Overwrite file with empty content
            JOptionPane.showMessageDialog(null, "History cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
