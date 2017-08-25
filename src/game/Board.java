/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author MingKie
 */
public class Board {
    private final char HUMAN = 'O';
    private final char COMPUTER = 'X';
    private final char BLANK = '-';
    private char[][] state;
    private int heuristicValue;
    private boolean humanFirst;
    
    public Board(char[][] state, boolean humanFirst) {
        this.state = state;
        this.humanFirst = humanFirst;        
    }
    
    private void evaluate() {
    }
}
