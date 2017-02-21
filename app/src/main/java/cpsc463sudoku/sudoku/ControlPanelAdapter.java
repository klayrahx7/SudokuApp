package cpsc463sudoku.sudoku;


import android.content.Context;
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
        this.context = context;
        controlPanelButtons = new ArrayList<ControlPanelCell>();
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_1_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_2_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_3_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_4_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_5_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_6_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_7_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_8_button));
        controlPanelButtons.add(new ControlPanelCell(R.drawable.ic_numeric_9_button));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cell;
        ImageButton newButton = new ImageButton(context);

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            cell = layoutInflater.inflate(R.layout.control_panel_cell, null);
            newButton.setLayoutParams(new GridView.LayoutParams(200, 200));
            newButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            newButton.setPadding(8, 8, 8, 8);
            newButton.setImageResource(controlPanelButtons.get(position).getImageResource());
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
