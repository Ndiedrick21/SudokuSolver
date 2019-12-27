public class TestDriver{

    public static void main(String[] args){
        Sudoku s = new Sudoku("test1.txt");
        s.printPuzzle();
        SudokuSolver solver = new SudokuSolver(s);
        Sudoku done = solver.solve();
        System.out.println("======="+done.getSolved()+"======");
        done.printPuzzle();
    }
}