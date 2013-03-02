package queens;

import java.util.*;

public class QueensAllNew2 {

	int[] nexty;
	boolean[] y;
	boolean[] d;
	int size;
	int[][] board;

	public QueensAllNew2(int n) {
		size = n;
		nexty = new int[n];
		y = new boolean[n];
		d = new boolean[4 * n - 2];// i+j~[0,2n-2];i-j~[-(n-1),n-1]==>[2n-1,4n-3]...+(3n-2)

		int i = 0;
		while (i < n) {
			nexty[i] = 0;
			y[i] = false;
			i++;
		}

		i = 0;
		while (i < 4 * n - 2)
			d[i++] = false;

		board = new int[n][n];
		i = 0;
		int j = 0;
		while (i < n) {
			board[i] = new int[n];
			while (j < n)
				board[i][j++] = 0;
			i++;
		}
	}

	public void solve() {
		int i = 0, j = 0;
		Stack<Integer> stamps = new Stack<Integer>();
		int count = 0;

		ALL: do {
			while (i < size) {
				while (j < size) {
					if (!conflict(i, j)) {
						putdown(i, j);
						stamps.push(j); // keep the current col number for
										// backtracking
						break;
					}

					j = nexty[i];
				}// end while j

				if (j == size) {
					if (stamps.empty()) {
						break ALL;
					}

					int p = --i;
					int q = stamps.pop();

					pullback(p, q);
					j = nexty[p];
				} else {
					i++;

					if (i < size) {
						j = nexty[i];
					}
				}
			}// end while i
			
			count++;
			
			//bactracking
			int p = --i;
			int q = stamps.pop();
			
			pullback(p,q);
			j = nexty[p];
		} while (true);

		System.out.println("Total Solutions: " + count);
	}

	private boolean conflict(int i, int j) {
		if (d[i + j] || d[i - j + 3 * size - 2]) {

			int k;
			for (k = j + 1; k < size; k++) {
				if (!y[k]) {
					nexty[i] = k;
					break;
				}
			}
			if (k == size) {
				nexty[i] = size;
			}

			return true;
		}
		return false;
	}

	private void putdown(int i, int j) {
		board[i][j] = 1;
		y[j] = true;

		if (i < size - 1) {
			int k;
			for (k = 0; k < size; k++) {
				if (!y[k]) {
					nexty[i + 1] = k;
					break;
				}
			}
			if (k == size) {
				nexty[i + 1] = size;
			}
		}

		d[i + j] = true;
		d[i - j + 3 * size - 2] = true;
	}

	private void pullback(int i, int j) {
		board[i][j] = 0;
		y[j] = false;

		int k;
		for (k = j + 1; k < size; k++) {
			if (!y[k]) {
				nexty[i] = k;
				break;
			}
		}
		if (k == size) {
			nexty[i] = size;
		}

		d[i + j] = false;
		d[i - j + 3 * size - 2] = false;
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		QueensAllNew2 q = new QueensAllNew2(15);
		q.solve();
		long endTime = System.currentTimeMillis();
		System.out.println("Time consumed: " + (endTime - startTime));
	}

}
