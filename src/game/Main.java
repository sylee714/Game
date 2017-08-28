/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

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
        state[0][5] = 'X';
        state[0][4] = 'O';
        state[0][6] = 'O';
        //state[0][7] = 'O';
        //state[1][5] = 'O';
        state[2][5] = 'O';
        state[2][3] = 'O';
        state[2][2] = 'O';
        state[5][4] = 'X';
        state[6][4] = 'X';
        //state[7][4] = 'X';
        //state[6][5] = 'O';
        //state[6][4] = 'O';
        state[1][3] = 'X';
        state[4][6] = 'X';
        state[4][7] = 'O';
        //state[3][2] = 'X';
        state[3][3] = 'X';
        state[3][4] = 'X';
        state[4][4] = 'X';
        state[7][4] = 'X';
        //state[3][5] = 'X';
        //state[3][1] = 'O';
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        Board board = new Board(state, 'O');
        board.evaluate();
    }
    
}
