package cpsc463sudoku.sudoku;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Micah on 2/15/2017.
 */

public class Solver {

    public static final List<Integer> ALLSET =
            Collections.unmodifiableList(Arrays.asList(1,2,3,4,5,6,7,8,9));

    public Solver() {}

    //Brute Force Solver
    private static Boolean backTrack(Puzzle board){
        List<Element> easyFill = new ArrayList<Element>();
        int best = 9;
        Set<Integer> needSet = new HashSet<Integer>();
        Set<Integer> bestChoices = null;
        Element bestEl = null;
        for (Element e : board.elements){
            if (e.getValue() == null) {
                needSet = e.choices();
                int length = needSet.size();
                if (length == 0) {
                    undo(easyFill);
                    return false;
                }
                if (length == 1) {
                    e.setValue(needSet.iterator().next());
                    easyFill.add(e);
                }
                if (length < best) {
                    bestEl = e;
                    bestChoices = needSet;
                    best = length;
                }
            }
        }
        int empty = 0;
        for (Element e: board.elements){
            if (empty > 0){
                break;
            }
            if (e.getValue() == null){
                empty++;
            }
        }
        if (empty == 0){
            return true;
        }

        for (Integer choice : bestChoices){
            bestEl.setValue(choice);
            if (!board.isValid()){
                bestEl.setValue(null);
                continue;
            }
            if (backTrack(board)){
                return true;
            }
            bestEl.setValue(null);
        }
        undo(easyFill);
        return false;
    }

    //Undo a bad fill
    private static void undo(List<Element> e){
        for (Element x : e){
            x.setValue(null);
        }

    }

    public static class Puzzle {
        String puzzle;
        String solution = "None";
        List<Element> elements = new ArrayList<Element>(81);
        List<Row> rows = new ArrayList<Row>(9);
        List<Column> cols = new ArrayList<Column>(9);
        List<SubBoard> sbs = new ArrayList<SubBoard>(9);

        Puzzle(String s){
            puzzle = s;

            //For initializing the elements
            for( int i = 0 ; i < puzzle.length() ; i++ )
            {
                if( puzzle.charAt(i) != '.' ){
                    elements.add(new Element((Character.getNumericValue(puzzle.charAt(i))), i));
                } else {
                    elements.add(new Element(null, i));
                }
            }

            //Initializing the rows
            for ( int i = 0; i < 81; i+=9){
                rows.add(new Row(elements.subList(i, i + 9)));
            }

            //Initializing the columns
            for (int i = 0 ; i < 9 ; i++){
                List<Element> newList = new ArrayList<Element>(elements.subList(i, i+1));
                newList.addAll(elements.subList(i+9, i+10));
                newList.addAll(elements.subList(i+18, i + 19));
                newList.addAll(elements.subList(i+27, i + 28));
                newList.addAll(elements.subList(i+36, i + 37));
                newList.addAll(elements.subList(i+45, i + 46));
                newList.addAll(elements.subList(i+54, i + 55));
                newList.addAll(elements.subList(i+63, i + 64));
                newList.addAll(elements.subList(i+72, i + 73));
                cols.add(new Column(newList));
            }

            //Initializing the subboards
            int x;
            for (int i = 0 ; i < 81 ; i+=27){
                for (int j = 0; j < 9 ; j+=3) {
                    x = i+j;
                    List<Element> newList = new ArrayList<Element>(9);
                    newList.addAll(elements.subList(x, x + 3));
                    newList.addAll(elements.subList(x+9, x + 12));
                    newList.addAll(elements.subList(x+18, x + 21));
                    sbs.add(new SubBoard(newList));
                }
            }

            //Fill in Simple Elements
            boolean found = true;
            Set<Integer> need = new HashSet<Integer>();
            while (found){
                found = false;
                for(Element e: elements){
                    if (e.getValue() == null){
                        need = e.choices();
                        if (need.isEmpty()){
                            continue;
                        }
                        if (need.size() == 1){
                            e.setValue(need.iterator().next());
                            //If we fill in one element, possible more single elements
                            found = true;
                        }
                    }
                }
            }
        }

        //Check if board is valid
        Boolean isValid(){
            for (Element e : elements){
                if (e.getValue() == null){
                    Set<Integer> need = e.choices();
                    if (need.size() == 0){
                        return false;
                    }
                }
            }
            return true;
        }

        String getSolution(){
            return this.solution;
        }

        void setSolution(){
            String s = "";
            for (Element e : elements){
                if (e.getValue() != null){
                    s += e.getValue().toString();
                } else {
                    s += '.';
                }
            }
            this.solution = s;
        }

    }

    private static class Element {
        Integer value;
        Integer index;
        Row r = null;
        Column c = null;
        SubBoard s = null;

        Element(Integer v, Integer i){
            this.value = v;
            this.index = i;
        }

        @Override
        public String toString(){
            return value.toString();
        }

        Set<Integer> choices(){
            Set<Integer> tmp = new HashSet<Integer>(9);
            tmp.addAll(r.need());
            tmp.retainAll(c.need());
            tmp.retainAll(s.need());
            return tmp;
        }

        Integer getValue() {
            return value;
        }

        void setValue(Integer value) {
            this.value = value;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Row getR() {
            return r;
        }

        public void setR(Row r) {
            this.r = r;
        }

        public Column getC() {
            return c;
        }

        void setC(Column c) {
            this.c = c;
        }

        public SubBoard getS() {
            return s;
        }

        void setS(SubBoard s) {
            this.s = s;
        }


    }

    private static class Row {
        List<Element> items;

        Row(List<Element> e){
            this.items = e;
            for(Element x: items){
                x.setR(this);
            }
        }

        Set<Integer> need(){
            Set<Integer> l = new HashSet<Integer>(9);
            for (Element x: items) {
                if (x.getValue() != null){
                    l.add(x.getValue());
                }
            }
            Set<Integer> t = new HashSet<Integer>(ALLSET);
            t.removeAll(l);//
            return t;
        }
    }

    private static class Column {
        List<Element> items;

        Column(List<Element> e){
            this.items = e;
            for(Element x: items){
                x.setC(this);
            }
        }

        Set<Integer> need(){
            Set<Integer> l = new HashSet<Integer>(9);
            for (Element x: items) {
                if (x.getValue() != null){
                    l.add(x.getValue());
                }
            }
            Set<Integer> t = new HashSet<Integer>(ALLSET);
            t.removeAll(l);//
            return t;
        }

    }

    private static class SubBoard {
        List<Element> items;

        SubBoard(List<Element> e) {
            this.items = e;
            for(Element x: items){
                x.setS(this);
            }
        }

        Set<Integer> need(){
            Set<Integer> l = new HashSet<Integer>(9);
            for (Element x: items) {
                if (x.getValue() != null){
                    l.add(x.getValue());
                }
            }
            Set<Integer> t = new HashSet<Integer>(ALLSET);
            t.removeAll(l);//
            return t;
        }
    }

}
