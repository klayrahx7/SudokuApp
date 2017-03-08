package cpsc463sudoku.sudoku;


import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mark Ballin on 2/12/2017.
 */

public class BoardAdapter extends BaseAdapter implements Parcelable {

    public static int boardStatePosition;
    public static final int NUM_OF_ROWS = 9;
    public static final int NNUM_OF_COLS = 9;
    public static final int BOARD_MOVE_HISTORY_LIMIT = 1000;

    private Context context;
    private ArrayList<BoardCell> boardCellMap;
    private ArrayList<String> boardStateList;

    private long boardID;
    private String boardCurrentState;
    private String boardSolvedState;
    private boolean isSolved;
    private boolean isSFull;
    private boolean isComplete;
    private static boardCallback boardCallback;

    /*
     * Default constructor: Used when building a new game only.
     */
    public BoardAdapter(String newBoardState)
    {
        this.boardStatePosition = 0;
        this.boardID = -1;
        this.boardCurrentState = newBoardState;
        this.isSolved = false;
        this.isSFull = false;
        this.isComplete = false;
        this.boardStateList = new ArrayList<String>(Arrays.asList(new String[BOARD_MOVE_HISTORY_LIMIT]));
        this.boardStateList.set(boardStatePosition, newBoardState);
        this.boardCellMap = this.setCurrentCellMap(newBoardState);
    }

    /*
     * Parcelable constructor: used when adding this class to a bundle.
     */
    public BoardAdapter(Parcel in)
    {
        this.boardStatePosition = in.readInt();
        this.boardID = in.readLong();
        this.boardCurrentState = in.readString();
        this.boardSolvedState = in.readString();
        String[] tempBoardStateList = new String[BOARD_MOVE_HISTORY_LIMIT];
        in.readStringArray(tempBoardStateList);
        this.boardStateList = new ArrayList<String>(Arrays.asList(tempBoardStateList));
        this.boardCellMap = this.setCurrentCellMap(boardCurrentState);
        in.readBooleanArray(new boolean[]{this.isSolved, this.isSFull, this.isComplete});
    }

    /*
     * Fragment constructor: used when building this object from a fragment
     */
    public BoardAdapter(Context context, String newBoardState, boardCallback callback)
    {
        this.boardStatePosition = 0;
        this.context = context;
        boardCallback = callback;
        this.boardID = -1;
        this.boardCurrentState = newBoardState;
        this.isSolved = false;
        this.isSFull = false;
        this.isComplete = false;
        this.boardStateList = new ArrayList<String>(Arrays.asList(new String[BOARD_MOVE_HISTORY_LIMIT]));
        this.boardStateList.set(boardStatePosition, newBoardState);
        this.boardCellMap = this.setCurrentCellMap(newBoardState);
    }

    /*
     * Helper function for parcelable constructor.
     */
    public static final Parcelable.Creator<BoardAdapter> CREATOR = new Parcelable.Creator<BoardAdapter>()
    {
        public BoardAdapter createFromParcel(Parcel in) {
            return new BoardAdapter(in);
        }

        public BoardAdapter[] newArray(int size) {
            return new BoardAdapter[size];
        }
    };

    // Helper function for parcelable
    @Override
    public int describeContents()
    {
        return 1;
    }

