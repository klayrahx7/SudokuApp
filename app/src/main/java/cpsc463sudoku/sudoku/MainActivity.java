package cpsc463sudoku.sudoku;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    TextView styleStart;
    TextView styleContinue;
    TextView styleSettings;
    TextView styleScores;
    Button startNewGame;

    //Declare variables to manage our fragments
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Link our objects to their corresponding ID's in the XML
        startNewGame = (Button)findViewById(R.id.startNewGameButton);
        styleStart = (TextView)findViewById(R.id.startText);
        styleContinue = (TextView)findViewById(R.id.continueText);
        styleSettings = (TextView)findViewById(R.id.settingsText);
        styleScores = (TextView) findViewById(R.id.scoresText);

        //Style the textview with our custom font
        Typeface customFont = Typeface.createFromAsset(getAssets(),"fonts/LinLibertine_aDRS.ttf");
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
                //playGame fr = new playGame(); create game fragment
                ft.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_right,0,0);
                //ft.replace(android.R.id.content, fr);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        File sdcard = Environment.getExternalStorageDirectory();
        try
        {
            File file = new File(sdcard, "/Download/puzzleList.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null)
            {
                Board newBoard = new Board(line);
                Log.d("Created a new Board: ", newBoard.toString());
            }
            br.close();
        }
        catch(Exception e)
        {
            Log.d("Caught Exception: ", e.getMessage());
            e.printStackTrace();
        }

    }

}
