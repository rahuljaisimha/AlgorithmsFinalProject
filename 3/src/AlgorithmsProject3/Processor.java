package AlgorithmsProject3;

import java.util.Comparator;

public class Processor implements Comparator<Processor>, Comparable<Processor>{
	public int id;
	public int t;
	
	Processor(int i){
		id = i;
	}

	@Override
	public int compare(Processor arg0, Processor arg1) {
		return arg1.t - arg0.t;
	}

	@Override
	public int compareTo(Processor arg0) {
		return this.t - arg0.t;
	}
}
