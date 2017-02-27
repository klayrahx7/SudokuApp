package cpsc463sudoku.sudoku;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Mark Ballin on 2/12/2017.
 */


public class BoardAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<BoardCell> boardCurrentStateBoardCellMap;
    private long boardID;
    private final int BOARD_MOVE_HISTORY_LIMIT = 1000;
    private String boardInitialState;
    private String boardCurrentState;
    private String boardSolvedState;
    private String[] boardStateList;
    private boolean isSolved;
    private boolean isSFull;
    private boolean isComplete;

    /*
     * Default Constructor
     */
    public BoardAdapter(Context context)
    {
        //super(context, android.R.id.content, objects);
        this.context = context;
        this.boardID = -1;
        this.boardInitialState = "";
        this.boardCurrentState = "";
        this.isSolved = false;
        this.isSFull = false;
        this.isComplete = false;
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCurrentStateBoardCellMap = new ArrayList<BoardCell>();
    }

    /*
     * Constructor from state string
     */
    public BoardAdapter(Context context, String newBoardState)
    {
        this.context = context;
        this.boardID = -1;
        this.boardInitialState = newBoardState;
        this.boardCurrentState = newBoardState;
        this.isSolved = false;
        this.isSFull = false;
        this.isComplete = false;
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCurrentStateBoardCellMap = this.setCurrentCellMap(newBoardState);
    }

    /*
     * Copy Constructor
     */
    public BoardAdapter(Context context, BoardAdapter newBoardAdapter)
    {
        this.context = context;
        this.boardID = newBoardAdapter.getBoardID();
        this.boardInitialState = newBoardAdapter.getBoardInitialState();
        this.boardCurrentState = newBoardAdapter.getBoardCurrentState();
        this.boardStateList = newBoardAdapter.getBoardStateList();
        this.boardCurrentStateBoardCellMap = newBoardAdapter.getCurrentCellMap();
        this.isSolved = newBoardAdapter.isSolved();
        this.isSFull = newBoardAdapter.isFull();
        this.isComplete = newBoardAdapter.isComplete();
    }

    @Override
    public int getCount() {
        return this.boardCurrentStateBoardCellMap.size();
    }

    @Override
    public BoardCell getItem(int position) {
        return this.boardCurrentStateBoardCellMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.boardCurrentStateBoardCellMap.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cell;

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            cell = layoutInflater.inflate(R.layout.board_cell, null);
            Button name = (Button) cell.findViewById(R.id.grid_item);
            final int pos = position;
            name.setText(String.valueOf(getCurrentCellMap().get(position).getCurrentValue()));
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "Board short click: " + pos);
                }
            });
            name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("TAG", "Board long click: " + pos);
                    return false;
                }
            });
        }
        else
        {
            cell = convertView;
        }

        return cell;
    }

    /*
     * Builds a board_cell map from a state string
     * @Parameter newBoardState: string containing current state
     * @Parameter initialState: if this is a new board, set each board_cell in the state string to be an
     *                          initial value.
     */
    private ArrayList<BoardCell> setCurrentCellMap(String newBoardState)
    {
        ArrayList<BoardCell> newList = new ArrayList<BoardCell>();
        for(int i = 0; i < newBoardState.length(); i++)
        {
            BoardCell newBoardCell = new BoardCell(newBoardState.substring(i,i+1));
            if(newBoardCell.getCurrentValue() != BoardCell.EMPTY_CELL)
            {
                newBoardCell.setInitialValue(true);
            }
            newList.add(newBoardCell);
        }
        return newList;
    }

    /*
     * When the user short presses a single board_cell:
     *  The current board_cell is marked as selected.
     *  The row and column that contain the board_cell become highlighted.
     *  If a value already exists in the board_cell, the corresponding button is highlighted.
     *  When a new button is pressed, that value is saved into the current board_cell.
     *  The newly pressed button is highlighted
    */
    private void setCurrentCellMainNumber(int newValue, int position)
    {
        this.boardCurrentStateBoardCellMap.get(position).setCurrentValue(newValue);
    }

    /*
     * When the user long presses a single board_cell:
     *  The current board_cell is marked as selected.
     *  The row and column that contain the board_cell become highlighted.
     *  If a
    */
    private void setCurrentCellNotes()
    {

    }

    /*
     * Checks to see if the user has compleatly filled the board.
     */
    private boolean isFull()
    {
        for( BoardCell c : this.boardCurrentStateBoardCellMap)
        {
            if(c.getCurrentValue() == c.EMPTY_CELL)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the current board state against the solver to determine correctness.
     */
    private boolean isBoardCorrect()
    {
        if(this.isFull() && this.isSolved())
        {
            if(this.boardCurrentStateBoardCellMap.toString().equals(this.boardSolvedState))
            {
                return true;
            }
        }
        return false;
    }


    /*
     * Asks the solver for a hint on a particular board_cell.
     */
    private void getHint(int xPos, int yPos)
    {
        if(this.isSolved)
        {
            int hintPosition = (xPos+1) * (yPos+1);
            this.boardCurrentStateBoardCellMap.get(hintPosition).setCurrentValue(Integer.valueOf(this.boardSolvedState.substring(hintPosition,hintPosition+1)));
            this.boardCurrentStateBoardCellMap.get(hintPosition).setHint(true);
        }
    }

    /*
     * Will output the boards current state as a string, example:
     * 4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......
     */
    @Override
    public String toString()
    {
        String output = "";
        for(BoardCell c : this.boardCurrentStateBoardCellMap)
        {
            if(c.getCurrentValue() != BoardCell.EMPTY_CELL)
            {
                output += String.valueOf(c.getCurrentValue());
            }
            else
            {
                output  += ".";
            }
        }
        return output;
    }

    public long getBoardID() {
        // TODO: ask database for nexst available boardID
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

    public ArrayList<BoardCell> getCurrentCellMap()
    {
        return this.boardCurrentStateBoardCellMap;
    }

    public String getBoardSolvedState() {
        return boardSolvedState;
    }

    public void setBoardSolvedState(String boardSolvedState) {
        this.boardSolvedState = boardSolvedState;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isSFull() {
        return isSFull;
    }

    public void setSFull(boolean SFull) {
        isSFull = SFull;
    }

}
