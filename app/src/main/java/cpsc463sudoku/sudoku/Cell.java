package cpsc463sudoku.sudoku;

/**
 * Created by MarkBallin on 2/13/2017.
 */

public class Cell {
    private int currentValue;
    private boolean isHighlighted;
    private boolean isSelected;
    private boolean isStartingValue;
    private boolean isHint;
    private boolean isSolved;
    private int[] possibleValues;

    public Cell()
    {
        this.currentValue = 0;
        this.isHighlighted = false;
        this.isSelected = false;
        this.possibleValues = new int[9];
    }

    public Cell(String newCurrentValue)
    {
        if( isInteger(newCurrentValue) )
        {
            this.currentValue = Integer.valueOf(newCurrentValue);
        }
        else
        {
            this.currentValue = -1;
        }
        this.isHighlighted = false;
        this.isSelected = false;
        this.possibleValues = new int[9];
    }

    public Cell(Cell newCell)
    {
        this.currentValue = newCell.getCurrentValue();
        this.isHighlighted = newCell.isHighlighted();
        this.isSelected = newCell.isSelected();
        this.possibleValues = new int[9];
    }

    public Cell(int newCurrentValue, boolean isHighlighted, boolean isSelected, int[] possibleValues)
    {
        this.currentValue = newCurrentValue;
        this.isHighlighted = isHighlighted;
        this.isSelected = isSelected();
        this.possibleValues = possibleValues;
    }

    private boolean isInteger(String s) {
        return isInteger(s,10);
    }

    private boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
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

    public int[] getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(int[] possibleValues) {
        this.possibleValues = possibleValues;
    }

    public boolean isStartingValue() {
        return isStartingValue;
    }

    public void setStartingValue(boolean startingValue) {
        isStartingValue = startingValue;
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
}
