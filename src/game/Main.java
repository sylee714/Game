/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.Scanner;

/**
 *
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);


//        char[][] state = new char[8][8];
//        for (int i = 0; i < 8; ++i) {
//            for (int j = 0; j < 8; ++j) {
//                state[i][j] = '-';
//            }
//        }
//        state[0][5] = 'X';
//        state[0][4] = 'O';
//        state[0][6] = 'O';
//        //state[0][7] = 'O';
//        //state[1][5] = 'O';
//        state[2][5] = 'O';
//        state[2][3] = 'O';
//        state[2][2] = 'O';
//        state[5][4] = 'X';
//        state[6][4] = 'X';
//        //state[7][4] = 'X';
//        //state[6][5] = 'O';
//        //state[6][4] = 'O';
//        state[1][3] = 'X';
//        state[4][6] = 'X';
//        state[4][7] = 'O';
//        //state[3][2] = 'X';
//        state[3][3] = 'X';
//        state[3][4] = 'X';
//        state[4][4] = 'X';
//        state[7][4] = 'X';
        //state[3][5] = 'X';
        //state[3][1] = 'O';
//        for (int i = 0; i < 8; ++i) {
//            for (int j = 0; j < 8; ++j) {
//                System.out.print(state[i][j] + " ");
//            }
//            System.out.println();
//        }

        Board board = new Board();
        Game game = new Game();
        String choice;
        boolean humanFirst;
        int time;
        char[][] state = game.getState();

        System.out.print("Would you like to go first? (y/n): ");
        choice = scn.next().toLowerCase();
        if(choice.equals("y")) {
            System.out.print("\nHow long should the computer think about its moves (in seconds)? : ");
            time = scn.nextInt();
            humanFirst = true;
//            board = new Board(state, 'O'); // need another parameter to account for time to think
            game = new Game(time, humanFirst);
            game.print();
        } else if(choice.equals("x")){
            System.out.print("\nHow long should the computer think about its moves (in seconds)? : ");
            time = scn.nextInt();
            humanFirst = false;
//            board = new Board(state,'X');// need another parameter to account for time to think
            game = new Game(time, humanFirst);
            game.print();
        }

//        System.out.println('\n');
//        System.out.println(board.gethValue());
    
    }

}
