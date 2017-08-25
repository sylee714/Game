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
    private final char HUMAN = 'O';
    private final char COMPUTER = 'X';
    private final char BLANK = '-';
    private long timeLimit;
    private boolean humanFirst;
    private final int SIZE = 8;
    private char[][] board;
    private final char[] ROWS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    
    public Game(long timeLimit, boolean humanFirst) {
        this.timeLimit = timeLimit;
        this.humanFirst = humanFirst;
        board = new char[SIZE][SIZE];
        initializeBoard();
    }
    
    public void getMove(String row, String column) {
        String upperCaseRow = row.toUpperCase();
        int rowIndex = 0;
        int columnIndex = Integer.parseInt(column);
        for (int i = 0; i < SIZE; ++i) {
            if (upperCaseRow.equals(ROWS[i])) {
                rowIndex = i;
                break;
            }
        }
        board[rowIndex][columnIndex] = HUMAN;
    }
    
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = BLANK;
            }
        }
    }
    
    
    public int evaluate() {
        int value = 0;
        return value;
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
