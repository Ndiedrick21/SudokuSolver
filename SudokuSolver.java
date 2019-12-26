import java.util.Optional;

/**
 * A class that solves a sudoku puzzle
 */
public class SudokuSolver{
    private Sudoku puzzle;
    private int[] columnConstraints; //The value at each index is the number of assigned values in that column.
    private int[] rowConstraints; //The value at each index is the number of assigned values in that row.
    private int[][] sectionConstraints; //The value at each index is the number of assigned values in that section.
    public SudokuSolver(Sudoku s){
        puzzle = s;
        columnConstraints = new int[9];
        rowConstraints = new int[9];
        sectionConstraints = new int[3][3];
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
                }
            }
        }
    }

    /**
     * A method for initializing the solving of the sudoku puzzle.
     * @return an optional of Sudoku type, if the puzzle was solvable,
     *     then the optional will have a completed Sudoku
     */
    public Optional<Sudoku> solve(){
        backtrack(puzzle);
        return null;
    }

    private Sudoku backtrack(Sudoku s){

        return null;
    }
}