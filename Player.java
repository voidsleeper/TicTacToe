// === Player.java ===
import javax.swing.*;

public class Player {
    private String name;
    private ImageIcon profilePicture;
    private int wins;
    private boolean isAI;
    private String catchPhrase; // Add a fun catch phrase

    public Player(String name, ImageIcon profilePicture) {
        this.name = name;
        this.profilePicture = profilePicture;
        this.wins = 0;
        this.isAI = false;
        this.catchPhrase = getRandomCatchPhrase();
    }

    // Constructor for AI player (no profile picture)
    public Player(String name) {
        this.name = name;
        this.profilePicture = null;
        this.wins = 0;
        this.isAI = true;
        this.catchPhrase = getRandomAICatchPhrase();
    }

    public String getName() { return name; }
    public ImageIcon getProfilePicture() { return profilePicture; }
    public int getWins() { return wins; }
    public void incrementWins() { wins++; }
    public void resetWins() { wins = 0; }
    public boolean isAI() { return isAI; }
    public String getCatchPhrase() { return catchPhrase; }

    // Add some fun catch phrases for players
    private String getRandomCatchPhrase() {
        String[] phrases = {
            "Ready to win!", "Let's do this!", "Game on!", "Bring it!", "Victory is mine!"
        };
        return phrases[(int)(Math.random() * phrases.length)];
    }

    // Add some fun catch phrases for AI
    private String getRandomAICatchPhrase() {
        String[] phrases = {
            "Beep boop, I will win!", "You can't beat the machine!", "AI always wins!", "Processing victory...", "Prepare to lose!"
        };
        return phrases[(int)(Math.random() * phrases.length)];
    }
}