package cpsc463sudoku.sudoku;

/**
 * Created by Blacklotis on 2/20/2017.
 */

public class ControlPanelCell {
    private long id;
    private boolean isHighlighted;
    private int imageResource;

    public ControlPanelCell()
    {
        this.isHighlighted = false;
        this.imageResource = -1;
    }

    public ControlPanelCell(int newImage)
    {
        this.isHighlighted = false;
        this.imageResource = newImage;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }
}
