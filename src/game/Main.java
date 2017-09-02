package game;

import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        char[][] state = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                state[i][j] = '-';
            }
        }

        char choice = pickFirst();
        Game game = new Game(choice);
        int timeLimit =  chooseTime();// 5-30 sec

        game.setTime(timeLimit);
        game.print();

        if (choice == 'O') {
            while(true) {
                double time = (double) System.currentTimeMillis();
                playerChoice(game);
                System.out.println();
                game.print();
                System.out.println("Computer's \"thinking\"...\n");
                game.makeMove();
                game.print();
            }
        } else {
            while(true) {
                System.out.println("Computer's \"thinking\"...\n");
                game.makeMove();
                game.print();
                playerChoice(game);
                System.out.println();
                game.print();
            }
        }
    }

    /**
     * Prompts user if player takes first turn
     * @return  char identifier used in Game class
     */
    private static char pickFirst() {
        Scanner scan = new Scanner(System.in);
        char option = 'O';
        String whosTurn;
        boolean correctIn = false;
        while(!correctIn) {
            System.out.print("Would you like to go first? (y/n): ");
            whosTurn = scan.next().toLowerCase();
            if(whosTurn.equals("y")) {
                option = 'O';
                correctIn = true;
            } else if(whosTurn.equals("n")){
                option = 'X';
                correctIn = true;
            } else {
                System.out.println("Invalid input!\n");
            }
        }
        return option;
    }

    /**
     * Prompts user for where to place tic/tac
     * @param game instance of the Game class to modify state
     */
    private static void playerChoice(Game game) {
        Scanner scan = new Scanner(System.in);
        int row,col;
        boolean taken = true;
        String choice = "";
        boolean correctIn = false;
        while(!correctIn) {
            System.out.print("\nChoose your next move: ");
            choice = scan.next();
            if(choice.length() != 2) {
                System.out.println("\nWrong input length!");
            } else {
                if(Character.isAlphabetic(choice.charAt(0)) && Character.isDigit(choice.charAt(1))) {
                    if((int)choice.charAt(0) < 97) {
                        row = (int)choice.charAt(0) - 65; // accounts for upper case ASCII value
                    } else {
                        row = (int)choice.charAt(0) - 97; // accounts for lower case ASCII value
                    }
                    col = (int)choice.charAt(1) - 49; // accounts for pos int ASCII value
                    correctIn = game.getMove(row, col); // false if spot is occupied
                }

                else {
                    System.out.println("\nInvalid point coordinate!");
                }
            }
        }
    }

    /**
     * Prompts user to input time for computer to "think"
     * @return the time limit in seconds
     */
    private static int chooseTime() {
        boolean inRange = false;
        Scanner scan = new Scanner(System.in);
        int timeLimit = 5;
        while(!inRange) {
            System.out.print("\nHow long should the computer think about its moves (in seconds)? :");
            timeLimit = scan.nextInt(); // 5-30 sec
            if(timeLimit >= 5 && timeLimit <= 30) {
                inRange = true;
            } else {
                System.out.println("\nPlease choose a time from 5-30 seconds!");
            }
        }
        return timeLimit;
    }
}
