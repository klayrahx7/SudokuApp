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
    private Cell[][] boardCellMap;

    public Board()
    {
        this.boardID = -1;
        this.boardInitialState = "";
        this.boardCurrentState = "";
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCellMap = new Cell[BOARD_NUMBER_OF_COLUMNS][BOARD_NUMBER_OF_ROWS];
    }

    public Board(String newBoardState)
    {
        this.boardID = -1;
        this.boardInitialState = "";
        this.boardCurrentState = "";
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCellMap = new Cell[BOARD_NUMBER_OF_COLUMNS][BOARD_NUMBER_OF_ROWS];
    }

    public Board(Board newBoard)
    {
        this.boardID = newBoard.getBoardID();
        this.boardInitialState = newBoard.getBoardInitialState();
        this.boardCurrentState = newBoard.getBoardCurrentState();
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
    }

    public Board(long boardID, String boardInitialState, String boardCurrentState)
    {
        this.boardID = boardID;
        this.boardInitialState = boardInitialState;
        this.boardCurrentState = boardCurrentState;
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
    }

    private boolean buildCellMap(String newBoardState)
    {
        Cell[] cellInput = new Cell[newBoardState.length()];
        for(int i = 0; i < newBoardState.length(); i++)
        {
            cellInput[i] = new Cell(newBoardState.substring(i,i+1));
        }
        return true;
    }

    public String toString()
    {
        String currentState = "";
        for(int i = 0; i < BOARD_NUMBER_OF_COLUMNS; i++)
        {
            for(int j = 0; j < BOARD_NUMBER_OF_ROWS; j++)
            {
                if(this.boardCellMap[i][j].isSolved())
                {
                    currentState += this.boardCellMap[i][j].getCurrentValue();
                }
                else
                {
                    currentState += ".";
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
}
