package game;

import java.util.Random;

/**
 *  This class represents a Game. It implements minimax algorithm with 
 *  alpha-beta pruning to find the best move against the opponent.
 */
public class Game {
    private final int SIZE = 8; // Size of the board
    private final char HUMAN = 'O';
    private final char COMPUTER = 'X';
    private final char BLANK = '-';
    private char turn;
    private char[][] board;
    private char firstPlayer;
    private char secondPlayer; 
    private final char[] ROWS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    
    public Game(char firstPlayer) {
        this.firstPlayer = firstPlayer;
        if (this.firstPlayer == HUMAN) {
            secondPlayer = COMPUTER;
        } else {
            secondPlayer = HUMAN;
        }
        // Initialze the board
        board = new char[8][8];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = BLANK;
            }
        }
    }

    /**
     * Get user's move. Check if it's a valid move.
     * @param row the row number
     * @param col the col number
     * @return true if it was valid; otherwise, false.
     */
    public boolean getMove(int row, int col) {
        if (board[row][col] == BLANK) {
            board[row][col] = HUMAN;
            return true;
        } else {
            System.out.println("It's already occupied.");
            System.out.println();
            return false;
        }
    }
    
    /**
     * Computer makes the best move.
     */
    public void makeMove() {
        int best = Integer.MIN_VALUE;
        int score;
        int bestRow = -1;
        int bestCol = -1; 
        // Go thru every possible move.
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == BLANK) {
                    // Make a move
                    board[i][j] = COMPUTER;
                    score = minimax(board, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    System.out.println("moveVal: " + score);
                    System.out.println("bestVal: " + best);
                    if (score > best) {
                        bestRow = i;
                        bestCol = j;
                        best = score;
                    }
                    // Undo
                    board[i][j] = BLANK;
                }
            }
        }
        System.out.println("Computer move: " + ROWS[bestRow] + (bestCol + 1));
        System.out.println();
        board[bestRow][bestCol] = COMPUTER;
    }
    
    /**
     * Minimax algorithm with alpha-beta pruning.
     * @param board the current board
     * @param depth the depth
     * @param alpha the alpha
     * @param beta the beta
     * @param isMax to indicate which one to use minValue or maxValue
     * @return the best value
     */
    public int minimax(char[][] board, int depth, int alpha, int beta, boolean isMax){
        boolean stop = false;
        // Terminal state
        if (depth == 0 || terminate(board)) {
            char currentTurn = BLANK;
            if (isMax) {
                currentTurn = secondPlayer;
            } else {
                currentTurn = firstPlayer;
            }
            //print(state);
            //System.out.println(analyze(state, currentTurn));
            //System.out.println(currentTurn);
            return calculate(board, currentTurn);
        }
        // maxValue
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (board[i][j] == BLANK) {
                        board[i][j] = secondPlayer;
                        best = Math.max(best, minimax(board, depth - 1, alpha, beta, false));
                        board[i][j] = BLANK;
                        alpha = Math.max(alpha, best);
                        // Alpha-bata pruning
                        if (beta <= alpha) {
                           stop = true;
                           break;
                        }
                    }
                }
                // Alpha-bata pruning
                if (stop) {
                    break;
                }
            }
            return best;
        // minValue
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (board[i][j] == BLANK) {
                        board[i][j] = firstPlayer;
                        best = Math.min(best, minimax(board, depth - 1, alpha, beta, true));
                        board[i][j] = BLANK;
                        beta = Math.min(beta, best);
                        // Alpha-bata pruning
                        if (beta <= alpha) {
                            stop = true;
                            break;
                        }
                    }
                }
                // Alpha-bata pruning
                if (stop) {
                    break;
                }
            }
            return best;
        } 
    }
    
    /**
     * Calculate the total vale of a board.
     * @param board the board
     * @param currentTurn whose turn is it
     * @return the total value of the board
     */
    public int calculate(char[][] board, char currentTurn) {
        return (checkRows(board, COMPUTER, currentTurn) + 
                checkCols(board, COMPUTER, currentTurn)) - 
                (checkRows(board, HUMAN, currentTurn) +
                checkCols(board, HUMAN, currentTurn));
    }
    
    /**
     * Check a board from left to right, horizontally.
     * @param board the board
     * @param player the player's symbol
     * @param currentTurn whose turn is it
     * @return the summed value
     */
    public int checkRows(char[][] board, char player, char currentTurn) {
        int value = 0;
        int countConsecutive = 0;
        int openEnds = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == player) {
                    countConsecutive++;
                } else if (board[i][j] == BLANK && countConsecutive > 0) {
                    openEnds++;
                    value += getValue(countConsecutive, openEnds, player == currentTurn);
                    countConsecutive = 0;
                    openEnds = 1;
                } else if (board[i][j] == BLANK) {
                    openEnds = 1;
                } else if (countConsecutive > 0) {
                    value += getValue(countConsecutive, openEnds, player == currentTurn);
                    countConsecutive = 0;
                    openEnds = 0;
                } else {
                    openEnds = 0;
                }
            }
            if (countConsecutive > 0) {
                    value += getValue(countConsecutive, openEnds, player == currentTurn);
            }
            countConsecutive = 0;
            openEnds = 0;
        }
        return value;
    }
    
    /**
     * Check a board from top to down, vertically.
     * @param board the board
     * @param player the player's symbol
     * @param currentTurn whose turn is it
     * @return the summed value
     */
    public int checkCols(char[][] board, char player, char currentTurn) {
        int value = 0;
        int countConsecutive = 0;
        int openEnds = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[j][i] == player) {
                    countConsecutive++;
                } else if (board[j][i] == BLANK && countConsecutive > 0) {
                    openEnds++;
                    value += getValue(countConsecutive, openEnds, player == currentTurn);
                    countConsecutive = 0;
                    openEnds = 1;
                } else if (board[j][i] == BLANK) {
                    openEnds = 1;
                } else if (countConsecutive > 0) {
                    value += getValue(countConsecutive, openEnds, player == currentTurn);
                    countConsecutive = 0;
                    openEnds = 0;
                } else {
                    openEnds = 0;
                }
            }
            if (countConsecutive > 0) {
                    value += getValue(countConsecutive, openEnds, player == currentTurn);
            }
            countConsecutive = 0;
            openEnds = 0;
        }
        return value;
    }
    
    /**
     * Returns the value of one of the cases. Based on whose turn is, it
     * give bonus.
     * @param consecutive the number of consecutive identical symbol
     * @param openEnds the number of open ends
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int getValue(int consecutive, int openEnds, boolean currentTurn) {
        // No open ends
        if (openEnds == 0 && consecutive < 5) {
            return 0;
        }
        switch(consecutive) {
            // 3 in a line
            case 3:
                switch(openEnds) {
                    case 1:
                        if (currentTurn) {
                            return 100000000;
                        }
                        return 50;
                    case 2:
                        if (currentTurn) {
                            return 100000000;
                        }
                        return 500000;
                }
            // 2 in a line
            case 2:
                switch(openEnds) {
                    case 1:
                        if (currentTurn) {
                            return 7;
                        }
                        return 5;
                    case 2:
                        if (currentTurn)
                            return 10000;
                        return 50;
                }
            // only 1
            case 1:
                switch(openEnds) {
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                } 
            default:
                return 200000000;
                
        }
    }
    
    /**
     * To check if it reached a terminal state
     * @param board the board 
     * @return true if it reached a terminal state; otherwise, false 
     */
    private boolean terminate(char[][] board) {
        if (blankSpaces(board) == 0 || checkGoal(HUMAN, board) || checkGoal(COMPUTER, board)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Check if it reached the goal state.
     * @param player the player's symbol
     * @param board the board
     * @return true if someone won; otherwise, false.
     */
    public boolean checkGoal(char player , char[][] board) {
        boolean foundGoal = false;
        // Checking rows
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == player) {
                    if (j + 3 < SIZE) {
                        if (board[i][j + 1] == player && 
                                board[i][j + 2] == player && 
                                    board[i][j + 3] == player) {
                            foundGoal = true;
                        }
                        // 5 in a line or above is not a goal
                        if (j + 4 < SIZE) {
                            if (board[i][j + 4] == player) {
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
                if (board[j][i] == player) {
                    if (j + 3 < SIZE) {
                        if (board[j + 1][i] == player && 
                                board[j + 2][i] == player && 
                                    board[j + 2][i] == player) {
                            foundGoal = true;
                        }
                        // 5 in a line or above is not a goal
                        if (j + 4 < SIZE) {
                            if (board[j + 4][i] == player) {
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
    
    /**
     * Check the number of blank spaces. It will be also used to indicate
     * a draw if the board has no empty spaces.
     * @param board a board
     * @return number of blank spaces
     */
    private int blankSpaces(char[][] board) {
        int blankSpaces = 0;
        for (int i  = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == BLANK) {
                    blankSpaces++;
                }
            }
        }
        return blankSpaces;
    }

    /**
     * Prints the board.
     */
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
    
    /**
     * Print a board
     * @param board a board 
     */
    public void print(char[][] board) {
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
