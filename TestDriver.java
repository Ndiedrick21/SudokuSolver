public class TestDriver{

    public static void main(String[] args){
        Sudoku s = new Sudoku(args[0]);
        s.printPuzzle();
        SudokuSolver solver = new SudokuSolver(s);
        Sudoku done = solver.solve();
        System.out.println("======="+done.getSolved()+"======");
        done.printPuzzle();
    }
}