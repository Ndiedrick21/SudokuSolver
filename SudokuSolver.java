import java.util.*;

/**
 * A class that solves a sudoku puzzle
 */
public class SudokuSolver{
    private Sudoku puzzle;
    private int[] columnConstraints; //The value at each index is the number of assigned values in that column.
    private int[] rowConstraints; //The value at each index is the number of assigned values in that row.
    private int[][] sectionConstraints; //The value at each index is the number of assigned values in that section.
    private HashMap<Integer, List<Integer>> assignedLocations;
    public SudokuSolver(Sudoku s){
        puzzle = s;
        columnConstraints = new int[9];
        rowConstraints = new int[9];
        sectionConstraints = new int[3][3];
        assignedLocations = new HashMap<>();
        for(int i = 0; i<9; i++){
            assignedLocations.put(i, new LinkedList<Integer>());
        }
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
                    assignedLocations.get(i).add(j);
                }
            }
        }
    }

    //Returns the slot of the most constraining variable
    private Slot getNextSlot(Sudoku s){
        int bestRow = -1;
        int bestCol = -1;
        int bestVal = 0;
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(s.getGridValue(i, j)==0){
                    int currVal = 0;
                    currVal+=rowConstraints[i];
                    currVal+=columnConstraints[j];
                    currVal+=sectionConstraints[i%3][j%3];
                    if(currVal>=bestVal){
                        bestRow = i;
                        bestCol = j;
                        bestVal = currVal;
                    }
                }
            }
        }
        System.out.println("Found slot: row="+bestRow+"| col="+bestCol);
        return new Slot(bestRow, bestCol);
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

        Slot nextSlot = getNextSlot(s);
        if(nextSlot.row==-1&&nextSlot.column==-1){
            s.setSolved(true);
            return s;
        }
        for(int i = 1; i<=9;i++){
            if(assignValue(nextSlot.row, nextSlot.column, i, s)){
                backtrack(s);
                if(s.getSolved()){
                    return s;
                }
            }
        }
        return s;
    }

    private boolean assignValue(int row, int col, int value, Sudoku s){
        boolean assignment = s.setGridValue(row, col, value);
        if(assignment){
            rowConstraints[row]++;
            columnConstraints[col]++;
            sectionConstraints[row%3][col%3]++;
        }
        return assignment;
    }

    class Slot{
        public int row;
        public int column;

        public Slot(int r, int c){
            row = r;
            column = c;
        }
    }
}