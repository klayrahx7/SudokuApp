package cpsc463sudoku.sudoku;

import android.content.Context;
import android.widget.ImageButton;

/**
 * Created by Blacklotis on 2/20/2017.
 */

public class ControlPanelCell {
    private long id;
    private boolean isHighlighted;
    private int imageResource;
    private ImageButton cellButton;

    public ControlPanelCell()
    {
        this.isHighlighted = false;
        this.imageResource = -1;
        this.cellButton = new ImageButton(null);
    }

    public ControlPanelCell(Context context)
    {
        this.isHighlighted = false;
        this.imageResource = -1;
        this.cellButton = new ImageButton(context);
        this.cellButton.setImageResource(-1);
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public ImageButton getCellButton() {
        return cellButton;
    }

    public void setCellButton(ImageButton cellButton) {
        this.cellButton = cellButton;
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
