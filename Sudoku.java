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
    private boolean solved;

    /**
     * A constructor for a Sudoku puzzle.
     *      The file should have nine rows, with numbers or x's as values in each row.
     * @param filename the file that stores the outline of the sudoku puzzle
     */
    public Sudoku(String filename){
        grid = new int[9][9];
        solved = false;
        File pFile = new File(filename);
        FileReader fin;
        BufferedReader bin;
        Scanner  scan;
        try{
            fin = new FileReader(pFile);
            bin = new BufferedReader(fin);
            String currRow = bin.readLine();
            int row = 0;
            while(currRow!=null){
                scan = new Scanner(currRow);
                int col = 0;
                while(scan.hasNext()){
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
                scan.close();
            }
            bin.close();
            fin.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * A getter method to return a copy of the puzzle array
     * @return a copy of the grid
     */
    public int[][] getGrid(){
        int[][] newGrid = new int[9][9];
        for(int i = 0; i<grid.length; i++){
            for(int j = 0; j<grid[i].length; j++){
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }

    /**
     * A method for printing the current status of the puzzle
     */
    public void printPuzzle(){
        boolean horizontalRow = true;
        for(int i = 0; i<grid.length; i++){
            if(horizontalRow){
                for(int r = 0; r<19; r++){
                    System.out.print("-");
                }
                i--;
            }else{
                for(int j = 0; j<grid[i].length; j++){
                    System.out.print("|");
                    if(grid[i][j]==0){
                        System.out.print("x");
                    }else{
                        System.out.print(grid[i][j]);
                    }
                }
                System.out.print("|");
            }
            horizontalRow = !horizontalRow;
            System.out.println();
        }
    }

    /**
     * A getter method for a specific value in a grid
     * @param row row index in grid
     * @param col column index in grid
     * @return the value at that row and column
     */
    public int getGridValue(int row, int col){
        return grid[row][col];
    }

    /**
     * A setter method for a place in the grid
     * Also checks if the value violates any constraints
     * @param row The row index to set the value
     * @param col The column index to set the value
     * @param value The value to set at the specific location
     * @return true if the value can be assigned, false if a constraint is violated
     */
    public boolean setGridValue(int row, int col, int value){
        for(int i = 0; i<9; i++){
            if(i!=row&&grid[i][col]==value){
                return false;
            }
        }
        for(int j = 0; j<9; j++){
            if(j!=col&&grid[row][j]==value){
                return false;
            }
        }
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                if(i!=row&&j!=col&&grid[i][j]==value){
                    return false;
                }
            }
        }
        grid[row][col] = value;
        return true;
    }

    /**
     * A setter method for if the puzzle is solved.
     * @param val The value to set solved to.
     */
    public void setSolved(boolean val){
        solved = val;
    }

    /**
     * A getter for if the puzzle is solved.
     * @return true, if the puzzle is solved, else false
     */
    public boolean getSolved(){
        return solved;
    }

}