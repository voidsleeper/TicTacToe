import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private Player player1, player2, currentPlayer;
    private String mode;
    private JLabel scoreLabel1, scoreLabel2;
    private JLabel nameLabel1, nameLabel2;
    private int moves;
    private Random rand = new Random();
    private int gamesPlayed = 0;
    private final int SERIES_WINS = 4; // Best of 7
    private boolean seriesOver = false;
    private JButton restartButton;

    // Single player constructor
    public GameBoard(Player player, String mode) {
        this(player, mode.contains("2 Player") ? null : new Player("Computer"), mode);
    }

    // Two player constructor
    public GameBoard(Player player1, Player player2, String mode) {
        this.player1 = player1;
        this.player2 = player2 != null ? player2 : new Player("Computer");
        this.mode = mode;
        this.currentPlayer = player1;
        initUI();
    }

    private void initUI() {
        setTitle("Tic Tac Toe - Best of 7");
        setLayout(new BorderLayout());
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 48);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(font);
                btn.setFocusPainted(false);
                int row = i, col = j;
                btn.addActionListener(e -> handleMove(row, col));
                buttons[i][j] = btn;
                boardPanel.add(btn);
            }
        }

        JPanel infoPanel = new JPanel(new GridLayout(3, 2));
        // Profile + name for player 1 (X)
        JPanel player1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        if (player1.getProfilePicture() != null) {
            JLabel pic1 = new JLabel();
            pic1.setIcon(new ImageIcon(player1.getProfilePicture().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
            player1Panel.add(pic1);
        }
        nameLabel1 = new JLabel(player1.getName(), SwingConstants.LEFT);
        nameLabel1.setForeground(Color.BLUE);
        player1Panel.add(nameLabel1);

        // Profile + name for player 2 (O)
        JPanel player2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        if (player2.getProfilePicture() != null) {
            JLabel pic2 = new JLabel();
            pic2.setIcon(new ImageIcon(player2.getProfilePicture().getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
            player2Panel.add(pic2);
        }
        nameLabel2 = new JLabel(player2.getName(), SwingConstants.LEFT);
        nameLabel2.setForeground(Color.BLACK);
        player2Panel.add(nameLabel2);

        scoreLabel1 = new JLabel("Score: " + player1.getWins(), SwingConstants.CENTER);
        scoreLabel2 = new JLabel("Score: " + player2.getWins(), SwingConstants.CENTER);

        JLabel seriesLabel = new JLabel("First to " + SERIES_WINS + " wins!", SwingConstants.CENTER);
        seriesLabel.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        infoPanel.add(player1Panel);
        infoPanel.add(player2Panel);
        infoPanel.add(scoreLabel1);
        infoPanel.add(scoreLabel2);
        infoPanel.add(seriesLabel);

        restartButton = new JButton("Restart Series");
        restartButton.addActionListener(e -> restartSeries());
        infoPanel.add(restartButton);

        add(infoPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
        moves = 0;
    }

    private void handleMove(int row, int col) {
        if (seriesOver) return;
        JButton btn = buttons[row][col];
        if (!btn.getText().equals("")) return;

        if (currentPlayer == player1) {
            btn.setText("X");
            btn.setForeground(Color.BLUE);
        } else {
            btn.setText("O");
            btn.setForeground(Color.BLACK);
        }
        moves++;

        int[][] winLine = checkWin();
        if (winLine != null) {
            currentPlayer.incrementWins();
            updateScores();
            gamesPlayed++;
            animateWin(winLine);
            if (currentPlayer.getWins() >= SERIES_WINS) {
                seriesOver = true;
                JOptionPane.showMessageDialog(this, "🏆 Congratulations " + currentPlayer.getName() + "! You win the series!");
                disableBoard();
            } else {
                JOptionPane.showMessageDialog(this, "Congratulations " + currentPlayer.getName() + "! You win this round!");
                resetBoard();
            }
            return;
        }
        if (moves == 9) {
            gamesPlayed++;
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetBoard();
            return;
        }

        switchPlayer();

        // AI move for single player
        if (mode.startsWith("Single") && currentPlayer.isAI() && !seriesOver) {
            aiMove();
        }
    }

    private void aiMove() {
        // Slightly smarter AI: pick win/block, else random
        int[] move = findBestMove();
        if (move != null) {
            handleMove(move[0], move[1]);
        }
    }

    private int[] findBestMove() {
        // Try to win or block, else random
        String aiSymbol = "O";
        String playerSymbol = "X";
        // Try to win
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText(aiSymbol);
                    if (checkWin() != null) {
                        buttons[i][j].setText("");
                        return new int[]{i, j};
                    }
                    buttons[i][j].setText("");
                }
        // Try to block
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) {
                    buttons[i][j].setText(playerSymbol);
                    if (checkWin() != null) {
                        buttons[i][j].setText("");
                        return new int[]{i, j};
                    }
                    buttons[i][j].setText("");
                }
        // Else random
        ArrayList<int[]> empty = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals("")) empty.add(new int[]{i, j});
        if (!empty.isEmpty()) {
            return empty.get(rand.nextInt(empty.size()));
        }
        return null;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    // Returns winning line as array of [row,col] or null if no win
    private int[][] checkWin() {
        String symbol = currentPlayer == player1 ? "X" : "O";
        // Rows
        for (int i = 0; i < 3; i++)
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol))
                return new int[][]{{i,0},{i,1},{i,2}};
        // Columns
        for (int j = 0; j < 3; j++)
            if (buttons[0][j].getText().equals(symbol) && buttons[1][j].getText().equals(symbol) && buttons[2][j].getText().equals(symbol))
                return new int[][]{{0,j},{1,j},{2,j}};
        // Diagonals
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol))
            return new int[][]{{0,0},{1,1},{2,2}};
        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol))
            return new int[][]{{0,2},{1,1},{2,0}};
        return null;
    }

    private void animateWin(int[][] winLine) {
        // Flash the winning buttons
        Timer timer = new Timer(150, null);
        final int[] count = {0};
        timer.addActionListener(e -> {
            for (int[] pos : winLine) {
                JButton btn = buttons[pos[0]][pos[1]];
                if (count[0] % 2 == 0) {
                    btn.setBackground(Color.YELLOW);
                } else {
                    btn.setBackground(null);
                }
            }
            count[0]++;
            if (count[0] > 5) {
                timer.stop();
                for (int[] pos : winLine) {
                    buttons[pos[0]][pos[1]].setBackground(null);
                }
            }
        });
        timer.start();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(null);
            }
        moves = 0;
        currentPlayer = player1;
    }

    private void updateScores() {
        scoreLabel1.setText("Score: " + player1.getWins());
        scoreLabel2.setText("Score: " + player2.getWins());
    }

    private void disableBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setEnabled(false);
    }

    private void enableBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setEnabled(true);
    }

    private void restartSeries() {
        player1.resetWins();
        player2.resetWins();
        updateScores();
        resetBoard();
        enableBoard();
        seriesOver = false;
        gamesPlayed = 0;
    }
}