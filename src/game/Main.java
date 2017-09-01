/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public static void main(String[] args) {
        
        char[][] state = new char[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                state[i][j] = '-';
            }
        }


        char choice = pickFirst();
        
        Game game = new Game(choice);
        // loop to run test UI
        for(int i = 0; i < 15 ; i++) {
            game.print();
            playerChoice(game);
            System.out.println();
            game.print();
            System.out.println("Computer's Turn...\n");
            game.makeMove();
            game.print();
        }

        //game.print(state);
        //System.out.println(game.evaluate(state));
//        game.getMove(3,3);
//        game.print();
//        game.makeMove();
//        game.print();
//        game.getMove(3,4);
//        game.print();
//        game.makeMove();
//        game.print();
        /*
        state[6][4] = 'X';
        game.print(state);
        System.out.println(game.evaluate(state));
        game.makeMove();
        game.print();
        game.getMove(7,6);
        game.print();
        game.makeMove();
        game.print();
        game.getMove(6,6);
        game.print();
        game.makeMove();
        game.print();
        game.getMove();
        game.print();
        */
        
       
        
    }

    /**
     * Prompts user if player takes first turn
     * @return  char identifier used in Game class
     */
    public static char pickFirst() {
        Scanner scan = new Scanner(System.in);
        String whosTurn;
        System.out.print("Would you like to go first? (y/n): ");
        whosTurn = scan.next().toLowerCase();
        if(whosTurn.equals("y")) {
            return 'O';
        } else {
            return 'X';
        }
    }

    /**
     * Prompts user for where to place tic/tac
     * @param game instance of the Game class to modify state
     */
    public static void playerChoice(Game game) {
        Scanner scan = new Scanner(System.in);
        String choice;
//        String move;
        System.out.print("Choose your next move: ");
        choice = scan.next();
        int row = (int)choice.charAt(0) - 97; // accounts for lower case ASCII value
        int col = (int)choice.charAt(1) - 49; // accounts for pos int ASCII value
        game.getMove(row, col);
    }
}
