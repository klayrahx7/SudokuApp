package cpsc463sudoku.sudoku;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Jenniwazhere on 3/8/2017.
 */

public class DifficultyPage extends Fragment {
    Button easy;
    Button medium;
    Button hard;
    GridLayout layoutHolder;
    ViewGroup.LayoutParams params;
    Fragment frag;

    //Declare variables to manage our fragments
    private FragmentTransaction ft;

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ft = getFragmentManager().beginTransaction();
        frag = getFragmentManager().findFragmentByTag("Difficulty");
        View v = inflater.inflate(R.layout.activity_difficulty_page, container, false);

        easy = (Button) v.findViewById(R.id.easyButton);
        medium = (Button) v.findViewById(R.id.mediumButton);
        hard = (Button) v.findViewById(R.id.hardButton);

        //Declare buttons to go to new fragments
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment newGame = new PlayGame();
                Bundle gameState = new Bundle();
                gameState.putParcelable("gameState", getBoardData());
                newGame.setArguments(gameState);
                //ft.hide(frag);
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,  R.anim.exit_to_left);
                ft.replace(R.id.activity_main, newGame, "PlayGame");
                ft.commit();
            }
        });

        /*medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment newGame = new PlayGame();
                Bundle gameState = new Bundle();
                gameState.putParcelable("gameState", getBoardData());
                newGame.setArguments(gameState);
                //ft.hide(frag);
                //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,  R.anim.exit_to_left);
                //ft.replace(R.id.activity_main, newGame, "PlayGame");
                ft.addToBackStack("Home");
                ft.commit();
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.Fragment newGame = new PlayGame();
                Bundle gameState = new Bundle();
                gameState.putParcelable("gameState", getBoardData());
                newGame.setArguments(gameState);
                //ft.hide(frag);
                //ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,  R.anim.exit_to_left);
                //ft.replace(R.id.activity_main, newGame, "PlayGame");
                ft.addToBackStack("Home");
                ft.commit();
            }
        });*/

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
                BoardAdapter newBoardAdapter = new BoardAdapter(linepl);
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
        return new BoardAdapter("");
    }
}
