package queens;

import java.util.*;

public class Queens2 {
	
	boolean[] x;
	boolean[] y;
	boolean[] d;
	int size;
	int[][] board;
	
	public Queens2(int n) {
		size = n;
		x = new boolean[n];
		y = new boolean[n];
		d = new boolean[4*n-2];//i+j~[0,2n-2];i-j~[-(n-1),n-1]==>[2n-1,4n-3]...+(3n-2)
		
		int i=0;
		while(i<n) {
			x[i] = false;
			y[i] = false;
			i++;
		}
		
		i=0;
		while(i<4*n-2) d[i++] = false;
		
		board = new int[n][n];
		i=0;
		int j=0;
		while(i<n) {
			board[i] = new int[n];
			while(j<n) board[i][j++] = 0;
			i++;
		}
	}
	
	public void solve() {
		int i,j;
		Stack<Integer> stamps = new Stack<Integer>();
		boolean backtrack = false;
		
		for(i=0;i<size;i++) {
			for(j=0;j<size;j++) {			
				if(backtrack) {
					j = stamps.pop()+1;
					backtrack = false;
				}
				
				if(j==size) break;
				
				if(!(x[i]||y[j]||d[i+j]||d[i-j+3*size-2])) {
					board[i][j] = 1;
					x[i] = true;
					y[j] = true;
					d[i+j] = true;
					d[i-j+3*size-2] = true;
					
					backtrack = false;
					stamps.push(j);  //keep the current col number for backtracking
					break;
				}
			}
			
			if(j==size) {
				backtrack = true;
				int p = i-1;
				int q = stamps.peek();
				board[p][q] = 0;
				x[p] = false;
				y[q] = false;
				d[p+q] = false;
				d[p-q+3*size-2] = false;
				i-=2;
			}
		}
		
		for(int m=0;m<size;m++) {
			for(int n=0;n<size;n++)
				System.out.print(board[m][n]+" ");
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		Queens2 q = new Queens2(8);
		q.solve();
	}
	
	
}
