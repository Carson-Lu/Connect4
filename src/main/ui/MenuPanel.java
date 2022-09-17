package ui;

import model.Connect4Game;

import javax.swing.*;
import java.awt.*;

import static ui.Connect4Frame.*;

/*
 * Panel for the menu which includes buttons to play again
 * and also text for the current game state (turn, game over, etc)
 */
public class MenuPanel extends JPanel {
    private static final int MPANEL_HEIGHT = 100;
    private Connect4Game game;
    private JLabel turnLabel;
    private JButton playAgainButton;
    private JButton loadGameButton;
    private GridBagConstraints gbc;
    private MenuEventHandler eventHandler;

    // Constructs panel for menu elements
    // EFFECTS: creates panel and shows
    public MenuPanel(Connect4Game game, Connect4Frame frame) {
        setPreferredSize(new Dimension(WIDTH, MPANEL_HEIGHT));
        this.game = game;
        this.eventHandler = new MenuEventHandler(game, frame);

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.weightx = 0;
        gbc.weighty = 0;

        initializeTurnLabel();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes button
    public void initializeTurnLabel() {
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20, 20, 20, 20);
        turnLabel = new JLabel();
        turnLabel.setAlignmentX(SwingConstants.CENTER);
        turnLabel.setAlignmentY(SwingConstants.CENTER);
        setTurnLabel();
        add(turnLabel, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes Buttons
    public void initializeButtons() {
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.ipadx = 0;

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        loadGameButton = new JButton();
        loadGameButton.setAlignmentX(Button.CENTER_ALIGNMENT);
        loadGameButton.setAlignmentY(Button.CENTER_ALIGNMENT);
        loadGameButton.setText("Load Game");
        loadGameButton.addActionListener(eventHandler);
        add(loadGameButton, gbc);

        gbc.gridx = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        playAgainButton = new JButton();
        playAgainButton.setAlignmentX(Button.CENTER_ALIGNMENT);
        playAgainButton.setAlignmentY(Button.CENTER_ALIGNMENT);
        playAgainButton.setText("Play Again");
        playAgainButton.addActionListener(eventHandler);
        playAgainButton.setEnabled(false);
        add(playAgainButton, gbc);

    }

    // MODIFIES: this
    // EFFECTS: sets the turn label based off of current state
    public void setTurnLabel() {
        switch (game.getGameState()) {
            case 0:
                turnLabel.setText("It is " + getTurnString(game.getTurn()) + "'s turn");
                break;
            case 3:
                turnLabel.setText("Draw, nobody wins!");
                break;
            default:
                turnLabel.setText(getTurnString(game.getTurn()) + " wins!");
                break;
        }
    }

    // REQUIRES: argument passed in is not null
    // EFFECTS: Returns colour based off of turn
    public String getTurnString(Boolean player) {
        if (player) {
            return PLAYER2_COLOUR_STRING;
        }
        return PLAYER1_COLOUR_STRING;
    }

    // MODIFIES: this
    // EFFECTS: disables load button after anything is pressed (load button can only used when program stsarts)
    //          enables play again button when game is over, otherwise disables it
    public void enableOrDisableButtons() {
        loadGameButton.setEnabled(false);
        playAgainButton.setEnabled(game.getGameState() != 0);
    }

    // MODIFIES: this
    // EFFECTS: calls setTurnLabel() and enableOrDisableButtons()
    public void update() {
        setTurnLabel();
        enableOrDisableButtons();
    }

}
