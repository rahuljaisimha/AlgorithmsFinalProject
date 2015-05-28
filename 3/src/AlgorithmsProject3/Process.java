package AlgorithmsProject3;

import java.util.ArrayList;
import java.util.Comparator;

public class Process implements Comparator<Process>, Comparable<Process>{
	public int id;
	public int t;
	public ArrayList<Integer> D;

	@Override
	public int compare(Process arg0, Process arg1) {
		if(!Driver.T3B)	return arg0.t - arg1.t;
		else return arg1.id - arg0.id;
	}

	@Override
	public int compareTo(Process arg0) {
		if(!Driver.T3B) return arg0.t - this.t;
		else return this.id - arg0.id;
	}
}
