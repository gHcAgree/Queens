package queens;

import java.util.*;

public class QueensAllNew {

	int[] y;
	boolean[] d;
	int size;
	int[][] board;

	public QueensAllNew(int n) {
		size = n;
		y = new int[n];
		d = new boolean[4 * n - 2];// i+j~[0,2n-2];i-j~[-(n-1),n-1]==>[2n-1,4n-3]...+(3n-2)

		int i = 0;
		while (i < n) {
			y[i] = 0;
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
					j = nextj(j);
				}// end while j

				if (j == size) {
					if (stamps.empty())
						break ALL;

					int p = --i;
					int q = stamps.pop();

					clearTrials();
					pullback(p, q);
					j = nextj(q);
				} else {
					i++;

					clearTrials();
					j = nextj();
				}
			}// end while i

			count++;

			// bactracking
			int p = --i;
			int q = stamps.pop();

			pullback(p, q);
			j = nextj(q);
		} while (true);

		System.out.println("Total Solutions: " + count);
	}

	private boolean conflict(int i, int j) {
		if (d[i + j] || d[i - j + 3 * size - 2])
			return true;
		return false;
	}

	private void putdown(int i, int j) {
		board[i][j] = 1;
		y[j] = 1;
		d[i + j] = true;
		d[i - j + 3 * size - 2] = true;
	}

	private void pullback(int i, int j) {
		board[i][j] = 0;
		y[j] = 2;
		d[i + j] = false;
		d[i - j + 3 * size - 2] = false;
	}

	private int nextj(int j) {
		for (int i = j + 1; i < size; i++) {
			if (y[i] == 0) {
				y[i] = 2;
				return i;
			}
		}
		return size;
	}

	private int nextj() {
		for (int i = 0; i < size; i++) {
			if (y[i] == 0) {
				y[i] = 2;
				return i;
			}
		}
		return size;
	}

	private void clearTrials() {
		for (int i = 0; i < size; i++) {
			if (y[i] == 2) {
				y[i] = 0;
			}
		}
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		QueensAllNew q = new QueensAllNew(15);
		q.solve();
		long endTime = System.currentTimeMillis();
		System.out.println("Time consumed: " + (endTime - startTime));
	}

}
