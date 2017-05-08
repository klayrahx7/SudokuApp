package cpsc463sudoku.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

public class SettingsPage extends Fragment {

    Button HTP;
    Button Rules;
    ToggleButton Theme;

    private Boolean isChecked;


    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_settings_page, container, false);

        HTP = (Button)v.findViewById(R.id.howToPlay);
        Rules = (Button)v.findViewById(R.id.rules);
        HTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://youtu.be/OtKxtvMUahA"));
                startActivity(intent);
            }
        });

        Rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.sudoku.name/rules/en"));
                startActivity(intent);
            }
        });


        return v;
    }

}
