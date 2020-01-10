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
                //Add inferences
                //Inference inf = findInferences(s, nextSlot);
                //if(!inf.conflictFound){
                    backtrack(s);
                    if(s.getSolved()){
                        return s;
                    }
                //}
               // undoInferences(s, inf);
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
            sectionConstraints[slot.row/3][slot.column/3]++;
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
        sectionConstraints[slot.row/3][slot.column/3]--;
        s.setGridValue(slot.row, slot.column, 0);
    }

    private Inference findInferences(Sudoku s, Slot currSlot){
        Inference newInference = new Inference();
        System.out.println("Start of inference");
        if(rowConstraints[currSlot.row]==8){
            s.printPuzzle();
            Slot rowInf = assignRowInference(s, currSlot.row);
            if(rowInf==null){
                System.out.println("constrsint failed...");
                newInference.foundConflict();
                return newInference;
            }else{
                newInference.addAssignedSlot(rowInf);
                s.printPuzzle();
            }
        }
        if(columnConstraints[currSlot.column]==8){
            s.printPuzzle();
            Slot colInf = assignColInference(s, currSlot.column);
            if(colInf==null){
                System.out.println("constrsint failed...");
                newInference.foundConflict();
                return newInference;
            }else{
                newInference.addAssignedSlot(colInf);
                s.printPuzzle();
            }
        }
        if(sectionConstraints[currSlot.row/3][currSlot.column/3]==8){
            s.printPuzzle();
            Slot secInf = assignSectionInference(s, currSlot);
            if(secInf==null){
                System.out.println("constrsint failed...");
                newInference.foundConflict();
                return newInference;
            }else{
                newInference.addAssignedSlot(secInf);
                s.printPuzzle();
            }
        }
        return newInference;
    }

    /**
     * If a row if found with 8 assignments, we can infer the last value.
     *      This process is done by finding the open slot and computing the remining value.
     * @param s The puzzle to find the inference
     * @param row The row that caused the inference to be available
     * @return the slot that recieved the assignment from the inference, null if the value cant be assigned.
     */
    private Slot assignRowInference(Sudoku s, int row){
        int valueToAssign = Sudoku.SECTION_TOTAL;
        int colToAssign = 0;
        for(int i = 0; i<9; i++){
            int currVal = s.getGridValue(row, i);
            if(currVal == 0){
                colToAssign = i;
            }
            valueToAssign-=currVal;
        }
        Slot assignSlot = new Slot(row, colToAssign);
        boolean validAssignment = assignValue(assignSlot, valueToAssign, s);
        if(validAssignment){
            unassignedVariables.remove(assignSlot);
            return assignSlot;
        }else{return null;}
    }

    /**
     * If a column if found with 8 assignments, we can infer the last value.
     *      This process is done by finding the open slot and computing the remining value.
     * @param s The puzzle to find the inference
     * @param col The col that caused the inference to be available
     * @return the slot that recieved the assignment from the inference, null if the value cant be assigned.
     */
    private Slot assignColInference(Sudoku s, int col){
        int valueToAssign = Sudoku.SECTION_TOTAL;
        int rowToAssign = 0;
        for(int i = 0; i<9; i++){
            int currVal = s.getGridValue(i, col);
            if(currVal == 0){
                rowToAssign = i;
            }
            valueToAssign-=currVal;
        }
        Slot assignSlot = new Slot(rowToAssign, col);
        boolean validAssignment = assignValue(assignSlot, valueToAssign, s);
        if(validAssignment){
            unassignedVariables.remove(assignSlot);
            return assignSlot;
        }else{return null;}
    }

    /**
     * If a section if found with 8 assignments, we can infer the last value.
     *      This process is done by finding the open slot and computing the remining value.
     * @param s The puzzle to find the inference
     * @param slot The slot that caused the inference to be available
     * @return the slot that recieved the assignment from the inference, null if the value cant be assigned.
     */
    private Slot assignSectionInference(Sudoku s, Slot slot){
        int valueToAssign = Sudoku.SECTION_TOTAL;
        int rowToAssign = 0;
        int colToAssign = 0;
        for(int i = (slot.row/3)*3; i<(slot.row/3)*3+3; i++){
            for(int j = (slot.column/3)*3; j<(slot.column/3)*3+3; j++){
                int currVal = s.getGridValue(i, j);
                if(currVal == 0){
                    rowToAssign = i;
                    colToAssign = j;
                }
                valueToAssign-=currVal;
            }
        }
        Slot assignSlot = new Slot(rowToAssign, colToAssign);
        boolean validAssignment = assignValue(assignSlot, valueToAssign, s);
        if(validAssignment){
            unassignedVariables.remove(assignSlot);
            return assignSlot;
        }else{return null;}
    }

    /**
     * Will unassign values made in this inferece object
     *      These slots will be added back to the unassigned variables list.
     * @param s The puzzle to unassign values from
     * @param inf The inferene object to be undone
     */
    private void undoInferences(Sudoku s, Inference inf){
        for(Slot slot:inf.assignedSlots){
            unassignValue(slot, s);
            unassignedVariables.add(slot);
        }
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
            return "Slot-> ("+row+" , "+column+")";
        }

        @Override
        public boolean equals(Object slot){
            Slot s = (Slot)slot;
            return row==s.row&&column==s.column;
        }

        @Override
        public int hashCode(){
            int hash = 0;
            hash+=row+column;
            hash*=row*column;
            return hash;
        }
    }

    class Inference{
        public boolean conflictFound;
        public List<Slot> assignedSlots;

        public Inference(){
            conflictFound = false;
            assignedSlots = new LinkedList<>();
        }

        public void foundConflict(){
            conflictFound = true;
        }

        public void addAssignedSlot(Slot slot){
            assignedSlots.add(slot);
        }
    }
}