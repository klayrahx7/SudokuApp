package cpsc463sudoku.sudoku;


/**
 * Created by Mark Ballin on 2/12/2017.
 */


public class Board {

    private long boardID;
    private final int BOARD_MOVE_HISTORY_LIMIT = 1000;
    private final int BOARD_NUMBER_OF_COLUMNS = 9;
    private final int BOARD_NUMBER_OF_ROWS = 9;
    private String boardInitialState;
    private String boardCurrentState;
    private String[] boardStateList;
    private Cell[][] boardCurrentCellMap;

    /*
     * Default Constructor
     */
    public Board()
    {
        this.boardID = -1;
        this.boardInitialState = "";
        this.boardCurrentState = "";
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCurrentCellMap = new Cell[BOARD_NUMBER_OF_COLUMNS][BOARD_NUMBER_OF_ROWS];
    }

    /*
     * Constructor from state string
     */
    public Board(String newBoardState)
    {
        this.boardID = -1;
        this.boardInitialState = "";
        this.boardCurrentState = "";
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCurrentCellMap = this.setCurrentCellMap(newBoardState);
    }

    /*
     * Copy Constructor
     */
    public Board(Board newBoard)
    {
        this.boardID = newBoard.getBoardID();
        this.boardInitialState = newBoard.getBoardInitialState();
        this.boardCurrentState = newBoard.getBoardCurrentState();
        this.boardStateList = newBoard.getBoardStateList();
        this.boardCurrentCellMap = newBoard.getCurrentCellMap();
    }

    /*
     * Builds a cell map from a state string
     * @Parameter newBoardState: string containing current state
     * @Parameter initialState: if this is a new board, set each cell in the state string to be an
     *                          initial value.
     */
    private Cell[][] setCurrentCellMap(String newBoardState, boolean ... initialState)
    {
        Cell[][] cellMap = new Cell[BOARD_NUMBER_OF_COLUMNS][BOARD_NUMBER_OF_ROWS];
        int currentPosition = 0;
        for(int i = 0; i < BOARD_NUMBER_OF_COLUMNS; i++)
        {
            for(int j = 0; j < BOARD_NUMBER_OF_ROWS; j++)
            {
                cellMap[j][i] = new Cell(newBoardState.substring(currentPosition,currentPosition+1));
                if(initialState[0])
                {
                    cellMap[j][i].setInitialValue(true);
                }
                currentPosition++;
            }
        }
        return cellMap;
    }

    /*
     * When the user short presses a single cell:
     *  The current cell is marked as selected.
     *  The row and column that contain the cell become highlighted.
     *  If a value already exists in the cell, the corresponding button is highlighted.
     *  When a new button is pressed, that value is saved into the current cell.
     *  The newly pressed button is highlighted
    */
    private void setCurrentCellMainNumber(int newValue, int xPos, int yPos)
    {
        this.boardCurrentCellMap[yPos][xPos].setCurrentValue(newValue);
        this.boardCurrentCellMap[yPos][xPos].setSelected(true);
        for(int i = 0; i < BOARD_NUMBER_OF_ROWS; i++)
        {
            if(xPos != i)
            {
                this.boardCurrentCellMap[yPos][i].setHighlighted(true);
            }
        }
        for(int i = 0; i < BOARD_NUMBER_OF_COLUMNS; i++)
        {
            if(yPos != i)
            {

            }

            this.boardCurrentCellMap[xPos][i].setHighlighted(true);
        }
    }

    /*
     * When the user long presses a single cell:
     *  The current cell is marked as selected.
     *  The row and column that contain the cell become highlighted.
     *  If a
    */
    private void setCurrentCellPossibleNumbers()
    {

    }

    /*
     * Checks to see if the user has compleatly filled the board.
     */
    private boolean isBoardFull()
    {
        for(int i = 0; i < BOARD_NUMBER_OF_COLUMNS; i++)
        {
            for(int j = 0; j < BOARD_NUMBER_OF_ROWS; j++)
            {
                if( ! this.boardCurrentCellMap[j][i].isSolved())
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks the current board state against the solver to determine correctness.
     */
    private boolean isBoardCorrect() {
        if (isBoardFull()) {
            //I TODO: nsert call to solver here
            // if(solver.isBoardCorrect(this.toString()))
            {
                return true;
            }
        }
        return false;
    }


    /*
     * Asks the solver for a hint on a particular cell.
     */
    private void getHint(int xPos, int yPos)
    {
        int hintPosition = (xPos+1) * (yPos+1);
        // TODO : uncomment the below line once the solver has been implemented.
        //this.boardCurrentCellMap[yPos][xPos].setCurrentValue(solver.getHint(this.board.toString(), hintPosition));
        this.boardCurrentCellMap[yPos][xPos].setCurrentValue(-1);
        this.boardCurrentCellMap[yPos][xPos].setHint(true);
    }

    /*
     * Will output the boards current state as a string, example:
     * 4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......
     */
    @Override
    public String toString()
    {
        String currentState = "";

        for(int i = 0; i < BOARD_NUMBER_OF_COLUMNS; i++)
        {
            for(int j = 0; j < BOARD_NUMBER_OF_ROWS; j++)
            {
                if(this.boardCurrentCellMap[j][i].getCurrentValue() == -1)
                {
                    currentState += ".";
                }
                else
                {
                    currentState += this.boardCurrentCellMap[j][i].getCurrentValue();
                }
            }
        }
        return currentState;
    }

    public long getBoardID() {
        return boardID;
    }

    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }

    public String getBoardInitialState() {
        return boardInitialState;
    }

    public void setBoardInitialState(String boardInitialState) {
        this.boardInitialState = boardInitialState;
    }

    public String[] getBoardStateList() {
        return boardStateList;
    }

    public void setBoardStateList(String[] boardStateList) {
        this.boardStateList = boardStateList;
    }

    public String getBoardCurrentState() {
        return boardCurrentState;
    }

    public void setBoardCurrentState(String boardCurrentState) {
        this.boardCurrentState = boardCurrentState;
    }

    public Cell[][] getCurrentCellMap()
    {
        return this.boardCurrentCellMap;
    }

}
