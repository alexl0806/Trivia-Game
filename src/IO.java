/* 
 * Author: Alex L (Taken from Google Classroom)
 * Date: January 6, 2019
 * Description: IO class to make using IO functions easier.
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IO
{
  private static PrintWriter fileOut;
  private static BufferedReader fileIn;
  
  public static void createOutputFile(String fileName) throws IOException
  {
    fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
  }
  
  public static void appendOutputFile(String fileName) throws IOException
  {
      fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
  }
  
  public static void print(String text) throws IOException
  {
    fileOut.print(text);
  }
  
  public static void println(String text) throws IOException
  {
    fileOut.println(text);
  }
  
  public static void closeOutputFile() throws IOException
  {
    fileOut.close();
  }
  
  public static void openInputFile(String fileName) throws IOException
  {
    fileIn = new BufferedReader(new FileReader(fileName));
  }
  
  public static String readLine()throws IOException    
  {
    return fileIn.readLine();
  }
  
  public static void closeInputFile()throws IOException
  {
    fileIn.close();
  }
}