    // Helper function for parcelable
    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(boardStatePosition);
        out.writeLong(this.boardID);
        out.writeString(this.boardCurrentState);
        out.writeString(this.boardSolvedState);
        out.writeStringArray(this.boardStateList.toArray(new String[BOARD_MOVE_HISTORY_LIMIT]));
        out.writeBooleanArray(new boolean[]{this.isSolved, this.isSFull, this.isComplete});
    }

    // Helper function for BaseAdapter
    @Override
    public int getCount()
    {
        return this.boardCellMap.size();
    }

    // Helper function for BaseAdapter
    @Override
    public BoardCell getItem(int position)
    {
        return this.boardCellMap.get(position);
    }

    // Helper function for BaseAdapter
    @Override
    public long getItemId(int position)
    {
        return this.boardCellMap.get(position).getId();
    }

    // Fragment callback
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

    // Inflate and build board grid view
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // Get current context to build in
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.board_cell, null);

        // Build and describe the look of the board cells (buttons)
        Button name = (Button) v.findViewById(R.id.grid_item);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) name.getLayoutParams();
        Resources res = context.getResources();
        int screen_width = res.getDisplayMetrics().widthPixels;
        int screen_height = res.getDisplayMetrics().heightPixels;
        params.width = screen_width / NNUM_OF_COLS;
        params.height = (int)(screen_height * 0.65) / NUM_OF_ROWS;
        final BoardCell currentCell = getCurrentCellMap().get(position);
        currentCell.setId((long)position);

        // Set default button parameters
        name.setLayoutParams(params);
        name.setTextColor(res.getColor(R.color.White));
        name.setTextSize(30);
        name.setPadding(0,0,0,0);
        name.setBackgroundColor(res.getColor(R.color.Transparent));

        // Display Notes
        if(currentCell.NOTES_SET)
        {
            LinearLayout.LayoutParams notesParam = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            notesParam.gravity = Gravity.LEFT;
            name.setLayoutParams(notesParam);
            name.setTextSize(10);
            int[] notes = currentCell.getUserNotes();
            String note = "";
            for(int i = 0; i < notes.length; i++)
            {
                note += String.valueOf(notes[i]);
            }
            name.setText(note);
        }

        // Highlighting for row and column
        if(currentCell.isHighlighted())
        {
            name.setTextColor(res.getColor(R.color.Black));
            name.setBackgroundColor(res.getColor(R.color.Highlighted));
        }

        // Highlighting for selected v
        if(currentCell.isSelected())
        {
            name.setBackgroundColor(res.getColor(R.color.Selected));
        }

        // Highlighting for hints
        if(currentCell.isHint())
        {
            name.setBackgroundColor(res.getColor(R.color.Hint));
        }

        // Highlighting initial buttons
        if(currentCell.isInitialValue()) {
            name.setTextColor(res.getColor(R.color.InitialValue));
        }

        // Display text on button
        if(currentCell.getCurrentValue() == 0)
        {
            name.setText(" ");
        }
        else
        {
            name.setText(String.valueOf(currentCell.getCurrentValue()));
        }

        // Build click listeners for each button
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "Board short click: " + currentCell.getId());
                setAllBoardHighlights(currentCell);
                boardCallback.cellClicked((int)currentCell.getId());
            }
        });
        name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG", "Board long click: " + position);

                return false;
            }
        });

        return v;
    }

    // Add new state to the state list
    public void advanceBoardState(String newState)
    {
        boardStatePosition++;
        this.boardStateList.set(boardStatePosition, newState);
        this.boardCurrentState = newState;
    }

    // If no changes have been make, redo the last applied state
    public void redoBoardState()
    {
        boardStatePosition++;
        this.boardCurrentState = this.boardStateList.get(boardStatePosition).toString();
        this.boardCellMap = setCurrentCellMap(this.boardCurrentState);
    }

    // Revert to the last game state, keep all old states until a change is made.
    public void undoBoardState()
    {
        boardStatePosition--;
        if(boardStatePosition >= 0)
        {
            this.boardCurrentState = this.boardStateList.get(boardStatePosition).toString();
            this.boardCellMap = setCurrentCellMap(this.boardCurrentState);
        }
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
            if(boardCurrentState.equals(boardStateList.get(0)))
            {
                if(newBoardCell.getCurrentValue() != BoardCell.EMPTY_CELL)
                {
                    newBoardCell.setInitialValue(true);
                    newBoardCell.setSolved(true);
                }
            }
            newList.add(newBoardCell);
        }
        return newList;
    }


    // Returns the  selected cell.
    // If there is none selected, returns an empty cell.
    public BoardCell getSelectedCell()
    {
        for(int i = 0 ; i < getBoardCurrentState().length(); i++)
        {
            if(this.boardCellMap.get(i).isSelected())
            {
                return this.boardCellMap.get(i);
            }
        }
        return new BoardCell();
    }

    /*
     * Highlights the following:
     *      Selected item
     *      All cells with the same value as the selected cell.
     *      Current row and column
     */
    public void setAllBoardHighlights(BoardCell cell)
    {
        removeBoardHighlighting();
        cell.setSelected(true);
        setIsHighlightedRowAndColumn(cell, true);
        setIsHighlightedSimilarTiles(cell, true);
    }

    /*
     * Removes the following highlighting:
     * Row and column
     * Selected cell
     * Cells similar to the selected cell.
     */
    private void removeBoardHighlighting()
    {
        for(int i = 0 ; i < getBoardCurrentState().length(); i++)
        {
            this.boardCellMap.get(i).setHighlighted(false);
            this.boardCellMap.get(i).setSelected(false);
        }
    }

    /*
     * Highlights cells who share a current value with the given cell.
     */
    private void setIsHighlightedSimilarTiles(BoardCell cell, boolean flag)
    {
        // Make sure we dont ever highlight empty cells
        if(cell.getCurrentValue() != BoardCell.EMPTY_CELL)
        {
            // Search entire list
            for(int i = 0 ; i < getBoardCurrentState().length(); i++)
            {
                //Check for similar tile
                if(this.boardCellMap.get(i).getCurrentValue() == cell.getCurrentValue())
                {
                    this.boardCellMap.get(i).setHighlighted(flag);
                }

            }
        }
    }

    /*
     * When the user short clicks a square in the board it should highlight the board and its corresponding row and column
     */
    private void setIsHighlightedRowAndColumn(BoardCell cell, boolean flag)
    {
        int[] columnsToHighlight = getColumn((int)cell.getId());
        int[] rowsToHighlight = getRow((int)cell.getId());

        // Highlight currently selected row
        for(int i = 0 ; i < columnsToHighlight.length; i++)
        {
            this.boardCellMap.get(columnsToHighlight[i]).setHighlighted(flag);
        }

        // Highlight currently selected column
        for(int i = 0 ; i < rowsToHighlight.length; i++)
        {
            this.boardCellMap.get(rowsToHighlight[i]).setHighlighted(flag);
        }
    }

    // Helper function to get row positions
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

    // Helper function to get the current square of the selected item
    private final int[] getSquare(int position)
    {
        final int[] squareOne = new int[] { 0,1,2,9,10,11,18,19,20 };
        final int[] squareTwo = new int[] { 3,4,5,12,13,14,21,22,23 };
        final int[] squareThree = new int[] { 6,7,8,15,16,17,24,25,26 };
        final int[] squareFour = new int[] { 27,28,29,36,37,38,45,46,47 };
        final int[] squareFive = new int[] { 30,31,32,39,40,41,48,49,50 };
        final int[] squareSix = new int[] { 33,34,35,42,43,44,51,52,53 };
        final int[] squareSeven = new int[] { 54,55,56,63,64,65,72,73,74 };
        final int[] squareEight = new int[] { 57,58,59,66,67,68,75,76,77 };
        final int[] squareNine = new int[] { 60,61,62,69,70,71,78,79,80 };

        for(int i = 0; i < 9; i++)
        {
            if(squareOne[i] == position)
            {
                return squareOne;
            }
            if(squareTwo[i] == position)
            {
                return squareTwo;
            }
            if(squareThree[i] == position)
            {
                return squareThree;
            }
            if(squareFour[i] == position)
            {
                return squareFour;
            }
            if(squareFive[i] == position)
            {
                return squareFive;
            }
            if(squareSix[i] == position)
            {
                return squareSix;
            }
            if(squareSeven[i] == position)
            {
                return squareSeven;
            }
            if(squareEight[i] == position)
            {
                return squareEight;
            }
            if(squareNine[i] == position)
            {
                return squareNine;
            }
        }
        return new int[] {-1,-1,-1,-1,-1,-1,-1,-1,-1};

    }

    // Helper function to get column positions
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

        // Debug code
        Log.d("GetColumn ",String.valueOf(position));
        for(int i = 0 ; i < outputSol.length; i++)
        {
            outputSol[i] = temp[i];
            Log.d(String.valueOf(i), String.valueOf(outputSol[i]));
        }
        return outputSol;
    }

    /*
     * Checks to see if the user has fully filled the board.
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

    public long getBoardID() {
        // TODO: ask database for nexst available boardID
        return boardID;
    }

    public void setBoardID(long boardID) {
        this.boardID = boardID;
    }

    public ArrayList<String> getBoardStateList() {
        return boardStateList;
    }

    public void setBoardStateList(ArrayList<String> boardStateList) {
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
