package game;

import java.util.Random;

/**
 *  This class represents a board. It stores the current state and 
 *  calculates the heuristic value of its state.
 */
public class Game {
    private final int MAX = 1000000;
    private final int HIGH = 10000;
    private final int HIGH_MEDIUM = 1050;
    private final int MEDIUM = 1000;
    private final int LOW = 100;
    private final int ONE = 1;
    private final int SIZE = 8; // Size of the board
    private final char HUMAN = 'O';
    private final char COMPUTER = 'X';
    private final char BLANK = '-';
    private char[][] board;
    private int hValue; // heuristic value
    private char firstPlayer; // to indicate who went first; 0 = human, 1 = computer
    private char secondPlayer; 
    private final char[] ROWS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    
    public Game(char firstPlayer) {
        hValue = 0;
        this.firstPlayer = firstPlayer;
        if (this.firstPlayer == HUMAN) {
            secondPlayer = COMPUTER;
        } else {
            secondPlayer = HUMAN;
        }
        board = new char[8][8];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = BLANK;
            }
        }
    }
    
    public void getMove() {
        Random r = new Random();
        int row = r.nextInt(SIZE);
        int col = r.nextInt(SIZE);
        boolean success = false;
        while(!success) {
            if (board[row][col] == BLANK) {
                board[row][col] = HUMAN;
                success = true;
            }
        }
    }
    
    public void getMove(int row, int col) {
        board[row][col] = HUMAN;
    }
    
    public void makeMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1; 
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == BLANK) {
                    board[i][j] = COMPUTER;
                    if (firstPlayer == COMPUTER) {
                        int moveVal = minimax(board, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                        if (moveVal > bestVal) {
                            bestRow = i;
                            bestCol = j;
                            bestVal = moveVal;
                        }
                        board[i][j] = BLANK;
                    } else {
                        int moveVal = minimax(board, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                        if (moveVal > bestVal) {
                            bestRow = i;
                            bestCol = j;
                            bestVal = moveVal;
                        }
                        board[i][j] = BLANK;
                    }
                }
            }
        }
        board[bestRow][bestCol] = COMPUTER;
    }
    
    public int minimax(char[][] state, int depth, int alpha, int beta, boolean isMax){
        boolean stop = false;
        if (depth == 0 || terminate(state)) {
            //print(state);
            //System.out.println(evaluate(state));
            return evaluate(state);
        }
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (state[i][j] == BLANK) {
                        state[i][j] = firstPlayer;
                        best = Math.max(best, minimax(state, depth - 1, alpha, beta, false));
                        state[i][j] = BLANK;
                        alpha = Math.max(alpha, best);
                        if (beta <= alpha) {
                           stop = true;
                           break;
                        }
                    }
                }
                if (stop) {
                    break;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (state[i][j] == BLANK) {
                        state[i][j] = secondPlayer;
                        best = Math.min(best, minimax(state, depth - 1, alpha, beta, true));
                        state[i][j] = BLANK;
                        beta = Math.min(beta, best);
                        if (beta <= alpha) {
                            stop = true;
                            break;
                        }
                    }
                }
                if (stop) {
                    break;
                }
            }
            return best;
        } 
    }
    
    public int evaluate(char[][] state) {
        hValue = 0; // Reset to 0
        if (checkGoal(firstPlayer, state)) {
            hValue = MAX;
        } else if (checkGoal(secondPlayer, state)) {
            hValue = -MAX;
        } else if (blankSpaces(state) == 0) {
            hValue = 0;
        } else {
            calculatePlayerMoves(HUMAN, state);
            calculatePlayerMoves(COMPUTER, state);
        }
        return hValue;
    }
    
    private boolean terminate(char[][] state) {
        if (blankSpaces(state) == 0 || checkGoal(HUMAN, state) || checkGoal(COMPUTER, state)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkGoal(char player , char[][] state) {
        boolean foundGoal = false;
        // Checking rows
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[i][j] == player) {
                    if (j + 3 < SIZE) {
                        if (state[i][j + 1] == player && 
                                state[i][j + 2] == player && 
                                    state[i][j + 3] == player) {
                            foundGoal = true;
                        }
                        // 5 in a line or above is not a goal
                        if (j + 4 < SIZE) {
                            if (state[i][j + 4] == player) {
                                foundGoal = false;
                                j = j + 4;
                            }
                        }
                    }
                }
                if (foundGoal) {
                    break;
                }
            }
        }      
        // Checking cols
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[j][i] == player) {
                    if (j + 3 < SIZE) {
                        if (state[j + 1][i] == player && 
                                state[j + 2][i] == player && 
                                    state[j + 2][i] == player) {
                            foundGoal = true;
                        }
                        // 5 in a line or above is not a goal
                        if (j + 4 < SIZE) {
                            if (state[j + 4][i] == player) {
                                foundGoal = false;
                                j = j + 4;
                            }
                        }
                    }
                }
                if (foundGoal) {
                    break;
                }
            }
        }
        return foundGoal;
    }
    
    
    private int blankSpaces(char[][] state) {
        int blankSpaces = 0;
        for (int i  = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[i][j] == BLANK) {
                    blankSpaces++;
                }
            }
        }
        return blankSpaces;
    }
    
    
    /**
     * Calculates a player's move to get a heuristic value.
     * @param player
     *              the symbol of the player
     */
    private void calculatePlayerMoves(char player, char[][] state) {
        int skipIndex;
        // Checking rows
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[i][j] == player) {
                    skipIndex = checkOverFour(true, state,player, i, j);
                    if (skipIndex != 0) {
                        //System.out.println(skipIndex);
                        j = j + skipIndex; 
                    } else if (checkDisjointThree(true, state,player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("D Three");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 3;
                    } else if (checkThree(true, state,player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("Three");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkDisjointTwo(true, state,player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("D Two");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkTwo(true, state,player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("Two");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 1;
                    } else if (checkOne(true, state,player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("One");    
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                    }
                }
            }
        }
        
        // Checking cols
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[j][i] == player) {
                    skipIndex = checkOverFour(false, state,player, j, i);
                    if (skipIndex != 0) {
                        System.out.println(skipIndex);
                        j = j + skipIndex; 
                    } else if (checkDisjointThree(false, state,player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("D Three");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 3;
                    } else if (checkThree(false, state,player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("Three");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkDisjointTwo(false, state,player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("D Two");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkTwo(false, state,player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("Two");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 1;
                    } else if (checkOne(false, state,player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("One");    
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                    }
                }
            }
        }
    }
    
    /**
     * Returns the how many number of index it should skip when it finds
     * a 5 in a line or above.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return 
     *                  the number of index is should skip
     */
    private int checkOverFour(boolean checkingRow, char[][] state, char symbol, int row, int col) {
        int skipIndex = 0;
        // Check row
        if (checkingRow) {
            // Only from 0 to 4
            if (col >= 0 && col < 5) {
                // 5 in a line
                if (col + 4 < SIZE) {
                    if (state[row][col + 1] == symbol && 
                            state[row][col + 2] == symbol && 
                            state[row][col + 3] == symbol && 
                            state[row][col + 4] == symbol) {
                        skipIndex = 4;
                    }
                }
                // 6 in a line
                if (col + 5 < SIZE) {
                    if (state[row][col + 1] == symbol && 
                            state[row][col + 2] == symbol && 
                            state[row][col + 3] == symbol && 
                            state[row][col + 4] == symbol && 
                            state[row][col + 5] == symbol) {
                        skipIndex = 5;
                    }
                }
                // 7 in a line
                if (col + 6 < SIZE) {
                    if (state[row][col + 1] == symbol && 
                            state[row][col + 2] == symbol && 
                            state[row][col + 3] == symbol && 
                            state[row][col + 4] == symbol && 
                            state[row][col + 5] == symbol && 
                            state[row][col + 6] == symbol) {
                        skipIndex = 6;
                    }
                }
                // 8 in a line
                if (col + 7 < SIZE) {
                    if (state[row][col + 1] == symbol && 
                            state[row][col + 2] == symbol && 
                            state[row][col + 3] == symbol && 
                            state[row][col + 4] == symbol && 
                            state[row][col + 5] == symbol && 
                            state[row][col + 6] == symbol && 
                            state[row][col + 7] == symbol) {
                        skipIndex = 7;
                    }
                }
            }
        // Check Column
        } else {
            if (row >= 0 && row < 5) {
                // 5 in a line
                if (row + 4 < SIZE) {
                    if (state[row + 1][col] == symbol && 
                            state[row + 2][col] == symbol && 
                            state[row + 3][col] == symbol && 
                            state[row + 4][col] == symbol) {
                        skipIndex = 4;
                    }
                }
                // 6 in a line
                if (row + 5 < SIZE) {
                    if (state[row + 1][col] == symbol && 
                            state[row + 2][col] == symbol && 
                            state[row + 3][col] == symbol && 
                            state[row + 4][col] == symbol && 
                            state[row + 5][col] == symbol) {
                        skipIndex = 5;
                    }
                }
                // 7 in a line
                if (row + 6 < SIZE) {
                    if (state[row + 1][col] == symbol && 
                            state[row + 2][col] == symbol && 
                            state[row + 3][col] == symbol && 
                            state[row + 4][col] == symbol && 
                            state[row + 5][col] == symbol && 
                            state[row + 6][col] == symbol) {
                        skipIndex = 6;
                    }
                }
                // 8 in a line
                if (row + 7 < SIZE) {
                    if (state[row + 1][col] == symbol && 
                            state[row + 2][col] == symbol && 
                            state[row + 3][col] == symbol && 
                            state[row + 4][col] == symbol && 
                            state[row + 5][col] == symbol && 
                            state[row + 6][col] == symbol && 
                            state[row + 7][col] == symbol) {
                        skipIndex = 7;
                    }
                }
            }
        }
        return skipIndex;
    }
    
    /**
     * Checks if there is a 3 in a row.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return 
     *                  true if it found one; otherwise, false 
     */
    private boolean checkThree(boolean checkingRow,  char[][] state, char symbol,int row, int col) {
        boolean found = false;
        boolean startClosed = false;
        boolean endClosed = false;
        // Check row
        if (checkingRow) {
            // When start is out of bound
            if (col == 0) {
                startClosed = true;
                if (state[row][col + 1] == symbol && state[row][col + 2] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row, col + 2); 
            } else if (col > 0 && col < 6) {
                startClosed = checkStart(checkingRow, state,row, col);
                if (state[row][col + 1] == symbol && state[row][col + 2] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (col + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row, col + 2);
                }
            }
        // Check Column
        } else {
            // When start is out of bound
            if (row == 0) {
                startClosed = true;
                if (state[row + 1][col] == symbol && state[row + 2][col] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row + 2, col); 
            } else if (row > 0 && row < 6) {
                startClosed = checkStart(checkingRow, state,row, col);
                if (state[row + 1][col] == symbol && state[row + 2][col] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (row + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row + 2, col);
                }
            }
        }
        // 3 in a row with 2 opens = HIGH
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, HIGH);
        // 3 in a row with 1 open = HIGH_MEDIUM
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, HIGH_MEDIUM);
        }
        return found;
    }
    
    /**
     * Checks if there is a disjoint 3 in a row. Ex. xx-x or x-xx
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return 
     *                  true if it found one; otherwise, false 
     */
    private boolean checkDisjointThree(boolean checkingRow,  char[][] state, char symbol,int row, int col) {
        boolean found = false;
        boolean startClosed = false;
        boolean endClosed = false;
        // Check row
        if (checkingRow) {
            // When start is out of bound
            if (col == 0) {
                startClosed = true;
                if ((state[row][col + 1] == symbol && state[row][col + 2] == BLANK && state[row][col + 3] == symbol) || 
                        (state[row][col + 1] == BLANK && state[row][col + 2] == symbol && state[row][col + 3] == symbol)) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row, col + 3); 
            } else if (col > 0 && col < 5) {
                startClosed = checkStart(checkingRow, state,row, col);
                if ((state[row][col + 1] == symbol && state[row][col + 2] == BLANK && state[row][col + 3] == symbol) || 
                        (state[row][col + 1] == BLANK && state[row][col + 2] == symbol && state[row][col + 3] == symbol)) {
                    found = true;
                }
                // When end is out of bound
                if (col + 4 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row, col + 3);
                }
            }
        // Check Column
        } else {
            // When start is out of bound
            if (row == 0) {
                startClosed = true;
                if ((state[row + 1][col] == symbol && state[row + 2][col] == BLANK && state[row + 3][col] == symbol) || 
                        (state[row + 1][col] == BLANK && state[row + 2][col] == symbol && state[row + 3][col] == symbol)) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row + 3, col); 
            } else if (row > 0 && row < 5) {
                startClosed = checkStart(checkingRow, state,row, col);
                if ((state[row + 1][col] == symbol && state[row + 2][col] == BLANK && state[row + 3][col] == symbol) || 
                        (state[row + 1][col] == BLANK && state[row + 2][col] == symbol && state[row + 3][col] == symbol)) {
                    found = true;
                }
                // When end is out of bound
                if (row + 4 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row + 3, col);
                }
            }
        }
        // 3 in a row with 2 opens = HIGH
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, HIGH);
        // 3 in a row with 1 open = HIGH_MEDIUM
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, HIGH_MEDIUM);
        }
        return found;
    }
    
    /**
     * Checks if there is a 2 in a row.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return 
     *                  true if it found one; otherwise, false 
     */
    private boolean checkTwo(boolean checkingRow,  char[][] state, char symbol, int row, int col) {
        boolean found = false;
        boolean startClosed = false;
        boolean endClosed = false;
        // Check row
        if (checkingRow) {
            // When start is out of bound
            if (col == 0) {
                startClosed = true;
                if (state[row][col + 1] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row, col + 1); 
            } else if (col > 0 && col < 7) {
                startClosed = checkStart(checkingRow,state, row, col);
                if (state[row][col + 1] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (col + 2 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row, col + 1);
                }
            }
        // Check Column
        } else {
            if (row == 0) {
                startClosed = true;
                if (state[row + 1][col] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row + 1, col);
            } else if (row > 0 && row < 7) {
                startClosed = checkStart(checkingRow, state,row, col);
                if (state[row + 1][col] == symbol) {
                    found = true;
                }
                if (row + 2 == SIZE) {
                    endClosed = true;
                } else {
                    //System.out.println("row: " + row);
                    //System.out.println("col: " + col);
                    endClosed = checkEnd(checkingRow, state,row + 1, col);
                }
            }
        }
        // 2 in a row with 2 opens = MEDIUM
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, MEDIUM);
        // 2 in a row with 2 opens = LOW   
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, LOW);
        }
        return found;
    }
    
    /**
     * Checks if there is a disjoint 2 in a row. Ex. x-x
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return 
     *                  true if it found one; otherwise, false 
     */
    private boolean checkDisjointTwo(boolean checkingRow,  char[][] state, char symbol, int row, int col) {
        boolean found = false;
        boolean startClosed = false;
        boolean endClosed = false;
        // Check row
        if (checkingRow) {
            // When start is out of bound
            if (col == 0) {
                startClosed = true;
                if (state[row][col + 1] == BLANK && state[row][col + 2] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row, col + 2); 
            } else if (col > 0 && col < 6) {
                startClosed = checkStart(checkingRow, state,row, col);
                if (state[row][col + 1] == BLANK && state[row][col + 2] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (col + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row, col + 2);
                }
            }
        // Check Column
        } else {
            // When start is out of bound
            if (row == 0) {
                startClosed = true;
                if (state[row + 1][col] == BLANK && state[row + 2][col] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, state,row + 2, col); 
            } else if (row > 0 && row < 6) {
                startClosed = checkStart(checkingRow, state,row, col);
                if (state[row + 1][col] == BLANK && state[row + 2][col] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (row + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, state,row + 2, col);
                }
            }
        }
        // 2 in a row with 2 opens = MEDIUM
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, MEDIUM);
        // 2 in a row with 2 opens = LOW   
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, LOW);
        }
        return found;
    }
    
    /**
     * Checks if there is a 1 in a row.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return 
     *                  true if it found one; otherwise, false 
     */
    private boolean checkOne(boolean checkingRow,  char[][] state, char symbol, int row, int col) {
        boolean found = true;
        boolean startClosed = false;
        boolean endClosed = false;
        // Check row
        if (checkingRow) {
            if (col == 0) {
                startClosed = true;
                endClosed = checkEnd(checkingRow, state,row, col);
            } else if (col == 7) {
                startClosed = checkStart(checkingRow, state, row, col);
                endClosed = true;
            } else {
                startClosed = checkStart(checkingRow, state, row, col);
                endClosed = checkEnd(checkingRow,state,row, col);
            }
        // Column
        } else {
            if (row == 0) {
                startClosed = true;
                endClosed = checkEnd(checkingRow, state,row, col);
            } else if (row == 7) {
                startClosed = checkStart(checkingRow, state, row, col);
                endClosed = true;
            } else {
                startClosed = checkStart(checkingRow, state, row, col);
                endClosed = checkEnd(checkingRow, state,row, col);
            }
        }
        // Add one per every open
        if (!startClosed) {
            updateHValue(symbol, ONE);
        }
        if (!endClosed) {
            updateHValue(symbol, ONE);
        }
        return found;
    }
    
    /**
     * Updates the heuristic value with the given points.
     * @param symbol
     *              the symbol that is currently evaluated
     * @param points 
     *              the value of points
     */
    private void updateHValue(char symbol, int points) {
        // If it's first player, add. Otherwise, subtract
        if (symbol == firstPlayer) {
            hValue = hValue + points;
        } else {
            hValue = hValue - points;
        }
    }
    
    /**
     * Checks if the start is open, meaning it's BLANK.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return
     *                  true if its closed; otherwise, false.
     */
    private boolean checkStart(boolean checkingRow, char[][] state, int row, int col) {
        boolean closed = false;
        if (checkingRow) {
            if (!(state[row][col - 1] == BLANK)) {
                closed = true;
            }
        } else {
            if (!(state[row - 1][col] == BLANK)) {
                closed = true;
            }
        }
        return closed;
    }
    
    /**
     * Checks if the end is open, meaning it's BLANK.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param row
     *                  row number
     * @param col
     *                  col number
     * @return
     *                  true if its closed; otherwise, false.
     */
    private boolean checkEnd(boolean checkingRow, char[][] state, int row, int col) {
        boolean closed = false;
        if (checkingRow) {     
            if (!(state[row][col + 1] == BLANK)) {
                closed = true;
            }
        } else {
            if (!(state[row + 1][col] == BLANK)) {
                closed = true;
            }
        }
        return closed;
    }
    
    /*
    private char opponentSymbol(char symbol) {
        if (symbol == HUMAN) {
            return COMPUTER;
        } else {
            return HUMAN;
        }
    }
    */

    public boolean putSymbol(char player, int row, int col) {
        if (board[row][col] == BLANK) {
            board[row][col] = player;
            return true;
        } else {
            System.out.println("It's already occupied.");
            return false;
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
    
    public void print(char[][] state) {
        System.out.println("  1 2 3 4 5 6 7 8");
        for (int i = 0; i < SIZE; ++i) {
            System.out.print(ROWS[i] + " ");
            for (int j = 0; j < SIZE; ++j) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
