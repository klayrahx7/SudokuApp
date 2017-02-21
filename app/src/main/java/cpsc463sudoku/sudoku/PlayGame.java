package cpsc463sudoku.sudoku;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Nguyen on 2/17/2017.
 */

public class PlayGame extends Fragment {

    private FragmentTransaction ft;

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.play_game, container, false);
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == keyEvent.KEYCODE_BACK)
                {
                    ft = getFragmentManager().beginTransaction();
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else
                {
                    return false;
                }
            }
        });

        GridView board = (GridView)v.findViewById(R.id.boardView);
        BoardAdapter boardAdapter = new BoardAdapter(getActivity(), getBoardData().toString());
        board.setAdapter(boardAdapter);

        GridView controlPanel = (GridView)v.findViewById(R.id.controlPanelView);
        ControlPanelAdapter controlPanelAdapter = new ControlPanelAdapter(getActivity());
        controlPanel.setAdapter(controlPanelAdapter);

        return v;
    }

    public BoardAdapter getBoardData()
    {
        File sdcard = Environment.getExternalStorageDirectory();
        try
        {
            File puzzleList = new File(sdcard, "/Download/puzzleList.txt");
            File solutionList = new File(sdcard, "/Download/solutionList.txt");
            BufferedReader brpl = new BufferedReader(new FileReader(puzzleList));
            BufferedReader brsl = new BufferedReader(new FileReader(solutionList));
            String linepl;
            String linesl;
            if ((linepl = brpl.readLine()) != null && (linesl = brsl.readLine()) != null) {
                BoardAdapter newBoardAdapter = new BoardAdapter(getActivity(), linepl);
                newBoardAdapter.setBoardSolvedState(linesl);
                Log.d("Created a new Board: ", newBoardAdapter.toString());
                Log.d("Its Solution is    : ", newBoardAdapter.getBoardSolvedState());
                brpl.close();
                brsl.close();
                return newBoardAdapter;
            }
        }
        catch(Exception e)
        {
            Log.d("Caught Exception: ", e.getMessage());
            e.printStackTrace();
        }
        return new BoardAdapter(getActivity());
    }
}
