import java.util.*;

/**
 * A class that solves a sudoku puzzle
 */
public class SudokuSolver{
    private Sudoku puzzle;
    private int[] columnConstraints; //The value at each index is the number of assigned values in that column.
    private int[] rowConstraints; //The value at each index is the number of assigned values in that row.
    private int[][] sectionConstraints; //The value at each index is the number of assigned values in that section.
    private HashSet<Slot> unassignedVariables;
    public SudokuSolver(Sudoku s){
        puzzle = s;
        columnConstraints = new int[9];
        rowConstraints = new int[9];
        sectionConstraints = new int[3][3];
        unassignedVariables = new HashSet<>();
        setupConstraints();
    }

    /**
     * A private helper method that setups the counts for constraints for row, columns, and sections.
     */
    private void setupConstraints(){
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                int currVal = puzzle.getGridValue(i, j);
                if(currVal!=0){
                    rowConstraints[i]++;
                    columnConstraints[j]++;
                    sectionConstraints[i%3][j%3]++;
                }else{
                    unassignedVariables.add(new Slot(i, j));
                }
            }
        }
    }

    //Returns the slot of the most constraining variable
    private Slot getNextSlot(){
        Slot bestSlot = null;
        int bestVal = 0;
        for(Slot slot:unassignedVariables){
            int currVal = 0;
            currVal+=rowConstraints[slot.row];
            currVal+=columnConstraints[slot.column];
            currVal+=sectionConstraints[slot.row%3][slot.column%3];
            if(currVal>=bestVal){
                bestSlot = slot;
                bestVal = currVal;
            }
        }
        return bestSlot;
    }

    /**
     * A method for initializing the solving of the sudoku puzzle.
     * @return an optional of Sudoku type, if the puzzle was solvable,
     *     then the optional will have a completed Sudoku
     */
    public Sudoku solve(){
        return backtrack(puzzle);
    }

    private Sudoku backtrack(Sudoku s){

        Slot nextSlot = getNextSlot();
        unassignedVariables.remove(nextSlot);
        
        if(nextSlot==null){
            s.setSolved(true);
            return s;
        }
        for(int i = 1; i<=9;i++){
            if(assignValue(nextSlot, i, s)){
                backtrack(s);
                if(s.getSolved()){
                    return s;
                }
                unassignValue(nextSlot, s);
            }
        }
        unassignedVariables.add(nextSlot);
        return s;
    }

    /**
     * Assigns a value to a slot then updates the relevant counters for the solver.
     * @param slot The slot that will have the value set
     * @param value The value that will be assigned to the slot.
     * @param s The puzzle to assign the value to
     * @return returns true if the value was successfully assigned
     */
    private boolean assignValue(Slot slot, int value, Sudoku s){
        boolean assignment = s.setGridValue(slot.row, slot.column, value);
        if(assignment){
            rowConstraints[slot.row]++;
            columnConstraints[slot.column]++;
            sectionConstraints[slot.row%3][slot.column%3]++;
            return true;
        }
        return false;
    }

    /**
     * Unassigns a value from a grid spot
     * @param slot The slot to unassign the value from
     * @param s The puzzle to unassign the value from
     */
    private void unassignValue(Slot slot, Sudoku s){
        rowConstraints[slot.row]--;
        columnConstraints[slot.column]--;
        sectionConstraints[slot.row%3][slot.column%3]--;
        s.setGridValue(slot.row, slot.column, 0);
    }

    /**
     * A helper class for storing a pair of row and column
     */
    class Slot{
        public int row;
        public int column;

        public Slot(int r, int c){
            row = r;
            column = c;
        }

        public String toString(){
            return "Slot-> row: "+row+" | col: "+column;
        }

        @Override
        public boolean equals(Object slot){
            Slot s = (Slot)slot;
            return row==s.row&&column==s.column;
        }
    }
}