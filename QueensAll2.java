package queens;

import java.util.ArrayList;

public class QueensAll2 {
	
	boolean[] d;
	int[] position;
	int size;
	ArrayList<int[]> permutation;
	int count;
	
	public QueensAll2(int n) {
		size = n;
		d = new boolean[4*n-2];//i+j~[0,2n-2];i-j~[-(n-1),n-1]==>[2n-1,4n-3]...+(3n-2)
				
		int i=0;
		while(i<4*n-2) d[i++] = false;
		
		position = new int[n];
		for(i=0;i<n;i++) {
			position[i] = i;
		}
		
		count = 0;
		permutation = new ArrayList<int[]>();
	}
	
	private void perm(int st,int ed) {
		if(st==ed) {
			if(!notValidPosition(position))
				count++;
		}
		else {
			for(int i=st;i<=ed;i++) {
				int tmp = position[st];
				position[st] = position[i];
				position[i] = tmp;
				
				perm(st+1,ed);
				
				tmp = position[st];
				position[st] = position[i];
				position[i] = tmp;
			}
		}
	}
	
	private boolean notValidPosition(int[] position) {
		boolean notValid = false;
		
		for(int i=0;i<size;i++) {
			if(d[i+position[i]]||d[i-position[i]+3*size-2]) {
				notValid = true;
				break;
			}
			else {
				d[i+position[i]] = true;
				d[i-position[i]+3*size-2] = true;
			}
		}
		
		for(int i=0;i<d.length;i++)
			d[i] = false;
		
		return notValid;
	}
	
	public void solve() {
		perm(0,size-1);
		System.out.println("Total: "+count);
	}
	
	public static void main(String[] args) {
		QueensAll2 q = new QueensAll2(8);
		q.solve();
	}
}
