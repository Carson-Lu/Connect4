# My Personal Project

## Connect 4

A game where each player takes turns placing a chip on a board one at a time and they will win if they get 4 in a row in any direction.

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

# Instructions For Grader
- You can generate the first required event by clicking on the board and placing a piece onto the board
- You can generate the second required event by pressing a number \[1-7\] to place a piece on the corresponding column (requires you to first place a piece by clicking the board first though)
- You can locate my audio component by placing a piece (it will play a sound)
- You can save the state of my application by playing the game (placing a piece) or clicking the Load game/ Play game buttons (any action will save the game)
- You can reload the state of my application by opening the application and loading the game


# Phase 4: Task 2

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


# Phase 4: Task 3

- Use observer pattern where Connect4Game is the subject/observable since many UI elements (the observers) rely on it
- Observers in this case would be the Connect4Frame, BoardPanel, and MenuPanel
- Observer pattern will also remove the need of the frame being passed into the handlers
