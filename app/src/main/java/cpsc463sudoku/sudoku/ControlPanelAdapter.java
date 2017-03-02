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
    private static controlPanelCallback panelCallback;

    public static final boolean buttonBlue = false;
    public static final boolean buttonRed = true;
    public static final int UNDO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int NOTES = 4;
    public static final int REDO = 5;
    public static final int FOUR = 6;
    public static final int FIVE = 7;
    public static final int SIX = 8;
    public static final int HINT = 9;
    public static final int ERASE = 10;
    public static final int SEVEN = 11;
    public static final int EIGHT = 12;
    public static final int NINE = 13;
    public static final int SOLVE = 14;

    /*
     * Default Constructor
     */
    public ControlPanelAdapter(Context context, controlPanelCallback callback )
    {
        this.controlPanelState = new ArrayList<ControlPanelCell>();
        this.controlPanelButtonsBlue = new int[NUM_OF_BUTTONS];
        this.controlPanelButtonsRed = new int[NUM_OF_BUTTONS];
        this.context = context;
        panelCallback = callback;

        //Make blue buttons
        // Row One
        this.controlPanelButtonsBlue[UNDO] =  R.drawable.control_panel_blue_undo;
        this.controlPanelButtonsBlue[ONE] =  R.drawable.control_panel_blue_1;
        this.controlPanelButtonsBlue[TWO] =  R.drawable.control_panel_blue_2;
        this.controlPanelButtonsBlue[THREE] =  R.drawable.control_panel_blue_3;
        this.controlPanelButtonsBlue[NOTES] =  R.drawable.control_panel_blue_set_notes;

        // Row two
        this.controlPanelButtonsBlue[REDO] =  R.drawable.control_panel_blue_redo;
        this.controlPanelButtonsBlue[FOUR] =  R.drawable.control_panel_blue_4;
        this.controlPanelButtonsBlue[FIVE] =  R.drawable.control_panel_blue_5;
        this.controlPanelButtonsBlue[SIX] =  R.drawable.control_panel_blue_6;
        this.controlPanelButtonsBlue[HINT] =  R.drawable.control_panel_blue_get_hint;

        // Row three
        this.controlPanelButtonsBlue[ERASE] =  R.drawable.control_panel_blue_erase;
        this.controlPanelButtonsBlue[SEVEN] =  R.drawable.control_panel_blue_7;
        this.controlPanelButtonsBlue[EIGHT] =  R.drawable.control_panel_blue_8;
        this.controlPanelButtonsBlue[NINE] =  R.drawable.control_panel_blue_9;
        this.controlPanelButtonsBlue[SOLVE] =  R.drawable.control_panel_blue_solve;

        //Make red buttons
        // Row One
        this.controlPanelButtonsRed[UNDO] = R.drawable.control_panel_red_undo;
        this.controlPanelButtonsRed[ONE]  = R.drawable.control_panel_red_1;
        this.controlPanelButtonsRed[TWO]  = R.drawable.control_panel_red_2;
        this.controlPanelButtonsRed[THREE]= R.drawable.control_panel_red_3;
        this.controlPanelButtonsRed[NOTES]= R.drawable.control_panel_red_set_notes;

        // Row two
        this.controlPanelButtonsRed[REDO] = R.drawable.control_panel_red_redo;
        this.controlPanelButtonsRed[FOUR] = R.drawable.control_panel_red_4;
        this.controlPanelButtonsRed[FIVE] = R.drawable.control_panel_red_5;
        this.controlPanelButtonsRed[SIX]  = R.drawable.control_panel_red_6;
        this.controlPanelButtonsRed[HINT] = R.drawable.control_panel_red_get_hint;

        // Row three
        this.controlPanelButtonsRed[ERASE] = R.drawable.control_panel_red_erase;
        this.controlPanelButtonsRed[SEVEN] = R.drawable.control_panel_red_7;
        this.controlPanelButtonsRed[EIGHT] = R.drawable.control_panel_red_8;
        this.controlPanelButtonsRed[NINE]  = R.drawable.control_panel_red_9;
        this.controlPanelButtonsRed[SOLVE] = R.drawable.control_panel_red_solve;

        setControlPanelState(new boolean[]
        {
            buttonBlue,buttonBlue,buttonBlue,buttonBlue,buttonBlue,
            buttonBlue,buttonBlue,buttonBlue,buttonBlue,buttonBlue,
            buttonBlue,buttonBlue,buttonBlue,buttonBlue,buttonBlue
        });
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
                setButtonState(position, buttonRed);
                panelCallback.panelClicked(position);
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

    public interface controlPanelCallback
    {
        void panelClicked(int position);
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
                newCell.setHighlighted(buttonRed);
                newCell.setImageResource(this.controlPanelButtonsRed[i]);
            }
            newCell.setHighlighted(buttonBlue);
            newCell.setImageResource(this.controlPanelButtonsBlue[i]);

            newList.add(newCell);
        }
        this.controlPanelState = newList;
    }

    public void setAllButtonsBlue()
    {
        for(int i = 0; i < NUM_OF_BUTTONS; i++)
        {
            setButtonState(i,buttonBlue);
        }
    }
    public void setAllButtonsRed()
    {
        for(int i = 0; i < NUM_OF_BUTTONS; i++)
        {
            setButtonState(i,buttonRed);
        }
    }

    public void setButtonState(int position, boolean flag)
    {
        this.controlPanelState.get(position).setHighlighted(flag);
        if(flag == buttonRed)
        {
            this.controlPanelState.get(position).setImageResource(this.controlPanelButtonsRed[position]);
        }
        else
        {
            this.controlPanelState.get(position).setImageResource(this.controlPanelButtonsBlue[position]);
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
