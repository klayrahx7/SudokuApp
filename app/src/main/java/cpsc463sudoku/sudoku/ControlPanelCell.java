package cpsc463sudoku.sudoku;

import android.content.Context;
import android.widget.ImageButton;

class ControlPanelCell {
    private long id;
    private boolean isHighlighted;
    private int imageResource;
    private ImageButton cellButton;

    public ControlPanelCell()
    {
        this.isHighlighted = false;
        this.cellButton = new ImageButton(null);
    }

    ControlPanelCell(Context context)
    {
        this.isHighlighted = false;
        this.cellButton = new ImageButton(context);
    }

    public int getImageResource() {
        return imageResource;
    }

    void setImageResource(int imageResource) {
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

    boolean isHighlighted() {
        return isHighlighted;
    }

    void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }
}
