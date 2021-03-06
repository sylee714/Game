/**
 *  This class represents a Game. It implements minimax algorithm with 
 *  alpha-beta pruning to find the best move against the opponent.
 */
public class Game {
    private final int SIZE = 8; // Size of the board
    private final char HUMAN = 'O';
    private final char COMPUTER = 'X';
    private final char BLANK = '-';

    private int TIME_LIMIT = 5000; // time in milliseconds
    private boolean searchCutOff = false;

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
            displayWinner(terminate(board));
            return true;
        } else {
            System.out.println("It's already occupied.");
            return false;
        }
    }
    
    /**
     * Computer makes the best move.
     */
    public void makeMove() {
        // original location of start time
        int best = Integer.MIN_VALUE;
        int score = 0;
        int bestRow = -1;
        int bestCol = -1;
        int moves = checkValidMoves();
        // Go through every possible move.
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == BLANK) {
                    // Make a move
                    board[i][j] = COMPUTER;
                    long searchTimeLimit = ((TIME_LIMIT - 1000)/moves);
                    score = iterativeDeepeningSearch(board, false, searchTimeLimit);

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
        System.out.println("\nComputer move: " + ROWS[bestRow] + (bestCol + 1));
        System.out.println();
        board[bestRow][bestCol] = COMPUTER;
        displayWinner(terminate(board));
    }

    /**
     * Displays who's the winner based on the score parameter
     * @param score value from running the IDS w/AlphaBeta to determine winner/draw
     */
    public void displayWinner(int score) {
        switch(score) {
            case 200000000:
                print();
                System.out.println("Computer wins!\nGAME OVER");
                System.exit(1);
                break;
            case -200000000:
                print();
                System.out.println("You won!\nGAME OVER");
                System.exit(1);
                break;
            case 1:
                print();
                System.out.println("It's a TIE!\nGAME OVER");
                System.exit(1);
                break;
            default:
                break;
        }
    }

    /**
     * Minimax algorithm with alpha-beta pruning.
     * @param board     the current board
     * @param depth     the depth
     * @param alpha     the alpha
     * @param beta      the beta
     * @param isMax     to indicate which one to use minValue or maxValue
     * @param startTime starting time limit
     * @param timeLimit set time limit
     * @return the best value
     */
    public int minimax(char[][] board, int depth, int alpha, int beta, boolean isMax, long startTime, long timeLimit){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - startTime);

        if(elapsedTime >= timeLimit)
            searchCutOff = true;

        boolean stop = false;
        int whoWon = terminate(board);
        if (whoWon != 0) {
            return whoWon;
        }
        // Terminal state
        if (searchCutOff || depth == 0) {
            char currentTurn = BLANK;
            if (isMax) {
                currentTurn = secondPlayer;
            } else {
                currentTurn = firstPlayer;
            }
            return calculate(board, currentTurn);
        }
        // maxValue
        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < SIZE; ++i) {
                for (int j = 0; j < SIZE; ++j) {
                    if (board[i][j] == BLANK) {
                        board[i][j] = secondPlayer;
                        best = Math.max(best, minimax(board, depth - 1, alpha, beta, false,
                                startTime, timeLimit));
                        board[i][j] = BLANK;
                        alpha = Math.max(alpha, best);
                        // Alpha-beta pruning
                        if (beta <= alpha) {
                           stop = true;
                           break;
                        }
                    }
                }
                // Alpha-beta pruning
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
                        best = Math.min(best, minimax(board, depth - 1, alpha, beta, true,
                                startTime, timeLimit));
                        board[i][j] = BLANK;
                        beta = Math.min(beta, best);
                        // Alpha-bata pruning
                        if (beta <= alpha) {
                            stop = true;
                            break;
                        }
                    }
                }
                // Alpha-beta pruning
                if (stop) {
                    break;
                }
            }
            return best;
        } 
    }

    /**
     * Iterative deepening search
     * @param board state of the game
     * @param isMax boolean to check if player is MAX vs MIN
     * @param timeLimit time limit for total search
     * @return
     */
    public int iterativeDeepeningSearch(char[][] board,boolean isMax, long timeLimit) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + timeLimit;
        int depth = 1;
        int score = 0;
        searchCutOff = false;

        while(true) {
            long currentTime = System.currentTimeMillis();

            if(currentTime >= endTime)
                break;

            int searchResult =  minimax(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, isMax,
                    currentTime, endTime - currentTime);
            if(searchResult == 0) {
                score = searchResult;
                break;
            }
            if(!searchCutOff) {
                score = searchResult;
            }
        }
        return score;
    }

    public int checkValidMoves() {
        int validMoves = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if(board[i][j] == BLANK) {
                    validMoves ++;
                }
            }
        }
        return validMoves;
    }

    /**
     * Calculate the total vale of a board.
     * @param board the board
     * @param currentTurn whose turn is it
     * @return the total value of the board
     */
    public int calculate(char[][] board, char currentTurn) {
        int computerValue = checkRows(board, COMPUTER, currentTurn) + 
                            checkCols(board, COMPUTER, currentTurn) +
                            checkDisjointRows(board, COMPUTER, currentTurn) +
                            checkDisjointCols(board, COMPUTER, currentTurn);
        int humanValue = checkRows(board, HUMAN, currentTurn) + 
                            checkCols(board, HUMAN, currentTurn) +
                            checkDisjointRows(board, HUMAN, currentTurn) +
                            checkDisjointCols(board, HUMAN, currentTurn);;;
        return computerValue - humanValue;
    }
    
    /**
     * Check rows if there is any disjoint 2 in a line or 3 in a line.
     * @param board the board
     * @param player the player
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int checkDisjointRows(char[][] board, char player, char currentTurn) {
        int value = checkDisjointThreeRows(board, player, currentTurn);
        if (value == 0) {
            value = checkDisjointTwoRows(board, player, currentTurn);
        }
        return value;
    }
    
    /**
     * Check cols if there is any disjoint 2 in a line or 3 in a line.
     * @param board the board
     * @param player the player
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int checkDisjointCols(char[][] board, char player, char currentTurn) {
        int value = checkDisjointThreeCols(board, player, currentTurn);
        if (value == 0) {
            value = checkDisjointTwoCols(board, player, currentTurn);
        }
        return value;
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
     * Check if there is a disjoint 2 in a line, in rows. Ex. X-X
     * @param board the board
     * @param player the player
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int checkDisjointTwoRows(char[][] board, char player, char currentTurn) {
        int value = 0;
        int openEnds = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == player) {
                    if (j >= 0 && j <= 5) {
                        if ((board[i][j+1] == BLANK) && (board[i][j+2] == player)) {
                            if (checkFirstEnd(true, board, i, j)) {
                                openEnds++;
                            }
                            if (checkSecondEnd(true, board, i, j+2)) {
                                openEnds++;
                            }
                            value += getValue(2, openEnds, player == currentTurn);
                            j += 2;
                            openEnds = 0;
                        }
                    }
                }
            }
        }
        return value;
    }
    
    /**
     * Check if there is a disjoint 2 in a line, in columns. Ex. X-X
     * @param board the board
     * @param player the player
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int checkDisjointTwoCols(char[][] board, char player, char currentTurn) {
        int value = 0;
        int openEnds = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[j][i] == player) {
                    if (j >= 0 && j <= 5) {
                        if ((board[j+1][i] == BLANK) && (board[j+2][i] == player)) {
                            if (checkFirstEnd(false, board, j, i)) {
                                openEnds++;
                            }
                            if (checkSecondEnd(false, board, j+2, i)) {
                                openEnds++;
                            }
                            value += getValue(2, openEnds, player == currentTurn);
                            j += 2;
                            openEnds = 0;
                        }
                    }
                }
            }
        }
        return value;
    }
    
    /**
     * Check if there is a disjoint 3 in a line, in columns. Ex. X-XX or XX-X
     * @param board the board 
     * @param player the player
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int checkDisjointThreeRows(char[][] board, char player, char currentTurn) {
        int value = 0;
        int openEnds = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == player) {
                    if (j >= 0 && j <= 4) {
                        //X-XX
                        if ((board[i][j+1] == BLANK) && (board[i][j+2] == player) && (board[i][j+3] == player)) {
                            if (checkFirstEnd(true, board, i, j)) {
                                openEnds++;
                            }
                            if (checkSecondEnd(true, board, i, j+3)) {
                                openEnds++;
                            }
                            value += getValue(3, openEnds, player == currentTurn);
                            j += 3;
                            openEnds = 0;
                        //XX-X    
                        } else if ((board[i][j+1] == player) && (board[i][j+2] == BLANK) && (board[i][j+3] == player)) {
                            if (checkFirstEnd(true, board, i, j)) {
                                openEnds++;
                            }
                            if (checkSecondEnd(true, board, i, j+3)) {
                                openEnds++;
                            }
                            value += getValue(3, openEnds, player == currentTurn);
                            j += 3;
                            openEnds = 0;
                        }
                    }
                }
            }
        }
        return value;
    }
    
    /**
     * Check if there is a disjoint 3 in a line, in columns. Ex. X-XX or XX-X
     * @param board the board 
     * @param player the player
     * @param currentTurn whose turn is it
     * @return the value
     */
    public int checkDisjointThreeCols(char[][] board, char player, char currentTurn) {
        int value = 0;
        int openEnds = 0;
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[j][i] == player) {
                    if (j >= 0 && j <= 4) {
                        //X-XX
                        if ((board[j+1][i] == BLANK) && (board[j+2][i] == player) && (board[j+3][i] == player)) {
                            if (checkFirstEnd(false, board, j, i)) {
                                openEnds++;
                            }
                            if (checkSecondEnd(false, board, j+3, i)) {
                                openEnds++;
                            }
                            value += getValue(3, openEnds, player == currentTurn);
                            j += 3;
                            openEnds = 0;
                        //XX-X    
                        } else if ((board[j+1][i] == player) && (board[j+2][i] == BLANK) && (board[j+3][i] == player)) {
                            if (checkFirstEnd(false, board, j, i)) {
                                openEnds++;
                            }
                            if (checkSecondEnd(false, board, j+3, i)) {
                                openEnds++;
                            }
                            value += getValue(3, openEnds, player == currentTurn);
                            j += 3;
                            openEnds = 0;
                        }
                    }
                }
            }
        }
        return value;
    }
    
    /**
     * Checks the first end.
     * @param checkingRow true if it's checking rows; otherwise, false
     * @param board the board
     * @param row the row number
     * @param col the col number
     * @return true if it's open; otherwise, false 
     */
    public boolean checkFirstEnd(boolean checkingRow, char[][] board, int row, int col) {
        if (checkingRow) {
            if (col == 0) {
                return false;
            } else {
                if (board[row][col-1] == BLANK) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (row == 0) {
                return false;
            } else {
                if (board[row-1][col] == BLANK) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    
    /**
     * Checks the second end.
     * @param checkingRow true if it's checking rows; otherwise, false
     * @param board the board
     * @param row the row number
     * @param col the col number
     * @return true if it's open; otherwise, false 
     */
    public boolean checkSecondEnd(boolean checkingRow, char[][] board, int row, int col) {
        if (checkingRow) {
            if (col >= 7) {
                return false;
            } else {
                if (board[row][col+1] == BLANK) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (row >= 7) {
                return false;
            } else {
                if (board[row+1][col] == BLANK) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    
    /**
     * Returns the value of one of the cases. Based on whose turn is, it
     * returns a greater value.
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
     * To check if it reached a terminal.
     * @param board the board 
     * @return 0 if draw; 1 is human won; 2 if computer won; -1 if it's not a terminal
     */
    public int terminate(char[][] board) {
        // Human won
        if (checkGoal(HUMAN, board)){
            return -200000000;
        // Computer won
        } else if (checkGoal(COMPUTER, board)) {
            return 200000000;
        // Draw
        } else if (blankSpaces(board) == 0) {
            return 1;
        } else {
            return 0;
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
//                            System.out.println("4 in a line");
                            foundGoal = true;
                            // 5 in a line or above is not a goal
                            if (j + 4 < SIZE) {
                                if (board[i][j + 4] == player) {
//                                    System.out.println("Row: " + i + " Col: " + j);
//                                    System.out.println("Went over 4 in a line");
                                    foundGoal = false;
                                    j = j + 4;
                                }
                            }
                        }
                    }
                }
                if (foundGoal) {
                    return foundGoal;
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
                                    board[j + 3][i] == player) {
                            foundGoal = true;
                            // 5 in a line or above is not a goal
                            if (j + 4 < SIZE) {
                                if (board[j + 4][i] == player) {
                                    foundGoal = false;
                                    j = j + 4;
                                }
                            }
                        }
                    }
                }
                if (foundGoal) {
                    return foundGoal;
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
        System.out.println("\n  1 2 3 4 5 6 7 8");
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
    public void setTime(int time) {
        TIME_LIMIT = time * 1000;
    }
}
