import java.util.LinkedList;

public class BookSolver {
    static int MAX_PAGE_NUMBER = 95;
    public static void main(String[] args){
        LinkedList<Sudoku> puzzles = new LinkedList<>();
        for(int i = 31; i<=MAX_PAGE_NUMBER; i++){
            puzzles.add(new Sudoku("puzzles/p"+i+".txt"));
        }
        System.out.println("Done reading files");
        SudokuSolver solver;
        int counter = 0;
        for(Sudoku s:puzzles){
            solver = new SudokuSolver(s);
            solver.solve();
            System.out.println(s.getSolved()+ " - "+ (++counter));
            //s.printPuzzle();
        }
        System.out.println("Solved "+puzzles.size()+" puzzles");
    }
}