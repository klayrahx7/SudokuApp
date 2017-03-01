package cpsc463sudoku.sudoku;



import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class HomePage extends Fragment {

    TextView styleStart;
    TextView styleContinue;
    TextView styleSettings;
    TextView styleScores;
    Button startNewGame;
    GridLayout layoutHolder;
    ViewGroup.LayoutParams params;
    Fragment frag;


    //Declare variables to manage our fragments
    private FragmentTransaction ft;



    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ft = getFragmentManager().beginTransaction();
        frag = getFragmentManager().findFragmentByTag("Home");
        View v = inflater.inflate(R.layout.activity_home_page, container,false);
        //Link our objects to their corresponding ID's in the XML
        startNewGame = (Button)v.findViewById(R.id.startNewGameButton);
        styleStart = (TextView)v.findViewById(R.id.startText);
        styleContinue = (TextView)v.findViewById(R.id.continueText);
        styleSettings = (TextView)v.findViewById(R.id.settingsText);
        styleScores = (TextView) v.findViewById(R.id.scoresText);
        layoutHolder = (GridLayout) v.findViewById(R.id.gridLayout);

        //Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        params = layoutHolder.getLayoutParams();
        params.width = width/2 + 100;
        params.height = height/2 + 100;
        layoutHolder.setLayoutParams(params);


        //Style the textview with our custom font
        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/LinLibertine_aDRS.ttf");
        styleStart.setTypeface(customFont, Typeface.BOLD);
        styleContinue.setTypeface(customFont, Typeface.BOLD);
        styleSettings.setTypeface(customFont, Typeface.BOLD);
        styleScores.setTypeface(customFont, Typeface.BOLD);


        //Declare buttons to go to new fragments
        startNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        Fragment newGame = new PlayGame();
                        Bundle gameState = new Bundle();
                        gameState.putParcelable("gameState", getBoardData());
                        newGame.setArguments(gameState);
                        //ft.hide(frag);
                        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_right,  R.anim.exit_to_left);
                        ft.replace(R.id.activity_main, newGame, "PlayGame");
                        ft.addToBackStack("Home");
                        ft.commit();
                    }
                };
                thread.run();

            }
        });

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
                BoardAdapter newBoardAdapter = new BoardAdapter(getContext(), linepl);
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
        return new BoardAdapter(getActivity(), "");
    }

}
