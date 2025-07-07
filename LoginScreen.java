// === LoginScreen.java ===
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LoginScreen extends JFrame {
    private JTextField nameField;
    private JTextField nameField2; // For player 2
    private JLabel imageLabel;
    private JLabel imageLabel2; // For player 2
    private ImageIcon profilePic;
    private ImageIcon profilePic2; // For player 2
    private JComboBox<String> modeSelect;

    public LoginScreen() {
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        getContentPane().removeAll();
        setTitle("Welcome");
        setLayout(new BorderLayout());
        setSize(400, 250);

        JLabel welcomeLabel = new JLabel("<html><center><h1>Welcome to Tic Tac Toe!</h1><br>Enjoy classic fun with a twist.<br>Click Continue to start.</center></html>", SwingConstants.CENTER);
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> showLoginForm());

        add(welcomeLabel, BorderLayout.CENTER);
        add(continueButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        revalidate();
        repaint();
    }

    private void showLoginForm() {
        getContentPane().removeAll();
        setTitle("Login");
        setLayout(new BorderLayout());
        setSize(400, 450);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(new JLabel("Enter your name:"), gbc);

        gbc.gridy++;
        nameField = new JTextField();
        centerPanel.add(nameField, gbc);

        gbc.gridy++;
        modeSelect = new JComboBox<>(new String[]{"Single Player - Easy", "Single Player - Hard", "2 Player"});
        centerPanel.add(modeSelect, gbc);

        gbc.gridy++;
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(80, 80));
        centerPanel.add(imageLabel, gbc);

        gbc.gridy++;
        JButton uploadButton = new JButton("Upload Profile Picture");
        uploadButton.addActionListener(e -> choosePicture());
        centerPanel.add(uploadButton, gbc);

        // Player 2 fields (initially hidden)
        gbc.gridy++;
        nameField2 = new JTextField();
        nameField2.setVisible(false);
        centerPanel.add(nameField2, gbc);

        gbc.gridy++;
        imageLabel2 = new JLabel();
        imageLabel2.setHorizontalAlignment(JLabel.CENTER);
        imageLabel2.setPreferredSize(new Dimension(80, 80));
        imageLabel2.setVisible(false);
        centerPanel.add(imageLabel2, gbc);

        gbc.gridy++;
        JButton uploadButton2 = new JButton("Upload Player 2 Picture");
        uploadButton2.addActionListener(e -> choosePicture2());
        uploadButton2.setVisible(false);
        centerPanel.add(uploadButton2, gbc);

        // Show/hide player 2 fields based on mode
        modeSelect.addActionListener(e -> {
            boolean twoPlayer = modeSelect.getSelectedIndex() == 2;
            nameField2.setVisible(twoPlayer);
            imageLabel2.setVisible(twoPlayer);
            uploadButton2.setVisible(twoPlayer);
            if (twoPlayer) {
                nameField2.setBorder(BorderFactory.createTitledBorder("Player 2 Name"));
            }
            centerPanel.revalidate();
            centerPanel.repaint();
        });

        nameField2.setBorder(BorderFactory.createTitledBorder("Player 2 Name"));

        add(centerPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());
        add(startButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        revalidate();
        repaint();
    }

    private void choosePicture() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            profilePic = new ImageIcon(file.getAbsolutePath());
            Image img = profilePic.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            profilePic = new ImageIcon(img);
            imageLabel.setIcon(profilePic);
        }
    }

    private void choosePicture2() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            profilePic2 = new ImageIcon(file.getAbsolutePath());
            Image img = profilePic2.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            profilePic2 = new ImageIcon(img);
            imageLabel2.setIcon(profilePic2);
        }
    }

    private void startGame() {
        String name = nameField.getText().trim();
        String mode = (String) modeSelect.getSelectedItem();

        if (mode.equals("2 Player")) {
            String name2 = nameField2.getText().trim();
            if (name.isEmpty() || profilePic == null || name2.isEmpty() || profilePic2 == null) {
                JOptionPane.showMessageDialog(this, "Please enter both player names and upload both pictures.");
                return;
            }
            Player player1 = new Player(name, profilePic);
            Player player2 = new Player(name2, profilePic2);
            new GameBoard(player1, player2, mode);
        } else {
            if (name.isEmpty() || profilePic == null) {
                JOptionPane.showMessageDialog(this, "Please enter a name and upload a picture.");
                return;
            }
            Player player = new Player(name, profilePic);
            new GameBoard(player, mode);
        }
        dispose();
    }
}
