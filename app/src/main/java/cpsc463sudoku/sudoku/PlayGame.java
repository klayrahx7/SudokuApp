package cpsc463sudoku.sudoku;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

import static android.view.KeyEvent.KEYCODE_BACK;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.EIGHT;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.ERASE;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.FIVE;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.FOUR;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.HINT;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.NINE;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.NOTES;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.ONE;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.REDO;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.SEVEN;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.SIX;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.SOLVE;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.THREE;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.TWO;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.UNDO;
import static cpsc463sudoku.sudoku.ControlPanelAdapter.buttonRed;

public class PlayGame extends Fragment implements BoardAdapter.boardCallback, ControlPanelAdapter.controlPanelCallback {

    private FragmentTransaction ft;
    private BoardAdapter boardAdapter;
    private ControlPanelAdapter controlPanelAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle gameState = this.getArguments();
        boardAdapter = gameState.getParcelable("gameState");
        String solvedState = boardAdapter.getBoardSolvedState();
        boardAdapter = new BoardAdapter(getActivity(), boardAdapter.getBoardStateList().get(0), this);
        boardAdapter.setBoardSolvedState(solvedState);
        controlPanelAdapter = new ControlPanelAdapter(getActivity(), this);
    }

    @Override
    public void cellClicked(int position)
    {
        BoardCell currentCell = boardAdapter.getItem(position);
        controlPanelAdapter.setAllButtonsBlue();

        // We can change any cell that is not an initial value cell (Bold)
        if(!currentCell.isInitialValue())
        {
            switch(currentCell.getCurrentValue())
            {
                case 1:
                    controlPanelAdapter.setButtonState(ONE, buttonRed);
                    break;
                case 2:
                    controlPanelAdapter.setButtonState(TWO, buttonRed);
                    break;
                case 3:
                    controlPanelAdapter.setButtonState(THREE, buttonRed);
                    break;
                case 4:
                    controlPanelAdapter.setButtonState(FOUR, buttonRed);
                    break;
                case 5:
                    controlPanelAdapter.setButtonState(FIVE, buttonRed);
                    break;
                case 6:
                    controlPanelAdapter.setButtonState(SIX, buttonRed);
                    break;
                case 7:
                    controlPanelAdapter.setButtonState(SEVEN, buttonRed);
                    break;
                case 8:
                    controlPanelAdapter.setButtonState(EIGHT, buttonRed);
                    break;
                case 9:
                    controlPanelAdapter.setButtonState(NINE, buttonRed);
                    break;
                case 0:
                    //boardAdapter.setSolved(true);
                    break;
            }
        }

        controlPanelAdapter.notifyDataSetChanged();
        boardAdapter.notifyDataSetChanged();
    }

    @Override
    public void panelClicked(int position)
    {
        BoardCell currentCell = boardAdapter.getSelectedCell();
        long currentCellPosition = currentCell.getId();

        // This case is only active when the last keypress was Notes or the cell already has notes
        if(currentCell.isNotesSet)
        {
            int[] notes = currentCell.getUserNotes();
            if (position == ONE) {
                notes[0] = 1;
            } else if (position == TWO) {
                notes[1] = 2;
            } else if (position == THREE) {
                notes[2] = 3;
            } else if (position == FOUR) {
                notes[3] = 4;
            } else if (position == FIVE) {
                notes[4] = 5;
            } else if (position == SIX) {
                notes[5] = 6;
            } else if (position == SEVEN) {
                notes[6] = 7;
            } else if (position == EIGHT) {
                notes[7] = 8;
            } else if (position == NINE) {
                notes[8] = 9;
            }
        }

        // We can change any cell that is not an initial value cell (Blue)
        if(!currentCell.isInitialValue()) {
            if (position == ONE) {
                currentCell.setCurrentValue(1);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == TWO) {
                currentCell.setCurrentValue(2);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == THREE) {
                currentCell.setCurrentValue(3);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == FOUR) {
                currentCell.setCurrentValue(4);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == FIVE) {
                currentCell.setCurrentValue(5);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == SIX) {
                currentCell.setCurrentValue(6);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == SEVEN) {
                currentCell.setCurrentValue(7);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == EIGHT) {
                currentCell.setCurrentValue(8);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == NINE) {
                currentCell.setCurrentValue(9);
                boardAdapter.advanceBoardState(boardAdapter.toString());
            } else if (position == UNDO) {
                // Set current state to last known state
                boardAdapter.undoBoardState();
            }else if (position == REDO) {
                // Set current state to last known state
                boardAdapter.redoBoardState();
            }else if (position == ERASE) {
                // Set current state to last known state
                currentCell.setCurrentValue(BoardCell.EMPTY_CELL);
                currentCell.setUserNotes(new int[BoardCell.NUM_NOTES]);
                currentCell.isNotesSet = false;
            }else if (position == SOLVE) {
                // Set current state to solved state
                boardAdapter.setBoardCurrentState(boardAdapter.getBoardSolvedState());
            }else if (position == NOTES) {
                // Set current state to last known state
              currentCell.isNotesSet = !currentCell.isNotesSet;
            }else if (position == HINT) {
                // Set current state to last known state
                boardAdapter.getHint(currentCell);
            }

            // Let the board Adapter know that we have changed it's state
            boardAdapter.setAllBoardHighlights(boardAdapter.getSelectedCell());
        }
        controlPanelAdapter.notifyDataSetChanged();
        boardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.play_game, container, false);
        RelativeLayout screenView = (RelativeLayout)v.findViewById(R.id.screenContainer);
        GridView controlPanelView = (GridView)v.findViewById(R.id.controlPanelView);
        GridView boardView = (GridView) v.findViewById(R.id.boardView);
        Resources res = getResources();

        // Setup screen background
        //screenView.setBackground(res.getDrawable(R.drawable.main_game_board_4));

        // Fit game board to screen
        ViewGroup.LayoutParams boardLayoutParams = boardView.getLayoutParams();
        int screen_width = res.getDisplayMetrics().widthPixels;
        int screen_height = res.getDisplayMetrics().heightPixels;
        boardLayoutParams.width = (int) (screen_width);
        boardLayoutParams.height = (int)(screen_height * 0.65);

        // Set up gridView containing the board
        boardView.setLayoutParams(boardLayoutParams);
        boardView.setAdapter(boardAdapter);
        boardView.setBackground(res.getDrawable(R.drawable.main_game_board_5));

        // set up gridView containing controlPanel
        controlPanelView.setAdapter(controlPanelAdapter);

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KEYCODE_BACK)
                {
                    ft = getFragmentManager().beginTransaction();
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ft.commit();
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        return v;
    }
}
