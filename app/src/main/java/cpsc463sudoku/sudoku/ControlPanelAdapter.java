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
            ImageButton undo = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton redo = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton erase = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton addNotes = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton getHint = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton checkSolution = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton one = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton two = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton three = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton four = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton five = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton six = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton seven = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton eight = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            ImageButton nine = (ImageButton) cell.findViewById(R.id.conrolPanelItem);
            undo.setText(R.string.undo);
            redo.setText(R.string.redo);
            erase.setText(R.string.erase);
            addNotes.setText(R.string.addNotes);
            getHint.setText(R.string.getHint);
            checkSolution.setText(R.string.checkSolution);
            one.setTextSize(R.string.one);
            two.setTextSize(R.string.two);
            three.setTextSize(R.string.three);
            four.setTextSize(R.string.four);
            five.setTextSize(R.string.five);
            six.setTextSize(R.string.six);
            seven.setTextSize(R.string.seven);
            eight.setTextSize(R.string.eight);
            nine.setTextSize(R.string.nine);
    */

    /*
     * Default Constructor
     */
    public ControlPanelAdapter(Context context)
    {
        controlPanelButtons = new ArrayList<ControlPanelCell>();
        this.context = context;

        ControlPanelCell numberOne = new ControlPanelCell(context, R.drawable.ic_numeric_1_button);
        ControlPanelCell numberTwo = new ControlPanelCell(context, R.drawable.ic_numeric_2_button);
        ControlPanelCell numberThree = new ControlPanelCell(context, R.drawable.ic_numeric_3_button);
        ControlPanelCell numberFour = new ControlPanelCell(context, R.drawable.ic_numeric_4_button);
        ControlPanelCell numberFive = new ControlPanelCell(context, R.drawable.ic_numeric_5_button);
        ControlPanelCell numberSix = new ControlPanelCell(context, R.drawable.ic_numeric_6_button);
        ControlPanelCell numberSeven = new ControlPanelCell(context, R.drawable.ic_numeric_7_button);
        ControlPanelCell numberEight = new ControlPanelCell(context, R.drawable.ic_numeric_8_button);
        ControlPanelCell numberNine = new ControlPanelCell(context, R.drawable.ic_numeric_9_button);

        controlPanelButtons.add(numberOne);
        controlPanelButtons.add(numberTwo);
        controlPanelButtons.add(numberThree);
        controlPanelButtons.add(numberFour);
        controlPanelButtons.add(numberFive);
        controlPanelButtons.add(numberSix);
        controlPanelButtons.add(numberSeven);
        controlPanelButtons.add(numberEight);
        controlPanelButtons.add(numberNine);
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
            //newButton.setLayoutParams(new GridView.LayoutParams(200, 200));
            //newButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            newButton.setPadding(8, 8, 8, 8);
            newButton.setImageResource(controlPanelButtons.get(position).getImageResource());
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
