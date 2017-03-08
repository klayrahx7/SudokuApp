package cpsc463sudoku.sudoku;

import android.os.Parcel;
import android.os.Parcelable;

class BoardCell implements Parcelable
{
    static final int EMPTY_CELL = -1;
    static final int NUM_NOTES = 9;

    int id;
    int currentValue;
    boolean isHighlighted;
    boolean isSelected;
    boolean isInitialValue;
    boolean isHint;
    boolean isSolved;
    boolean isCorrect;
    boolean isNotesSet;
    int[] userNotes;

    BoardCell()
    {
        isNotesSet = false;
        this.id = -1;
        this.currentValue = EMPTY_CELL;
        this.isHighlighted = false;
        this.isSelected = false;
        this.userNotes = new int[NUM_NOTES];
    }

    public static final Parcelable.Creator<BoardCell> CREATOR = new Parcelable.Creator<BoardCell>()
    {
        public BoardCell createFromParcel(Parcel in) {
            return new BoardCell(in);
        }

        public BoardCell[] newArray(int size) {
            return new BoardCell[size];
        }
    };

    private BoardCell(Parcel in)
    {
        this.id = in.readInt();
        this.currentValue = in.readInt();
        boolean[] bools = {this.isHighlighted, this.isSelected, this.isInitialValue, this.isHint, this.isSolved, this.isCorrect, isNotesSet};
        in.readBooleanArray(bools);
        this.userNotes = new int[NUM_NOTES];
        in.readIntArray(this.userNotes);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeInt(this.currentValue);
        out.writeBooleanArray(new boolean[]{this.isHighlighted, this.isSelected, this.isInitialValue, this.isHint, this.isSolved, this.isCorrect, isNotesSet});
        out.writeIntArray(this.userNotes);
    }

    @Override
    public int describeContents()
    {
        return 1;
    }

    BoardCell(String newCurrentValue)
    {
        if( isInteger(newCurrentValue) )
        {
            this.currentValue = Integer.valueOf(newCurrentValue);
        }
        else
        {
            this.currentValue = EMPTY_CELL;
        }
        this.isHighlighted = false;
        this.isSelected = false;
        this.userNotes = new int[] {EMPTY_CELL,EMPTY_CELL,EMPTY_CELL,EMPTY_CELL,EMPTY_CELL,EMPTY_CELL,EMPTY_CELL,EMPTY_CELL,EMPTY_CELL};
    }

    public BoardCell(BoardCell newBoardCell)
    {
        this.currentValue = newBoardCell.getCurrentValue();
        this.isHighlighted = newBoardCell.isHighlighted();
        this.isSelected = newBoardCell.isSelected();
        this.userNotes = newBoardCell.getUserNotes();
    }

    private boolean isInteger(String s)
    {
        return isInteger(s,10);
    }

    private boolean isInteger(String s, int radix)
    {
        if(s.isEmpty())
        {
            return false;
        }
        for(int i = 0; i < s.length(); i++)
        {
            if(i == 0 && s.charAt(i) == '-')
            {
                if(s.length() == 1)
                {
                    return false;
                }
                else
                {
                    continue;
                }
            }
            if(Character.digit(s.charAt(i),radix) < 0)
            {
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int getCurrentValue() {
        return currentValue;
    }

    void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    boolean isHighlighted() {
        return isHighlighted;
    }

    void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }

    int[] getUserNotes() {
        return userNotes;
    }

    void setUserNotes(int[] userNotes) {
        this.userNotes = userNotes;
    }

    boolean isInitialValue() {
        return isInitialValue;
    }

    void setInitialValue(boolean initialValue) {
        isInitialValue = initialValue;
    }

    boolean isHint() {
        return isHint;
    }

    void setHint(boolean hint) {
        isHint = hint;
    }

    boolean isSolved() {
        return isSolved;
    }

    void setSolved(boolean solved) {
        isSolved = solved;
    }

    boolean isCorrect() {
        return isCorrect;
    }

    void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
