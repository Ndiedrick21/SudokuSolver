import java.io.*;
import java.util.Scanner;

public class Sudoku{
    private int[][] grid;

    public Sudoku(String filename){
        File pFile = new File(filename);
        try{
            FileReader fin = new FileReader(pFile);
            BufferedReader bin = new BufferedReader(fin);
            Scanner scan = new Scanner(bin.readLine());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}