package game;

/**
 *  This class represents a board. It stores the current state and 
 *  calculates the heuristic value of its state.
 */
public class Board {
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
        evaluate();
    }
    
    private void evaluate() {
        calculatePlayerMoves(HUMAN);
        System.out.println("Value: " + hValue);
        calculatePlayerMoves(COMPUTER);
        System.out.println("Value: " + hValue);
    }
    
    /**
     * Calculates a player's move to get a heuristic value.
     * @param player
     *              the symbol of the player
     */
    private void calculatePlayerMoves(char player) {
     
        // Checking rows
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (state[i][j] == player) {
                    if (checkFour(true, player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("Four");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 3;
                    } else if (checkDisjointThree(true, player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("D Three");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 3;
                    } else if (checkThree(true, player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("Three");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkDisjointTwo(true, player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("D Two");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkTwo(true, player, i, j)) {
                        //System.out.println("Checking rows");
                        //System.out.println("Two");
                        //System.out.println("Row: " + i + " Col: " + j);
                        //System.out.println("---------------------------");
                        j = j + 1;
                    } else if (checkOne(true, player, i, j)) {
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
                    if (checkFour(false, player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("Four");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 3;
                    } else if (checkDisjointThree(false, player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("D Three");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 3;
                    } else if (checkThree(false, player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("Three");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkDisjointTwo(false, player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("D Two");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 2;
                    } else if (checkTwo(false, player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("Two");
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                        j = j + 1;
                    } else if (checkOne(false, player, j, i)) {
                        //System.out.println("Checking cols");
                        //System.out.println("One");    
                        //System.out.println("Row: " + j + " Col: " + i);
                        //System.out.println("---------------------------");
                    }
                }
            }
        }
    }
   
    /*
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
    */
    
    /**
     * Checks if there is a 4 in a row.
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
        // 4 in a row = MAX
        if (found && exactlyFour) {
            updateHValue(symbol, MAX);
        }
        return found;
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
    private boolean checkOne(boolean checkingRow, char symbol, int row, int col) {
        boolean found = true;
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
    
    /*
    private char opponentSymbol(char symbol) {
        if (symbol == HUMAN) {
            return COMPUTER;
        } else {
            return HUMAN;
        }
    }
    */

    public char[][] getState() {
        return state;
    }

    public int gethValue() {
        return hValue;
    }
}
