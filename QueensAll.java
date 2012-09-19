package queens;

import java.util.*;

public class QueensAll {
	
	boolean[] x;
	boolean[] y;
	boolean[] d;
	int size;
	int[][] board;
	
	public QueensAll(int n) {
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
		int i=0,j=0;
		Stack<Integer> stamps = new Stack<Integer>();
		int count = 0;
		
		ALL:
		do {
			while(i<size) {
				while(j<size) {						
					if(!conflict(i,j)) {
						putdown(i,j);
						stamps.push(j);  //keep the current col number for backtracking
						break;
					}			
					j++;
				}//end while j
			
				if(j==size) {
					if(stamps.empty()) break ALL;
					
					int p = --i;
					int q = stamps.pop();
					pullback(p,q);
				
					j = q+1;
				}
				else {
					i++;
					j = 0;
				}
			}//end while i
		
			count++;
		
			//bactracking
			int p = --i;
			int q = stamps.pop();
			pullback(p,q);
			j = q+1;
		} while(true);
		
		System.out.println("Total Solutions: "+count);
	}
	
	private boolean conflict(int i,int j) {
		if(x[i] || y[j] || d[i+j] || d[i-j+3*size-2])
			return true;
		return false;
	}
	
	private void putdown(int i,int j) {
		board[i][j] = 1;
		x[i] = true;
		y[j] = true;
		d[i+j] = true;
		d[i-j+3*size-2] = true;
	}
	
	private void pullback(int i,int j) {
		board[i][j] = 0;
		x[i] = false;
		y[j] = false;
		d[i+j] = false;
		d[i-j+3*size-2] = false;
	}
	
	
	
	public static void main(String[] args) {
		QueensAll q = new QueensAll(8);
		q.solve();
	}
	
	
}
