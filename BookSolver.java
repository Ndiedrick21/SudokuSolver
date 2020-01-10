import java.util.LinkedList;

public class BookSolver {
    static int MAX_PAGE_NUMBER = 250;

    //Different Sections of the book
    static final int EASY_START = 31;
    static final int EASY_LAST = 104;
    static final int TRICKY_START = 105;
    static final int TRICKY_LAST = 198;
    static final int TOUGH_START = 199;
    static final int TOUGH_LAST = 234;
    static final int DIABOLICAL_START = 235;
    static final int DIABOLICAL_LAST = 250;
    public static void main(String[] args){
        LinkedList<Sudoku> puzzles = new LinkedList<>();
        int start = EASY_START;
        int end = MAX_PAGE_NUMBER;
        if(args.length>0){
            if(args[0].equalsIgnoreCase("easy")){
                end = EASY_LAST;
                System.out.println("Solving all EASY puzzles...");
            }else if(args[0].equalsIgnoreCase("tricky")){
                start = TRICKY_START;
                end = TRICKY_LAST;
                System.out.println("Solving all TRICKY puzzles...");
            }else if(args[0].equalsIgnoreCase("tough")){
                start = TOUGH_START;
                end = TOUGH_LAST;
                System.out.println("Solving all TOUGH puzzles...");
            }else if(args[0].equalsIgnoreCase("diabolical")){
                start = DIABOLICAL_START;
                end = DIABOLICAL_LAST;
                System.out.println("Solving all DIABOLICAL puzzles...");
            }
        }

        //Read selected puzzles
        for(int i = start; i<=end; i++){
            puzzles.add(new Sudoku("puzzles/p"+i+".txt"));
        }
        System.out.println("Done reading files");
        SudokuSolver solver;
        double startTime = System.currentTimeMillis();
        //Solve selected puzzles
        for(Sudoku s:puzzles){
            solver = new SudokuSolver(s);
            solver.solve();
            System.out.println(s.getSolved()+ " - "+ s.getName());
            //s.printPuzzle();
        }
        double completionTime = (System.currentTimeMillis()-startTime)/1000;
        System.out.println("Solved "+puzzles.size()+" puzzles in "+completionTime+" seconds");
    }
}