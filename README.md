# My Personal Project

## Connect 4

A game where each player takes turns placing a chip on a board one at a time and they will win if they get 4 in a row in any direction.

![c4 1photo](https://github.com/Carson-Lu/Connect4/assets/54121633/8aaa5a49-4443-40ed-b343-decf10accb5c)

# Features
- You can place a piece by pressing a number \[1-7\]
- Audio will play when a piece is dropped
- The application state is saved (placing a piece)
- Each event (piece being placed) is logged to the console

## Project Proposal

For my project, I want to create the game **Connect 4** and allow two people to play.
Those who are looking for a game to play with someone else would use this program.
This project interests me because I would like to learn how to create a game and understand how to allow user input.

## User Stories
- As a user, I want to be able to place a Connect 4 piece onto a board of pieces
- As a user, I want to be able to restart the game without exiting the program
- As a user, I want to be able to see whose turn it is
- As a user, I want to be able to play the game
- As a user, I want to be able to determine a winner
- As a user, I want to be able to determine a draw if there's no winner
- As a user, I want to be able to save the game board if desired
- As a user, I want to be able to load a previous game board and play from there
- As a user, I want to be able to input any value without the program breaking, even if it doesn't expect that value





# Event Logging

Wed Aug 10 09:56:52 PDT 2022
Player 1 placed a piece at 0

Wed Aug 10 09:56:53 PDT 2022
Player 2 placed a piece at 0

Wed Aug 10 09:56:53 PDT 2022
Player 1 placed a piece at 1

Wed Aug 10 09:56:53 PDT 2022
Player 2 placed a piece at 1

Wed Aug 10 09:56:54 PDT 2022
Player 1 placed a piece at 2

Wed Aug 10 09:56:54 PDT 2022
Player 2 placed a piece at 2

Wed Aug 10 09:56:54 PDT 2022
Player 1 placed a piece at 3


# Possible Improvements for the project

- Use observer pattern where Connect4Game is the subject/observable since many UI elements (the observers) rely on it
- Observers in this case would be the Connect4Frame, BoardPanel, and MenuPanel
- Observer pattern will also remove the need of the frame being passed into the handlers
