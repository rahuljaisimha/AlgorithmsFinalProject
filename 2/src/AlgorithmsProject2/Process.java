package AlgorithmsProject2;

import java.util.Comparator;

public class Process implements Comparator<Process>, Comparable<Process>{
	int id;
	int t;
	int[] D;
	public int startTime;
	
	@Override
	public int compare(Process arg0, Process arg1) {
		return arg1.startTime - arg0.startTime;
	}

	@Override
	public int compareTo(Process arg0) {
		return this.startTime - arg0.startTime;
	}
}
