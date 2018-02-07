/* 
 * [ElectricCarAlpha.java]
 * Electric car with extension 1
 * Author: Josh Cai
 * Nov. 6 2017
*/

import java.util.*;
import java.io.*;

public class ElectricCarAlpha{
  
  public static void main(String args[])throws Exception{
    String fileName = "city.txt";
    
    System.out.println(findDistance(fileName));
    
  }
  
  public static int findDistance (String fileName)throws Exception{
    File file = new File (fileName);
    Scanner read = new Scanner (file);
    File file1 = new File ("temp.txt");
    Scanner sc = new Scanner (file1);
    PrintWriter write = new PrintWriter(file1);
    
    int rowSize = read.nextInt();
    int colSize = read.nextInt();
    char[][] map = new char[rowSize][colSize];
    int fuel = read.nextInt();
    read.nextLine();
    String tempLine = "";
    char[][] optimalMap = new char[rowSize][colSize];
    
    for(int row = 0; row < rowSize; row++){
      tempLine = read.nextLine();
      map[row] = tempLine.toCharArray();
      optimalMap[row] = tempLine.toCharArray();
    }
    
    
    read.close();
    
    int[] numComplete = {0};
    solveRoute(map, 1,1,fuel-1,numComplete);
    
    int[] complete = {0};
    complete[0] = sc.nextInt();
    write.print(0);
    write.close();
    
    int[] opFuel = {0}; 
    int optimalFuel = solveFuel(map, 1, 1, fuel-1, complete, opFuel, optimalMap);
    return optimalFuel;

  }
  
  //////////////////////////////////////////////////////////FUEL////////////////////////////////////////////////////
  //finds shortest route and writes the path to file
  
  public static int solveFuel (char[][] map, int row, int col, int fuel, int[] complete, int[] opFuel, char[][]optimalMap) throws Exception{
    
    int free = 0;
    
    //check number of empty spaces
    if (map[row+1][col] == ' '){
      free++;
    }
    if (map[row-1][col] == ' '){
      free++;
    }
    if (map[row][col+1] == ' '){
      free++;
    }
    if (map[row][col-1] == ' '){
      free++;
    }
    
    //if destination
    if (map[row][col+1] == 'D'){
      map[row][col] = '*';
      
      fuel--;
      complete[0]--;
      
      //replace optimal fuel with current fuel if necessary then update optimal map
      if (fuel > opFuel[0]){
        opFuel[0] = fuel;
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length;j++){
            optimalMap[i][j] = map[i][j];
          }
        }
      }
      
