/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

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
        
        /*
        state[7][5] = 'X';
        state[7][4] = 'X';
        state[7][7] = 'X';
        //state[0][4] = 'O';
        //state[0][6] = 'O';
        //state[0][7] = 'O';
        //state[1][5] = 'O';
        state[7][6] = 'O';
        state[6][6] = 'O';
        //state[2][4] = 'O';
        //state[2][3] = 'O';
        //state[2][2] = 'O';
        //state[5][4] = 'X';
        //state[6][4] = 'X';
        //state[7][4] = 'X';
        //state[6][5] = 'O';
        //state[6][4] = 'O';
        //state[1][3] = 'X';
        //state[4][6] = 'X';
        //state[4][7] = 'O';
        //state[3][2] = 'X';
        //state[3][3] = 'X';
        //state[3][4] = 'X';
        //state[4][4] = 'X';
        //state[7][4] = 'X';
        //state[3][5] = 'X';
        //state[3][1] = 'O';
        */
        
        
        Game game = new Game('O');
        //game.print(state);
        //System.out.println(game.evaluate(state));
        game.getMove(3,3);
        game.print();
        game.makeMove();
        game.print();
        game.getMove(3,4);
        game.print();
        game.makeMove();
        game.print();
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
    
}
