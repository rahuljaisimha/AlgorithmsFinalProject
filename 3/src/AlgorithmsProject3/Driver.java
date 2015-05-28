package AlgorithmsProject3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Driver
{
	private static int N;
	private static int noOfProcessors = 3;
	public static boolean T3B = false;
	
	public static void main(String[] args)
	{
		doEverything(args);
		T3B = true;
		doEverything(args);
	}
	public static void doEverything(String[] args){
		int[][] G;
		Process[] processList;
		ArrayList<Process> noDependencies = new ArrayList<Process>();
		try
		{

			// p3-testcase.txt
			FileReader freader = new FileReader(args[0]);
			BufferedReader reader = new BufferedReader(freader);
			
			N = Integer.parseInt(reader.readLine());
			
			// loop through file and initialize graph G
			G = new int[N+1][N+1];
			processList = new Process[N+1];
			
			// initialize dummy
			processList[0] = new Process();
			processList[0].id = 0;
			processList[0].t = 0;
			processList[0].D = new ArrayList<Integer>();
			
			// initialize
			for (int i = 1; i <= N; i++) 
			{
				String s = reader.readLine();
				String[] spl = s.split("\\s+");
				String trimmed = spl[2].substring(1, spl[2].length() - 1);
				String[] stringD;
				if(trimmed.length() == 0){
					stringD = new String[0];
				} else {
					stringD = trimmed.split(",");
				}
				processList[i] = new Process();
				processList[i].id = Integer.parseInt(spl[0]);
				processList[i].t = Integer.parseInt(spl[1]);
				processList[i].D = new ArrayList<Integer>();
				for(int j = 0; j < stringD.length; j++){
					processList[i].D.add(Integer.parseInt(stringD[j]));
					G[processList[i].D.get(j)][processList[i].id] = processList[i].D.get(j);
				}
				if(processList[i].D.size() == 0)
					noDependencies.add(processList[i]);
			}
			
			// change weights to time
			for(int i = 1; i <= N; i++){
				for(int j = 1; j <= N; j++){
					if(G[i][j] != 0){
						G[i][j] = processList[G[i][j]].t;
					}
				}
			}
			
			// set dummy weights
			for(int i = 1; i <= N; i++){
				int count = 0;
				for(int j = 1; j <= N; j++){
					if(G[i][j] != 0){
						count++;
						break;
					}
				}
				if(count == 0)
					G[i][0] = processList[i].t;
			}
			
			// print adjacency matrix
//			for(int i = 0; i <= N; i++){
//				for(int j = 0; j <= N; j++){
//					System.out.print(G[i][j] + "\t");
//				}
//				System.out.println();
//			}
			
			// check feasibility
			
			// arraylist for all finished processes
			ArrayList<Process> finished= new ArrayList<Process>();
			HashMap<Process, Integer> finishingTime = new HashMap<Process, Integer>();
			
			// create priority queues
			PriorityQueue<Process> processQ = new PriorityQueue<Process>(noDependencies);
			PriorityQueue<Processor> processorQ = new PriorityQueue<Processor>();
			for(int i = 0; i < noOfProcessors; i ++){
				processorQ.add(new Processor(i+1));
			}
			
			// loop through time till all processes are scheduled
			for(int t = 0; finished.size() < N; t++){
				// mark finished processes
				Iterator<Process> it = finishingTime.keySet().iterator();
				while(it.hasNext()){
					Process proc = it.next();
					// if proc has finished
					if(finishingTime.get(proc) <= t){
						it.remove();
						finished.add(proc);
						// remove dependency from its children
						for(int i = 1; i <= N; i ++){
							if(G[proc.id][i] != 0){
								processList[i].D.remove(new Integer(proc.id));
								// if now independent, add to Q
								if(processList[i].D.size() == 0){
									processQ.add(processList[i]);
								}
							}
						}
					}
				}
				// while some processor is free at time t
				Processor p = processorQ.peek();
				while(p.t <= t && processQ.size() > 0){
					p = processorQ.poll();
					// add process to processor
					Process proc = processQ.poll();
					if(!T3B)
						System.out.println("Process " + proc.id + " starts at time " + t + " on Processor " + p.id);
					p.t = t + proc.t;
					finishingTime.put(proc, p.t);
					processorQ.add(p);
					p = processorQ.peek();
				}
			}
			
			// Print solution
			processorQ.poll();
			processorQ.poll();
			if(!T3B)
				System.out.println("T3 = " + processorQ.poll().t);
			else
				System.out.println("T3B = " + processorQ.poll().t);
			
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
	
}
	

