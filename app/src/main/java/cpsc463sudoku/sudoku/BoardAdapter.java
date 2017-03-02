package cpsc463sudoku.sudoku;


import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mark Ballin on 2/12/2017.
 */

public class BoardAdapter extends BaseAdapter implements Parcelable {

    private Context context;
    private ArrayList<BoardCell> boardCellMap;
    private long boardID;
    private final int BOARD_MOVE_HISTORY_LIMIT = 1000;
    private String boardInitialState;
    private String boardCurrentState;
    private String boardSolvedState;
    private String[] boardStateList;
    private boolean isSolved;
    private boolean isSFull;
    private boolean isComplete;
    private static boardCallback boardCallback;

    /*
     * Constructor from state string
     */
    public BoardAdapter(String newBoardState)
    {
        this.boardID = -1;
        this.boardInitialState = newBoardState;
        this.boardCurrentState = newBoardState;
        this.isSolved = false;
        this.isSFull = false;
        this.isComplete = false;
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCellMap = this.setCurrentCellMap(newBoardState);
    }

    public BoardAdapter(Parcel in)
    {
        this.boardID = in.readLong();
        this.boardInitialState = in.readString();
        this.boardCurrentState = in.readString();
        this.boardSolvedState = in.readString();
        in.readStringArray(this.boardStateList);
        this.boardCellMap = this.setCurrentCellMap(boardCurrentState);
        in.readBooleanArray(new boolean[]{this.isSolved, this.isSFull, this.isComplete});
    }

    public BoardAdapter(Context context, String newBoardState, boardCallback callback)
    {
        this.context = context;
        boardCallback = callback;
        this.boardID = -1;
        this.boardInitialState = newBoardState;
        this.boardCurrentState = newBoardState;
        this.isSolved = false;
        this.isSFull = false;
        this.isComplete = false;
        this.boardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        this.boardCellMap = this.setCurrentCellMap(newBoardState);
    }

