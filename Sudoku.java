import java.io.*;
import java.util.Scanner;

public class Sudoku{
    private int[][] grid;

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

}