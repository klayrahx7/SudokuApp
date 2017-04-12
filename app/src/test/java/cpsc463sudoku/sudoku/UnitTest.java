package cpsc463sudoku.sudoku;

import android.util.Log;
import android.widget.Button;

import org.junit.Test;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nguyen on 4/11/2017.
 */

public class UnitTest {

    @Test
    public void startGameWorks(){
        HomePage h = new HomePage();
        assertThat(h.startTest(),is(true));
        Log.d("test", "Done");
    }
}
