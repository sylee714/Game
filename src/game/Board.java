package game;

/**
 *  This class represents a board. It stores the current state and 
 *  calculates the heuristic value of its state.
 */
public class Board {
    private final int MAX = 10000;
    private final int HIGH = 3000;
    private final int MEDIUM = 2000;
    private final int LOW = 500;
    private final int ONE = 1;
    private final int SIZE = 8; // Size of the board
    private final char HUMAN = 'O';
    private final char COMPUTER = 'X';
    private final char BLANK = '-';
    private char[][] state;
    private int hValue; // heuristic value
    //private boolean overFour;
    private char firstPlayer; // to indicate who went first; 0 = human, 1 = computer
    private char secondPlayer; 
    
    public Board(char[][] state, char firstPlayer) {
        hValue = 0;
        this.state = state;
        this.firstPlayer = firstPlayer;
        if (this.firstPlayer == HUMAN) {
            secondPlayer = COMPUTER;
        } else {
            secondPlayer = HUMAN;
        }
        //evaluate();
    }
    
    public void evaluate() {
        //calculateFirstPlayerMoves(HUMAN);
        //System.out.println("Value: " + hValue);
        calculatePlayerMoves(COMPUTER);
        System.out.println("Value: " + hValue);
    }
    
    /**
     * Calculates a player's move to get a heuristic value.
     * @param player
     *              the symbol of the player
     */
    private void calculatePlayerMoves(char player) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[i][j] == player) {
                    // Checking rows
                    //checkAll(true, player, i, j);
                    if (checkFour(true, player, i, j)) {
                        i = i + 3;
                    } else if (checkDisjointThree(true, player, i, j)) {
                        i = i + 3;
                    } else if (checkThree(true, player, i, j)) {
                        i = i + 2;
                    } else if (checkDisjointTwo(true, player, i, j)) {
                        i = i + 2;
                    } else if (checkTwo(true, player, i, j)) {
                        i = i + 1;
                    } else if (checkOne(true, player, i, j)) {
                        
                    }
                    // Checking columns
                    //checkAll(false, player, i, j);
                    if (checkFour(false, player, i, j)) {
                        i = i + 3;
                    } else if (checkDisjointThree(false, player, i, j)) {
                        i = i + 3;
                    } else if (checkThree(false, player, i, j)) {
                        i = i + 2;
                    } else if (checkDisjointTwo(false, player, i, j)) {
                        i = i + 2;
                    } else if (checkTwo(false, player, i, j)) {
                        i = i + 1;
                    } else if (checkOne(false, player, i, j)) {
                        
                    }
                }
            }
        }
    }
   
    /**
     * May not use this method.
     * @param checkingRow
     * @param player
     * @param row
     * @param col
     * @return 
     */
    private int checkAll(boolean checkingRow, char player, int row, int col) {
        int nextIndex = 0;
        if (checkFour(checkingRow, player, row, col)) {
            row = row + 3;
            System.out.println("Reached the goal");
        } else if (checkDisjointThree(checkingRow, player, row, col)) {
            row = row + 3;
        } else if (checkThree(checkingRow, player, row, col)) {
            row = row + 2;
        } else if (checkDisjointTwo(checkingRow, player, row, col)) {
            row = row + 2;
        } else if (checkTwo(checkingRow, player, row, col)) {
            row = row + 1;
        } else if (checkOne(checkingRow, player, row, col)) {
            // Do nothing
        }
        return nextIndex;
    }
    
    /**
     * Checks if there is a 4 in a row.
     * @param checkingRow
     *                  if checking rows then true; otherwise, false
     * @param symbol
     *                  the symbol of the player
     * @param row
     *                  number of the row
     * @param col
     *                  number of the column
     * @return 
     */
    private boolean checkFour(boolean checkingRow, char symbol, int row, int col) {
        boolean found = false;
        boolean exactlyFour = true;
        // Check row
        if (checkingRow) {
            // Only from 0 to 4
            if (col >= 0 && col < 5) {
                if (state[row][col + 1] == symbol && state[row][col + 2] == symbol && state[row][col + 3] == symbol) {
                    found = true;
                    //System.out.println(row + " " + col);
                }
                // Check if it goes over 4 
                if (col + 4 < SIZE) {
                    if (state[row][col + 4] == symbol) {
                        exactlyFour = false;
                    }
                }
            }
        // Check Column
        } else {
            if (row >= 0 && row < 5) {
                if (state[row + 1][col] == symbol && state[row + 2][col] == symbol && state[row + 3][col] == symbol) {
                    found = true;
                    //System.out.println(row + " " + col);
                }
                // Check if it goes over 4 
                if (row + 4 < SIZE) {
                    if (state[row + 4][col] == symbol) {
                        exactlyFour = false;
                    }
                }
            }
        }
        if (found && exactlyFour) {
            updateHValue(symbol, MAX);
        }
        return found;
    }
    
    private boolean checkThree(boolean checkingRow, char symbol,int row, int col) {
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
                endClosed = checkEnd(checkingRow, row, col + 2); 
            } else if (col > 0 && col < 6) {
                startClosed = checkStart(checkingRow, row, col);
                if (state[row][col + 1] == symbol && state[row][col + 2] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (col + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row, col + 2);
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
                endClosed = checkEnd(checkingRow, row + 2, col); 
            } else if (row > 0 && row < 6) {
                startClosed = checkStart(checkingRow, row, col);
                if (state[row + 1][col] == symbol && state[row + 2][col] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (row + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row + 2, col);
                }
            }
        }
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, MAX);
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, HIGH);
        }
        return found;
    }
    
    private boolean checkDisjointThree(boolean checkingRow, char symbol,int row, int col) {
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
                endClosed = checkEnd(checkingRow, row, col + 3); 
            } else if (col > 0 && col < 5) {
                startClosed = checkStart(checkingRow, row, col);
                if ((state[row][col + 1] == symbol && state[row][col + 2] == BLANK && state[row][col + 3] == symbol) || 
                        (state[row][col + 1] == BLANK && state[row][col + 2] == symbol && state[row][col + 3] == symbol)) {
                    found = true;
                }
                // When end is out of bound
                if (col + 4 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row, col + 3);
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
                endClosed = checkEnd(checkingRow, row + 3, col); 
            } else if (row > 0 && row < 5) {
                startClosed = checkStart(checkingRow, row, col);
                if ((state[row + 1][col] == symbol && state[row + 2][col] == BLANK && state[row + 3][col] == symbol) || 
                        (state[row + 1][col] == BLANK && state[row + 2][col] == symbol && state[row + 3][col] == symbol)) {
                    found = true;
                }
                // When end is out of bound
                if (row + 4 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row + 3, col);
                }
            }
        }
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, MAX);
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, HIGH);
        }
        return found;
    }
    
    private boolean checkTwo(boolean checkingRow, char symbol, int row, int col) {
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
                endClosed = checkEnd(checkingRow, row, col + 1); 
            } else if (col > 0 && col < 7) {
                startClosed = checkStart(checkingRow, row, col);
                if (state[row][col + 1] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (col + 2 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row, col + 1);
                }
            }
        // Check Column
        } else {
            if (row == 0) {
                startClosed = true;
                if (state[row + 1][col] == symbol) {
                    found = true;
                }
                endClosed = checkEnd(checkingRow, row + 1, col);
            } else if (row > 0 && row < 7) {
                startClosed = checkStart(checkingRow, row, col);
                if (state[row + 1][col] == symbol) {
                    found = true;
                }
                if (row + 2 == SIZE) {
                    endClosed = true;
                } else {
                    //System.out.println("row: " + row);
                    //System.out.println("col: " + col);
                    endClosed = checkEnd(checkingRow, row + 1, col);
                }
            }
        }
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, HIGH);
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, MEDIUM);
        }
        return found;
    }
    
    private boolean checkDisjointTwo(boolean checkingRow, char symbol, int row, int col) {
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
                endClosed = checkEnd(checkingRow, row, col + 2); 
            } else if (col > 0 && col < 6) {
                startClosed = checkStart(checkingRow, row, col);
                if (state[row][col + 1] == BLANK && state[row][col + 2] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (col + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row, col + 2);
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
                endClosed = checkEnd(checkingRow, row + 2, col); 
            } else if (row > 0 && row < 6) {
                startClosed = checkStart(checkingRow, row, col);
                if (state[row + 1][col] == BLANK && state[row + 2][col] == symbol) {
                    found = true;
                }
                // When end is out of bound
                if (row + 3 == SIZE) {
                    endClosed = true;
                } else {
                    endClosed = checkEnd(checkingRow, row + 2, col);
                }
            }
        }
        if (found && !startClosed && !endClosed) {
            updateHValue(symbol, HIGH);
        } else if (found && (!startClosed || !endClosed)) {
            updateHValue(symbol, MEDIUM);
        }
        return found;
    }
    
    private boolean checkOne(boolean checkingRow, char symbol, int row, int col) {
        boolean found = false;
        boolean startClosed = false;
        boolean endClosed = false;
        // Check row
        if (checkingRow) {
            if (col == 0) {
                startClosed = true;
                endClosed = checkEnd(checkingRow, row, col);
            } else if (col == 7) {
                startClosed = checkStart(checkingRow, row, col);
                endClosed = true;
            } else {
                startClosed = checkStart(checkingRow, row, col);
                endClosed = checkEnd(checkingRow,row, col);
            }
        // Column
        } else {
            if (row == 0) {
                startClosed = true;
                endClosed = checkEnd(checkingRow, row, col);
            } else if (row == 7) {
                startClosed = checkStart(checkingRow, row, col);
                endClosed = true;
            } else {
                startClosed = checkStart(checkingRow, row, col);
                endClosed = checkEnd(checkingRow, row, col);
            }
        }
        if (!startClosed) {
            updateHValue(symbol, ONE);
        }
        if (!endClosed) {
            updateHValue(symbol, ONE);
        }
        return found;
    }
    
    private void updateHValue(char symbol, int points) {
        if (symbol == firstPlayer) {
            hValue = hValue + points;
        } else {
            hValue = hValue - points;
        }
    }
    
    private boolean checkStart(boolean checkingRow, int row, int col) {
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
    
    private boolean checkEnd(boolean checkingRow, int row, int col) {
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
    
    private char opponentSymbol(char symbol) {
        if (symbol == HUMAN) {
            return COMPUTER;
        } else {
            return HUMAN;
        }
    }
    
}
