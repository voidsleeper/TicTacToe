# TicTacToe
1. **Player.java**
The Player class represents a player in the game. It stores information about the player's name, profile picture, number of wins, and whether the player is an AI. It also includes fun catchphrases for both human and AI players.

Key Features:
Fields:

name: The player's name.
profilePicture: The player's profile picture (optional for AI).
wins: Tracks the number of wins for the player.
isAI: Indicates whether the player is an AI.
catchPhrase: A fun phrase associated with the player.
Constructors:

One constructor initializes a human player with a name and profile picture.
Another constructor initializes an AI player with a name and no profile picture.
Methods:

Provides getters and setters for player attributes.
Includes methods to increment/reset wins and retrieve the player's catchphrase.
Catchphrase Logic:

Generates random catchphrases for human and AI players.
2. **LoginScreen.java**
The LoginScreen class provides the initial interface for players to set up the game. It allows players to enter their names, select the game mode, upload profile pictures, and start the game.

Key Features:
Fields:

Stores player names, profile pictures, and the selected game mode.
Welcome Screen:

Displays a welcome message and a "Continue" button to transition to the login form.
Login Form:

Allows players to enter their names, upload profile pictures, and select the game mode.
Toggles visibility of Player 2 fields based on the selected mode.
Profile Picture Upload:

Opens a file chooser dialog for players to upload profile pictures.
Game Start:

Validates inputs and launches the game by creating Player objects and initializing the GameBoard.



3. **GameBoard.java**
The GameBoard class represents the graphical user interface (GUI) for the Tic Tac Toe game. It handles game logic, player interactions, and series management.

Key Features:
Fields:

Stores the game board, players, scores, and series-related information.
Constructors:

Initializes the game board for single-player or two-player mode.
Game Logic:

Handles player moves, win detection, score updates, and series management.
AI Logic:

Implements AI behavior for single-player mode, including win/block strategies.
Animations:

Highlights the winning line with a flashing effect.
4. **HistoryManager.java**
The HistoryManager class manages the history of games played. It provides methods to save, view, and clear game history.

Key Features:
Fields:

Defines the file name for storing game history.
Save History:

Saves game history for single-player or two-player mode.
Auto-Save:

Automatically saves history for two-player games.
View History:

Displays game history in a scrollable dialog box.
Clear History:

Clears all game history by overwriting the file with empty content.
5. **Main Class**
To compile and run the project, you need a Main.java file that launches the LoginScreen. Here's an example:

Compilation into a Single Text File
Below is the combined code for all classes (Player.java, LoginScreen.java, GameBoard.java, HistoryManager.java, and Main.java) in a single text file format:

