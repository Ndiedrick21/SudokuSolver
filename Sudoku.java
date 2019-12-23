import java.io.*;
import java.util.Scanner;

/**
 * A class for a Sudoku puzzle.
 * Stores the puzzle as a 3x3 integer array.
 *      Unknown values are stored as 0.
 * Author: Nathan Diedrick
 */
public class Sudoku{
    private int[][] grid;

    /**
     * A constructor for a Sudoku puzzle.
     *      The file should have nine rows, with numbers or x's as values in each row.
     * @param filename the file that stores the outline of the sudoku puzzle
     */
    public Sudoku(String filename){
        grid = new int[3][3];
        File pFile = new File(filename);
        try{
            FileReader fin = new FileReader(pFile);
            BufferedReader bin = new BufferedReader(fin);
            Scanner scan;
            String currRow = bin.readLine();
            while(currRow!=null){
                int row = 0;
                scan = new Scanner(currRow);
                while(scan.hasNext()){
                    int col = 0;
                    if(scan.hasNextInt()){
                        grid[row][col] = scan.nextInt();
                    }else{
                        String curr = scan.next();
                        if(!curr.equalsIgnoreCase("x")){
                            throw new IOException("Incorrect file format");
                        }
                        grid[row][col] = 0;
                    }
                    col++;
                }
                row++;
                currRow = bin.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            bin.close();
            scan.close();
        }
    }

    /**
     * A method for printing the current status of the puzzle
     */
    public void printPuzzle(){
        boolean horizontalRow = true;
        for(int i = 0; i<grid.length; i++){
            if(horizontalRow){
                for(int r = 0; r<19; r++){
                    System.out.print("_");
                }
                i--;
            }else{
                for(int j = 0; j<gird[i].length; j++){
                    System.out.print("|");
                    if(grid[i][j]==0){
                        System.out.print("x");
                    }else{
                        System.out.print(grid[i][j]);
                    }
                }
                System.out.print("|");
                horizontalRow = !horizontalRow;
            }
        }
    }

}