package cpsc463sudoku.sudoku;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class MainActivity extends FragmentActivity {

    private FragmentTransaction ft;
    public Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ft = getSupportFragmentManager().beginTransaction();
        HomePage homeFrag = new HomePage(); //create game fragment
        ft.add(R.id.activity_main, homeFrag, "Home");
        ft.show(homeFrag);
        ft.commit();
    }

}