      //return fuel until all paths have been found - write optimal path to file
      if (complete[0] <= 0){
        File route = new File ("ROUTE.txt");
        PrintWriter pw = new PrintWriter (route);
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length;j++){
            pw.print(optimalMap[i][j]);
          }
          pw.println("");
        }
        pw.close();
        
        return fuel;
      }
      else{
        return fuel;
      }
    }
    //if one free space: find it and move
    else if(free == 1){
      if (map[row+1][col] == ' '){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveFuel(tempMap1, row+1, col, fuel, complete, opFuel, optimalMap);
      }
      else if (map[row-1][col] == ' '){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveFuel(tempMap1, row-1, col, fuel, complete, opFuel, optimalMap);
      }
      else if (map[row][col+1] == ' '){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveFuel(tempMap1, row, col+1, fuel, complete, opFuel, optimalMap);
      }
      else{
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveFuel(tempMap1, row, col-1, fuel, complete, opFuel, optimalMap);
      }
    }
    //if two free spaces: diverge paths then compare; return lower path
    else if (free == 2){
      if ((map[row+1][col] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row+1, col, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row-1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max(path1, path2);
      }
      else if ((map[row][col+1] == ' ') && (map[row+1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row, col+1, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row+1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max(path1, path2);
      }
      else if ((map[row][col+1] == ' ') && (map[row][col-1] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row, col+1, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row, col-1, fuel, complete, opFuel, optimalMap);
        
        return Math.max(path1, path2);
      }
      else if ((map[row+1][col] == ' ') && (map[row][col-1] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row+1, col, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row, col-1, fuel, complete, opFuel, optimalMap);
        
        return Math.max(path1, path2);
      }
      else if ((map[row][col-1] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row-1, col, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row, col-1, fuel, complete, opFuel, optimalMap);
        
        return Math.max(path1, path2);
      }
      else{
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row, col+1, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row-1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max(path1, path2);
      }
    }
    //if three free spaces: diverge paths then compare; return lower path
    else if (free == 3){
      if ((map[row][col-1] == ' ') && (map[row][col+1] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row, col-1, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row, col+1, fuel, complete, opFuel, optimalMap);
        int path3 = solveFuel(tempMap3, row-1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
      else if ((map[row][col-1] == ' ') && (map[row][col+1] == ' ') && (map[row+1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row, col-1, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row, col+1, fuel, complete, opFuel, optimalMap);
        int path3 = solveFuel(tempMap3, row+1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
      else if ((map[row+1][col] == ' ') && (map[row][col+1] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row+1, col, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row, col+1, fuel, complete, opFuel, optimalMap);
        int path3 = solveFuel(tempMap3, row-1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
      else{
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveFuel(tempMap1, row, col-1, fuel, complete, opFuel, optimalMap);
        int path2 = solveFuel(tempMap2, row+1, col, fuel, complete, opFuel, optimalMap);
        int path3 = solveFuel(tempMap3, row-1, col, fuel, complete, opFuel, optimalMap);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
    }
    //if dead end
    else{
      return 0;
    }
  }
  
  
  //////////////////////////////////////////////////////////ROUTE////////////////////////////////////////////////////
  //Find the total number of possible routes
  
  public static int solveRoute (char[][] map, int row, int col, int fuel, int[] numComplete) throws Exception{
    
    int free = 0;
    
    //check number of empty spaces
    if (map[row+1][col] == ' '){
      free++;
    }
    if (map[row-1][col] == ' '){
      free++;
    }
    if (map[row][col+1] == ' '){
      free++;
    }
    if (map[row][col-1] == ' '){
      free++;
    }
    
    //if destination add one to total routes and write to file, then return fuel
    if (map[row][col+1] == 'D'){
      map[row][col] = '*';
      
      File temp = new File("temp.txt");
      PrintWriter pw = new PrintWriter (temp);
      
      numComplete[0]++;
      pw.print(numComplete[0]);
      pw.close();
      
      fuel--;
   
      return fuel;
    }
    //if one free space: find it and move
    else if(free == 1){
      if (map[row+1][col] == ' '){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveRoute(tempMap1, row+1, col, fuel, numComplete);
      }
      else if (map[row-1][col] == ' '){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveRoute(tempMap1, row-1, col, fuel, numComplete);
      }
      else if (map[row][col+1] == ' '){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveRoute(tempMap1, row, col+1, fuel, numComplete);
      }
      else{
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        
        fuel--;
        return solveRoute(tempMap1, row, col-1, fuel, numComplete);
      }
    }
    //if two free spaces: diverge paths then compare; return lower path
    else if (free == 2){
      if ((map[row+1][col] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row+1, col, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row-1, col, fuel, numComplete);
        
        return Math.max(path1, path2);
      }
      else if ((map[row][col+1] == ' ') && (map[row+1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row, col+1, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row+1, col, fuel, numComplete);
        
        return Math.max(path1, path2);
      }
      else if ((map[row][col+1] == ' ') && (map[row][col-1] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row, col+1, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row, col-1, fuel, numComplete);
        
        return Math.max(path1, path2);
      }
      else if ((map[row+1][col] == ' ') && (map[row][col-1] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row+1, col, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row, col-1, fuel, numComplete);
        
        return Math.max(path1, path2);
      }
      else if ((map[row][col-1] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row-1, col, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row, col-1, fuel, numComplete);
        
        return Math.max(path1, path2);
      }
      else{
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row, col+1, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row-1, col, fuel, numComplete);
        
        return Math.max(path1, path2);
      }
    }
    //if three free spaces: diverge paths then compare; return lower path
    else if (free == 3){
      if ((map[row][col-1] == ' ') && (map[row][col+1] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row, col-1, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row, col+1, fuel, numComplete);
        int path3 = solveRoute(tempMap3, row-1, col, fuel, numComplete);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
      else if ((map[row][col-1] == ' ') && (map[row][col+1] == ' ') && (map[row+1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row, col-1, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row, col+1, fuel, numComplete);
        int path3 = solveRoute(tempMap3, row+1, col, fuel, numComplete);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
      else if ((map[row+1][col] == ' ') && (map[row][col+1] == ' ') && (map[row-1][col] == ' ')){
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row+1, col, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row, col+1, fuel, numComplete);
        int path3 = solveRoute(tempMap3, row-1, col, fuel, numComplete);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
      else{
        
        char [][] tempMap1 = new char[map.length][map[0].length];
        char [][] tempMap2 = new char[map.length][map[0].length];
        char [][] tempMap3 = new char[map.length][map[0].length];
        
        for (int i = 0; i < map.length; i++){
          for (int j = 0; j < map[0].length; j++){
            tempMap1[i][j] = map[i][j];
            tempMap2[i][j] = map[i][j];
            tempMap3[i][j] = map[i][j];
          }
        }
        
        tempMap1[row][col] = '*';
        tempMap2[row][col] = '*';
        tempMap3[row][col] = '*';
        
        fuel--;
        int path1 = solveRoute(tempMap1, row, col-1, fuel, numComplete);
        int path2 = solveRoute(tempMap2, row+1, col, fuel, numComplete);
        int path3 = solveRoute(tempMap3, row-1, col, fuel, numComplete);
        
        return Math.max((Math.max(path1, path2)), path3);
      }
    }
    //if dead end
    else{
      return 0;
    }
  }
  
}