package AlgorithmsFinalProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Driver
{
	private static int N;
	
	public static void main(String[] args)
	{
		Process[] processList;
		try
		{

			// p1-testcase.txt
			FileReader freader = new FileReader(args[0]);
			BufferedReader reader = new BufferedReader(freader);
			
			N = Integer.parseInt((String)reader.readLine().split("\\s+")[0]);
			
			// loop through file and initialize graph G
			processList = new Process[N];
			
			for (int i = 0; i < processList.length; i++) 
			{
				String s = reader.readLine();
				String[] spl = s.split("\\s+");
				processList[i] = new Process();
				processList[i].id = Integer.parseInt(spl[0]);
				processList[i].t = Integer.parseInt(spl[1]);
				processList[i].d = Integer.parseInt(spl[2]);
			}
			
			HashMap<Integer, ArrayList<ProcessSet>> sizeToSetList = new HashMap<Integer, ArrayList<ProcessSet>>();
			
			// initialize null set
			ArrayList<ProcessSet> listZero = new ArrayList<ProcessSet>();
			ProcessSet p = new ProcessSet();
			p.size = 0;
			p.lateness = 0;
			p.totalTime = 0;
			listZero.add(p);
			sizeToSetList.put(0, listZero);
			
			// the algorithm
			for(int i = 1; i <= N; i++){
				ArrayList<ProcessSet> listI = subsetsOfSize(i, processList);
//				System.out.println(i);
				sizeToSetList.put(i, listI);
				ArrayList<ProcessSet> prevList = sizeToSetList.get(i-1);
				// for every subset of size i
				for(ProcessSet ps : listI){
//					for(int k = 0; k < ps.elements.length; k++){
//						System.out.print(ps.elements[k].id + "\t");
//					}
					int min = Integer.MAX_VALUE;
					// for all of ps's subsets of size i - 1
					for(ProcessSet prev : prevList){
						if(prev.isSubsetOf(ps)){
							// missing is the process in ps not in prev
							Process missing = ps.subtract(prev);
							int missingLateness = Math.max(0, ps.totalTime - missing.d);
							if(prev.lateness + missingLateness < min){
								ps.lastNode = missing;
								ps.lateness = prev.lateness + missingLateness;
								min = ps.lateness;
							}
						}
					}
//					System.out.print(" -- " + ps.lateness + "\n");
				}
			}
			
			// backtracking			
			Process[] inOrder = new Process[N];
			ProcessSet[] orderedSet = new ProcessSet[N];
			inOrder[N - 1] = sizeToSetList.get(N).get(0).lastNode;
			orderedSet[N - 1] = sizeToSetList.get(N).get(0);
			
			for(int i = N - 1; i > 0; i--){
				ArrayList<ProcessSet> prevList = sizeToSetList.get(i);
				for(ProcessSet prev : prevList){
					if(prev.isSubsetOf(orderedSet[i])){
						// missing is the process in ps not in prev
						Process missing = orderedSet[i].subtract(prev);
						if(missing.id == inOrder[i].id){
							orderedSet[i - 1] = prev;
							inOrder[i - 1] = prev.lastNode;
							break;
						}
					}
				}
			}
			int t = 0;
			for(int i = 0; i < inOrder.length; i ++){
				System.out.println("Process " + inOrder[i].id + " starts at time " + t);
				t += inOrder[i].t;
			}
			System.out.println("minLateness = " + sizeToSetList.get(N).get(0).lateness);
			reader.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println ("Error: File not found. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		} 
        catch (IOException e) 
		{
			System.err.println ("Error: IO exception. Exiting...");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static ArrayList<ProcessSet> subsetsOfSize(int n, Process[] processList){
		ArrayList<ProcessSet> returnList = new ArrayList<ProcessSet>();
		Process[] arr = new Process[n];
		//sets size, elements and total time
		recSet(n, returnList, 0, arr, processList);

		return returnList;
	}
	
	public static void recSet(int n, ArrayList<ProcessSet> list, int index, Process[] arr, Process[] processList){
		if(index >= n){
			ProcessSet p = new ProcessSet();
			p.elements = new Process[arr.length];
			p.ids = new int[arr.length];
			for(int i = 0; i < arr.length; i ++){
				p.elements[i] = new Process();
				p.elements[i].id = arr[i].id;
				p.ids[i] = arr[i].id;
				p.elements[i].t = arr[i].t;
				p.elements[i].d = arr[i].d;
			}
			p.size = n;
			for(int i = 0; i < n; i++){
				p.totalTime += arr[i].t;
			}
			list.add(p);
			return;
		}
		for(int i = index == 0 ? 0 : arr[index - 1].id; i < N; i++){
			arr[index] = new Process();
			arr[index].id = processList[i].id;
			arr[index].t = processList[i].t;
			arr[index].d = processList[i].d;
			recSet(n, list, index + 1, arr, processList);
		}
	}
}
	

