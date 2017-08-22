/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 */
public class Game {
    private final String HUMAN = "O";
    private final String COMPUTER = "X";
    private final String BLANK = "-";
    private final int SIZE = 8;
    private String[][] board;
    private final String[] ROWS = {"A", "B", "C", "D", "E", "F", "G", "H"};
    
    public Game() {
        board = new String[SIZE][SIZE];
        initializeBoard();
    }
    
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = BLANK;
            }
        }
    }
    
    
    public void print() {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < SIZE; ++i) {
            System.out.print(ROWS[i] + " ");
            for (int j = 0; j < SIZE; ++j) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
