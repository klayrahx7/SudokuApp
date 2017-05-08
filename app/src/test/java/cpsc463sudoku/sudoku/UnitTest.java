package cpsc463sudoku.sudoku;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nguyen on 4/11/2017.
 */

public class UnitTest {
    HomePage h = new HomePage();
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    /*
    @Before
    public void startGameSetup() throws Exception{

        h.getActivity().getFragmentManager().beginTransaction().add(R.id.activity_home_page ,null).commit();

    }

    @Test
    public void startGameTest(){
        assertTrue(h.startTest());
    }*/

    @Test
    public void userTest() throws Exception{
        User u = new User();
        u.setUserID(20);
        u.setUserName("Jacob");
        u.setUserPassword("UnitTest");
        assertEquals(u.getUserName(), "Jacob");
        assertEquals(u.getUserID(), 20);
        assertEquals(u.getUserPassword(), "UnitTest");

    }

    @Test
    public void boardCellTest(){
        BoardCell currentCell = new BoardCell();
        assertEquals(currentCell.id, -1);
        assertEquals(currentCell.isNotesSet, false);
        currentCell.setCurrentValue(7);
        assertEquals(currentCell.currentValue, 7);
        currentCell.setCurrentValue(2);
        assertEquals(currentCell.currentValue, 2);
    }


}
