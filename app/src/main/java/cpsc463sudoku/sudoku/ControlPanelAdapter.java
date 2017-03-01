package cpsc463sudoku.sudoku;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;

/**
 * Created by Mark Ballin on 2/12/2017.
 */


public class ControlPanelAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ControlPanelCell> controlPanelState;
    private int[] controlPanelButtonsBlue;
    private int[] controlPanelButtonsRed;
    private final int NUM_OF_BUTTONS = 15;
    /*
     * Default Constructor
     */
    public ControlPanelAdapter(Context context)
    {
        this.controlPanelState = new ArrayList<ControlPanelCell>();
        this.controlPanelButtonsBlue = new int[NUM_OF_BUTTONS];
        this.controlPanelButtonsRed = new int[NUM_OF_BUTTONS];
        this.context = context;

        //Make blue buttons
        // Row One
        this.controlPanelButtonsBlue[0] =  R.drawable.control_panel_blue_undo;
        this.controlPanelButtonsBlue[1] =  R.drawable.control_panel_blue_1;
        this.controlPanelButtonsBlue[2] =  R.drawable.control_panel_blue_2;
        this.controlPanelButtonsBlue[3] =  R.drawable.control_panel_blue_3;
        this.controlPanelButtonsBlue[4] =  R.drawable.control_panel_blue_set_notes;

        // Row two
        this.controlPanelButtonsBlue[5] =  R.drawable.control_panel_blue_redo;
        this.controlPanelButtonsBlue[6] =  R.drawable.control_panel_blue_4;
        this.controlPanelButtonsBlue[7] =  R.drawable.control_panel_blue_5;
        this.controlPanelButtonsBlue[8] =  R.drawable.control_panel_blue_6;
        this.controlPanelButtonsBlue[9] =  R.drawable.control_panel_blue_get_hint;

        // Row three
        this.controlPanelButtonsBlue[10] =  R.drawable.control_panel_blue_erase;
        this.controlPanelButtonsBlue[11] =  R.drawable.control_panel_blue_7;
        this.controlPanelButtonsBlue[12] =  R.drawable.control_panel_blue_8;
        this.controlPanelButtonsBlue[13] =  R.drawable.control_panel_blue_9;
        this.controlPanelButtonsBlue[14] =  R.drawable.control_panel_blue_solve;

        //Make red buttons
        // Row One
        this.controlPanelButtonsRed[0] = R.drawable.control_panel_red_undo;
        this.controlPanelButtonsRed[1] = R.drawable.control_panel_red_1;
        this.controlPanelButtonsRed[2] = R.drawable.control_panel_red_2;
        this.controlPanelButtonsRed[3] = R.drawable.control_panel_red_3;
        this.controlPanelButtonsRed[4] = R.drawable.control_panel_red_set_notes;

        // Row two
        this.controlPanelButtonsRed[5] = R.drawable.control_panel_red_redo;
        this.controlPanelButtonsRed[6] = R.drawable.control_panel_red_4;
        this.controlPanelButtonsRed[7] = R.drawable.control_panel_red_5;
        this.controlPanelButtonsRed[8] = R.drawable.control_panel_red_6;
        this.controlPanelButtonsRed[9] = R.drawable.control_panel_red_get_hint;

        // Row three
        this.controlPanelButtonsRed[10] = R.drawable.control_panel_red_erase;
        this.controlPanelButtonsRed[11] = R.drawable.control_panel_red_7;
        this.controlPanelButtonsRed[12] = R.drawable.control_panel_red_8;
        this.controlPanelButtonsRed[13] = R.drawable.control_panel_red_9;
        this.controlPanelButtonsRed[14] = R.drawable.control_panel_red_solve;

        setControlPanelState(new boolean[]
                {
                        false,false,false,false,false,
                        false,false,false,false,false,
                        false,false,false,false,false
                });
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View cell = layoutInflater.inflate(R.layout.control_panel_cell, null);

        ImageButton newButton = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
        newButton.setPadding(1, 1, 1, 1);

        final ControlPanelCell currentCell = controlPanelState.get(position);
        if(currentCell.isHighlighted())
        {
            newButton.setImageResource(controlPanelButtonsRed[position]);
        }
        else
        {
            newButton.setImageResource(controlPanelButtonsBlue[position]);
        }
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "ControlPanel short click: " + position);
                setAllButtonsBlue();
                setButtonState(position, true);
                notifyDataSetChanged();
            }
        });
        newButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("TAG", "ControlPanel long click: " + position);
                return false;
            }
        });
        return cell;
    }

    public ArrayList<ControlPanelCell> getControlPanelState() {
        return controlPanelState;
    }

    public void setControlPanelState(boolean[] newState)
    {
        ArrayList<ControlPanelCell> newList = new ArrayList<ControlPanelCell>();
        for(int i = 0; i < NUM_OF_BUTTONS; i++)
        {
            ControlPanelCell newCell = new ControlPanelCell(getContext());
            if(newState[i])
            {
                newCell.setHighlighted(true);
                newCell.setImageResource(this.controlPanelButtonsRed[i]);
            }
            newCell.setHighlighted(false);
            newCell.setImageResource(this.controlPanelButtonsBlue[i]);

            newList.add(newCell);
        }
        this.controlPanelState = newList;
    }

    public void setAllButtonsBlue()
    {
        for(int i = 0; i < NUM_OF_BUTTONS; i++)
        {
            setButtonState(i,false);
        }
    }

    public void setButtonState(int position, boolean flag)
    {
        this.controlPanelState.get(position).setHighlighted(flag);
        if(flag)
        {
            this.controlPanelState.get(position).setImageResource(this.controlPanelButtonsRed[position]);
        }
        else
        {
            this.controlPanelState.get(position).setImageResource(this.controlPanelButtonsBlue[position]);
        }
    }

    @Override
    public long getItemId(int position) {
        return this.controlPanelState.get(position).getId();
    }

    @Override
    public int getCount() {
        return controlPanelState.size();
    }

    @Override
    public ControlPanelCell getItem(int position) {
        return this.controlPanelState.get(position);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
