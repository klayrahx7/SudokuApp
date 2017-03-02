package cpsc463sudoku.sudoku;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by MarkBallin on 2/13/2017.
 */

public class BoardCell implements Parcelable
{
    public static final int EMPTY_CELL = 0;
    public static final int NUM_NOTES = 9;
    public static boolean NOTES_SET;
    private long id;
    private int currentValue;
    private boolean isHighlighted;
    private boolean isSelected;
    private boolean isInitialValue;
    private boolean isHint;
    private boolean isSolved;
    private boolean isCorrect;
    private int[] userNotes;

    public BoardCell()
    {
        this.id = -1;
        this.NOTES_SET = false;
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
        this.id = in.readLong();
        this.currentValue = in.readInt();
        boolean[] bools = {this.isHighlighted, this.isSelected, this.isInitialValue, this.isHint, this.isSolved, this.isCorrect, this.NOTES_SET};
        in.readBooleanArray(bools);
        this.userNotes = new int[NUM_NOTES];
        in.readIntArray(this.userNotes);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeInt(this.currentValue);
        out.writeBooleanArray(new boolean[]{this.isHighlighted, this.isSelected, this.isInitialValue, this.isHint, this.isSolved, this.isCorrect, this.NOTES_SET});
        out.writeIntArray(this.userNotes);
    }

    @Override
    public int describeContents()
    {
        return 1;
    }

    public BoardCell(String newCurrentValue)
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
        this.NOTES_SET = newBoardCell.NOTES_SET;
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

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int[] getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(int[] userNotes) {
        this.userNotes = userNotes;
    }

    public boolean isInitialValue() {
        return isInitialValue;
    }

    public void setInitialValue(boolean initialValue) {
        isInitialValue = initialValue;
    }

    public boolean isHint() {
        return isHint;
    }

    public void setHint(boolean hint) {
        isHint = hint;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
