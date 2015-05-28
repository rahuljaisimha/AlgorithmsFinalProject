package AlgorithmsFinalProject;

import java.util.ArrayList;
import java.util.Arrays;

public class ProcessSet {
	public int size;
	public Process[] elements;
	public int[] ids;
	public int lateness;
	public Process lastNode;
	public int totalTime;
	
	public boolean isSubsetOf(ProcessSet superSet){
		if(this.size >= superSet.size){
			return false;
		}
		if(this.size == 0){
			return true;
		}
//		ArrayList<Process> elementsList = new ArrayList<Process>(Arrays.asList(superSet.elements));
//		for(int i = 0; i < elements.length; i ++){
//			if(!elementsList.contains(elements[i]))
//				return false;
//		}
		for(int i = 0; i < ids.length; i++){
			int count = 0;
			for(int j = 0; j < superSet.ids.length; j++){
				if(ids[i] == superSet.ids[j]){
					count++;
				}
			}
			if(count == 0){
				return false;
			}
		}
		return true;
	}
	
	// assumes smaller is a subset of this and returns the one missing Process
	public Process subtract(ProcessSet smaller){
		if(this.elements.length == 1){
			return this.elements[0];
		}
//		ArrayList<Process> elementsList = new ArrayList<Process>(Arrays.asList(this.elements));
//		for(int i = 0; i < smaller.elements.length; i ++){
//			if(!elementsList.contains(smaller.elements[i]))
//				return smaller.elements[i];
//		}
		for(int i = 0; i < ids.length; i++){
			int count = 0;
			for(int j = 0; j < smaller.ids.length; j++){
				if(this.ids[i] == smaller.ids[j]){
					count++;
				}
			}
			if(count == 0){
				return this.elements[i];
			}
		}
		return null;
	}
}
