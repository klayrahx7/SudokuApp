package cpsc463sudoku.sudoku;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Mark Ballin on 2/12/2017.
 */


public class ControlPanelAdapter extends BaseAdapter{

    private Context context;
    private boolean undo;
    private boolean redo;
    private boolean erase;
    private boolean addNotes;
    private boolean getHint;
    private boolean checkSolution;
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;
    private boolean five;
    private boolean six;
    private boolean seven;
    private boolean eight;
    private boolean nine;
    private ArrayList<ControlPanelCell> controlPanelButtons;

    /*
     * Default Constructor
     */
    public ControlPanelAdapter(Context context)
    {
        controlPanelButtons = new ArrayList<ControlPanelCell>();
        this.context = context;

        ControlPanelCell numberOne = new ControlPanelCell(context, R.drawable.control_panel_blue_1);
        ControlPanelCell numberTwo = new ControlPanelCell(context, R.drawable.control_panel_blue_2);
        ControlPanelCell numberThree = new ControlPanelCell(context, R.drawable.control_panel_blue_3);
        ControlPanelCell numberFour = new ControlPanelCell(context, R.drawable.control_panel_blue_4);
        ControlPanelCell numberFive = new ControlPanelCell(context, R.drawable.control_panel_blue_5);
        ControlPanelCell numberSix = new ControlPanelCell(context, R.drawable.control_panel_blue_6);
        ControlPanelCell numberSeven = new ControlPanelCell(context, R.drawable.control_panel_blue_7);
        ControlPanelCell numberEight = new ControlPanelCell(context, R.drawable.control_panel_blue_8);
        ControlPanelCell numberNine = new ControlPanelCell(context, R.drawable.control_panel_blue_9);
        ControlPanelCell erase = new ControlPanelCell(context, R.drawable.control_panel_blue_erase);
        ControlPanelCell undo = new ControlPanelCell(context, R.drawable.control_panel_blue_undo);
        ControlPanelCell redo = new ControlPanelCell(context, R.drawable.control_panel_blue_redo);
        ControlPanelCell addNotes = new ControlPanelCell(context, R.drawable.control_panel_blue_set_notes);
        ControlPanelCell getHint = new ControlPanelCell(context, R.drawable.control_panel_blue_get_hint);
        ControlPanelCell solve = new ControlPanelCell(context, R.drawable.control_panel_blue_solve);

        // Row One
        controlPanelButtons.add(undo);
        controlPanelButtons.add(numberOne);
        controlPanelButtons.add(numberTwo);
        controlPanelButtons.add(numberThree);
        controlPanelButtons.add(addNotes);

        // Row two
        controlPanelButtons.add(redo);
        controlPanelButtons.add(numberFour);
        controlPanelButtons.add(numberFive);
        controlPanelButtons.add(numberSix);
        controlPanelButtons.add(getHint);

        // Row three
        controlPanelButtons.add(erase);
        controlPanelButtons.add(numberSeven);
        controlPanelButtons.add(numberEight);
        controlPanelButtons.add(numberNine);
        controlPanelButtons.add(solve);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cell;

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            cell = layoutInflater.inflate(R.layout.control_panel_cell, null);
            final int pos = position;
            ImageButton newButton = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            newButton.setPadding(1, 1, 1, 1);
            newButton.setImageResource(controlPanelButtons.get(position).getImageResource());
            switch (position)
            {
                case 0:
                    //cell.set
                    break;
            }
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "ControlPanel short click: " + pos);
                }
            });
            newButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("TAG", "ControlPanel long click: " + pos);
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

    @Override
    public long getItemId(int position) {
        return this.controlPanelButtons.get(position).getId();
    }

    @Override
    public int getCount() {
        return controlPanelButtons.size();
    }

    @Override
    public ControlPanelCell getItem(int position) {
        return this.controlPanelButtons.get(position);
    }

    public boolean isOne() {
        return one;
    }

    public void setOne(boolean one) {
        this.one = one;
    }

    public boolean isTwo() {
        return two;
    }

    public void setTwo(boolean two) {
        this.two = two;
    }

    public boolean isThree() {
        return three;
    }

    public void setThree(boolean three) {
        this.three = three;
    }

    public boolean isFour() {
        return four;
    }

    public void setFour(boolean four) {
        this.four = four;
    }

    public boolean isFive() {
        return five;
    }

    public void setFive(boolean five) {
        this.five = five;
    }

    public boolean isSix() {
        return six;
    }

    public void setSix(boolean six) {
        this.six = six;
    }

    public boolean isSeven() {
        return seven;
    }

    public void setSeven(boolean seven) {
        this.seven = seven;
    }

    public boolean isEight() {
        return eight;
    }

    public void setEight(boolean eight) {
        this.eight = eight;
    }

    public boolean isNine() {
        return nine;
    }

    public void setNine(boolean nine) {
        this.nine = nine;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isUndo() {
        return undo;
    }

    public void setUndo(boolean undo) {
        this.undo = undo;
    }

    public boolean isRedo() {
        return redo;
    }

    public void setRedo(boolean redo) {
        this.redo = redo;
    }

    public boolean isErase() {
        return erase;
    }

    public void setErase(boolean erase) {
        this.erase = erase;
    }
}
