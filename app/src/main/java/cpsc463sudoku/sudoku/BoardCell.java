package cpsc463sudoku.sudoku;

/**
 * Created by MarkBallin on 2/13/2017.
 */

public class BoardCell
{
    public static final int EMPTY_CELL = 0;
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
        this.currentValue = EMPTY_CELL;
        this.isHighlighted = false;
        this.isSelected = false;
        this.userNotes = new int[9];
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
    }

    public BoardCell(int newCurrentValue, boolean isHighlighted, boolean isSelected, int[] userNotes)
    {
        this.currentValue = newCurrentValue;
        this.isHighlighted = isHighlighted;
        this.isSelected = isSelected();
        this.userNotes = userNotes;
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