    public static final Parcelable.Creator<BoardAdapter> CREATOR = new Parcelable.Creator<BoardAdapter>()
    {
        public BoardAdapter createFromParcel(Parcel in) {
            return new BoardAdapter(in);
        }

        public BoardAdapter[] newArray(int size) {
            return new BoardAdapter[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 1;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeLong(this.boardID);
        out.writeString(this.boardInitialState);
        out.writeString(this.boardCurrentState);
        out.writeString(this.boardSolvedState);
        out.writeStringArray(this.boardStateList);
        out.writeBooleanArray(new boolean[]{this.isSolved, this.isSFull, this.isComplete});
    }

    @Override
    public int getCount()
    {
        return this.boardCellMap.size();
    }

    @Override
    public BoardCell getItem(int position)
    {
        return this.boardCellMap.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return this.boardCellMap.get(position).getId();
    }

    public interface boardCallback
    {
        void cellClicked(int position);
    }

    /*
    * Will output the boards current state as a string, example:
    * 4.....8.5.3..........7......2.....6.....8.4......1.......6.3.7.5..2.....1.4......
    */
    @Override
    public String toString()
    {
        String output = "";
        for(BoardCell c : this.boardCellMap)
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View cell = layoutInflater.inflate(R.layout.board_cell, null);

        Button name = (Button) cell.findViewById(R.id.grid_item);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) name.getLayoutParams();
        Resources res = context.getResources();
        int screen_width = res.getDisplayMetrics().widthPixels;
        int screen_height = res.getDisplayMetrics().heightPixels;
        params.width = screen_width / 9;
        params.height = (int)(screen_height * 0.65) / 9;
        name.setLayoutParams(params);
        name.setTextSize(30);
        name.setPadding(0,0,0,0);
        name.setBackgroundColor(res.getColor(R.color.Transparent));

        final BoardCell currentCell = getCurrentCellMap().get(position);
        currentCell.setId((long)position);

        if(currentCell.isHighlighted())
        {
            name.setBackgroundColor(res.getColor(R.color.Highlighted));
            name.setTextColor(res.getColor(R.color.Black));
        }
        if(currentCell.isSelected())
        {
            name.setBackgroundColor(res.getColor(R.color.Selected));
        }
        if(currentCell.getCurrentValue() == 0)
        {
            name.setText(" ");
        }
        else
        {
            name.setText(String.valueOf(currentCell.getCurrentValue()));
        }
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Board short click: " + position);
                setAllBoardHighlights(position, true);
                currentCell.setSelected(true);
                boardCallback.cellClicked(position);
            }
        });
        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG", "Board long click: " + position);

                return false;
            }
        });

        return cell;
    }

    // Figured out the value of the currently selected square.
    public void setSelectedItemValue(int newValue)
    {
        for(int i = 0; i < boardCellMap.size(); i++)
        {
            if(boardCellMap.get(i).isSelected())
            {
                boardCellMap.get(i).setCurrentValue(newValue);
            }
        }
    }

    // Figured out the value of the currently selected square.
    public int getSelectedItemValue()
    {
        for(int i = 0; i < boardCellMap.size(); i++)
        {
            if(boardCellMap.get(i).isSelected())
            {
                return boardCellMap.get(i).getCurrentValue();
            }
        }
        return BoardCell.EMPTY_CELL;
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

    private void setAllBoardHighlights(int position, boolean flag)
    {
        removeAllCellEffects(false);
        setIsHighlightedRowAndColumn(position, true);
        setIsHighlightedSimiliarTiles(position, true);
    }

    /*
      Set all highlighting
     */
    private void removeAllCellEffects(boolean flag)
    {
        for(int i = 0 ; i < getBoardCurrentState().length(); i++)
        {
            this.boardCellMap.get(i).setHighlighted(flag);
            this.boardCellMap.get(i).setSelected(flag);
        }
    }

    /*
     * When the user short clicks a square in the board it should highlight the board and its corresponding row and column
     */
    private void setIsHighlightedSimiliarTiles(int position, boolean flag)
    {
        for(int i = 0 ; i < getBoardCurrentState().length(); i++)
        {
            if(this.boardCellMap.get(i).getCurrentValue() == this.boardCellMap.get(position).getCurrentValue())
            {
                if(this.boardCellMap.get(i).getCurrentValue() != BoardCell.EMPTY_CELL)
                {
                    this.boardCellMap.get(i).setHighlighted(flag);
                }

            }

        }
    }

    /*
     * When the user short clicks a square in the board it should highlight the board and its corresponding row and column
     */
    private void setIsHighlightedRowAndColumn(int position, boolean flag)
    {
        int[] columnsToHighlight = getColumn(position);
        int[] rowsToHighlight = getRow(position);

        // Highlight currently selected row
        for(int i = 0 ; i < columnsToHighlight.length; i++)
        {
            this.boardCellMap.get(columnsToHighlight[i]).setHighlighted(flag);
        }

        // Highlight currently selected rcolumn
        for(int i = 0 ; i < rowsToHighlight.length; i++)
        {
            this.boardCellMap.get(rowsToHighlight[i]).setHighlighted(flag);
        }
    }

    private int[] getRow(int position)
    {
        int[] solution = new int[9];
        int count = 0;
        int i = position;
        if(position % 9 != 0)
        {
            do
            {
                i--;
                solution[count] = i;
                count++;
            }while(i % 9 != 0);
            i = position;
        }
        else
        {
            solution[count] = position;
            count++;
            i = position+1;
        }

        do
        {
            solution[count] = i;
            count++;
            i++;
        }while(i % 9 != 0);
        Log.d("GetRow ",String.valueOf(position));
        for(i = 0; i < solution.length; i++)
        {
            Log.d(String.valueOf(i), String.valueOf(solution[i]));
        }
        return solution;
    }

    private int[] getColumn(int position)
    {
        Set<Integer> solution = new HashSet<Integer>() {};
        for(int i = position; i >= 0; i-=9)
        {
            solution.add(i);
        }
        for(int i = position; i <= 80; i+=9)
        {
            solution.add(i);
        }

        int[] outputSol = new int[solution.size()];
        Integer[] temp = solution.toArray(new Integer[solution.size()]);
        Log.d("GetColumn ",String.valueOf(position));
        for(int i = 0 ; i < outputSol.length; i++)
        {
            outputSol[i] = temp[i];
            Log.d(String.valueOf(i), String.valueOf(outputSol[i]));
        }
        return outputSol;
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
        this.boardCellMap.get(position).setCurrentValue(newValue);
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
        for( BoardCell c : this.boardCellMap)
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
            if(this.boardCellMap.toString().equals(this.boardSolvedState))
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
            this.boardCellMap.get(hintPosition).setCurrentValue(Integer.valueOf(this.boardSolvedState.substring(hintPosition,hintPosition+1)));
            this.boardCellMap.get(hintPosition).setHint(true);
        }
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
        return this.boardCellMap;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
