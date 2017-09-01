package game;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        char[][] state = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                state[i][j] = '-';
            }
        }
//        state[3][1] = 'O';
//        //state[3][2] = 'O';
//        state[3][3] = 'O';
//        //state[3][4] = 'X';
//        state[2][2] = 'X';
//        Game game = new Game('O');
//        game.print(state);
//        System.out.println(game.calculate(state, 'X'));
//        System.out.println(game.calculate(state, 'O'));
//        //state[2][1] = 'X';
//        game.print(state);
//        System.out.println(game.calculate(state, 'X'));
//        System.out.println(game.calculate(state, 'O'));
        
        

        char choice = pickFirst();
        Game game = new Game(choice);
        game.print();
        if (choice == 'O') {
            // loop to run test UI
            for(int i = 0; i < 15 ; i++) {
                double time = (double) System.currentTimeMillis();
                playerChoice(game);
                System.out.println();
                game.print();
                System.out.println("Computer's Turn...\n");

//                Thread.sleep(5000); // simulate computer thinking...

                long tStart = System.currentTimeMillis();
                game.makeMove();
                long tEnd = System.currentTimeMillis();
                long tDelta = tEnd - tStart;
                game.print();
                System.out.printf("Computer took: %d milliseconds", tDelta + '\n');
            }
        } else {
            // loop to run test UI
            for(int i = 0; i < 15 ; i++) {
                System.out.println("Computer's Turn...\n");
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
    public static char pickFirst() {
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
    public static void playerChoice(Game game) {
        Scanner scan = new Scanner(System.in);
        int row,col;
        String choice = "";

        boolean correctIn = false;
        while(!correctIn) {
            System.out.print("\nChoose your next move: ");
            choice = scan.next();
            if(choice.length() != 2) {
                System.out.println("\nWrong input length!");
            } else {
                if(Character.isAlphabetic(choice.charAt(0)) && Character.isDigit(choice.charAt(1)))
                    correctIn = true;
                else
                    System.out.println("\nInvalid point value!");
            }
        }
        if((int)choice.charAt(0) < 97) {
            row = (int)choice.charAt(0) - 65; // accounts for upper case ASCII value
        } else {
            row = (int)choice.charAt(0) - 97; // accounts for lower case ASCII value
        }

        col = (int)choice.charAt(1) - 49; // accounts for pos int ASCII value
        game.getMove(row, col);
    }
}
