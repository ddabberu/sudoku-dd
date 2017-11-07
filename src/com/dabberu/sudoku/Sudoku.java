package com.dabberu.sudoku;
import java.util.*;

/**
 * 
 * @author dabberu
 *
 */
public class Sudoku {
	
	int size;
	int [][] grid;
	
	
	
/**
 * Initializes a 9*9 Grid
 */
	public Sudoku()
	{
		this.size=9;
		grid= new int[size][size];
	}
	/**
	 * Setup play with randomized integers 
	 */
	public void setPlay()
	{
		//position int 1--9 in the grid randomly
		List<Integer> rlist,clist;
		rlist= new ArrayList<>();
		for( int i=0; i< size; i++)
		{
			rlist.add(i);
			
		}
		/// copy list to another and shuffle
		clist= new ArrayList<>(rlist);
		///randomize list
		Collections.shuffle(clist); //just shuffle it
		
		int i=1;
		int index=0;
		while( i <10)
		{
			int ri= rlist.get(index);
			int ci= clist.get(index++);
			if( grid[ri][ci] == 0)
			{
				grid[ri][ci]=i;
			}
			i++;
		}
	}
	
	/**
	 * Recursive function
	 * @param arr
	 * @return true for solved sudoku, false for error
	 */
	public boolean solve(int[][]arr)
	{
		int[] index= findASpace(arr);
		if( index == null) return true;
		else
		{
			int row= index[0]; int col= index[1];
			//printMe( "index:"+row+","+col);
			for( int i=1; i< 10; i++)
			{
				if( isGood( arr, row,col, i))
				{
					//assign the number
					arr[row][col]= i;
					//recurse
					if( solve(arr)) return true;
					//retry
					arr[row][col]=0;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * @param arr
	 * @param row
	 * @param col
	 * @param num
	 * @return
	 */
	boolean isGood(  int[][] arr, int row, int col, int num)
	{
		for( int i=0; i < size; i++)
		{
			if(    arr[row][i] == num) return false;
		}
		//check for col
		for( int i=0; i < size; i++)
		{
			if(    arr[i][col] == num) return false;
		}		
		//check in subgrid
		return !findInSubgrid( arr, row- (row%3), col-(col%3), num);
	}
	/**
	 * 
	 * @param arr
	 * @param startRow--Start of 3*3 Grid Row
	 * @param startCol--Start of 3*3 Grid Col
	 * @param num
	 * @return true if it finds, else false
	 */
	boolean findInSubgrid( int[][] arr, int startRow, int startCol, int num)
	{
		for( int i=0; i < 3; i++)
		{
			for( int j=0; j < 3; j++)
			{
				if( arr[i+startRow][j+startCol] == num) {
					printMe( "Found "+num+" at ("+(i+startRow)+","+(j+startCol)+")");
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * @param arr
	 * @return the position of space
	 */
	int[] findASpace(int[][]arr)
	{

		for( int i=0; i < size; i++)
		{
			for( int j=0; j < size; j++)
			{
				if( arr[i][j]== 0)
				{
					int[] index= {i,j};
					//printMe( "empty space at: "+index[0]+","+index[1]);
					return index;
				}
			}
		}
		return null;
	}
	
	void printMe(String str)
	{
		System.out.println(str);
	}
	public void printPuzzle()
	{
		System.out.println("==============================================");
		for( int i=0;i<size; i++)
		{
			for( int j=0; j< size; j++)
			{
				System.out.print(" "+grid[i][j]);
			}
			System.out.println("\n");
		}
		System.out.println("==============================================");
	}
	
	public static void main( String args[])
	{
		Sudoku s= new Sudoku();
		s.setPlay();
		System.out.println("---PLAY--");
		s.printPuzzle();
		System.out.println("---SOLUTION--");
		s.solve(s.grid);
		s.printPuzzle();
	}
}
