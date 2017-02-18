package cpsc463sudoku.sudoku;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class MainActivity extends Activity {


    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        HomePage fr = new HomePage(); //create game fragment
        ft.replace(android.R.id.content, fr);
        ft.commit();


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
