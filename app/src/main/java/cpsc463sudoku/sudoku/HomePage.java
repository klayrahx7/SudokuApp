package cpsc463sudoku.sudoku;



import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import static cpsc463sudoku.sudoku.R.styleable.View;

public class HomePage extends Fragment {

    TextView styleStart;
    TextView styleContinue;
    TextView styleSettings;
    TextView styleScores;
    Button startNewGame;


    //Declare variables to manage our fragments
    private FragmentManager fm;
    private FragmentTransaction ft;

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.activity_home_page, container,false);
        //Link our objects to their corresponding ID's in the XML
        startNewGame = (Button)v.findViewById(R.id.startNewGameButton);
        styleStart = (TextView)v.findViewById(R.id.startText);
        styleContinue = (TextView)v.findViewById(R.id.continueText);
        styleSettings = (TextView)v.findViewById(R.id.settingsText);
        styleScores = (TextView) v.findViewById(R.id.scoresText);

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
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                PlayGame fr = new PlayGame(); //create game fragment
                ft.setCustomAnimations(R.animator.enter_from_left,0,0, R.animator.exit_to_right);
                ft.replace(android.R.id.content, fr);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return v;
    }


}
