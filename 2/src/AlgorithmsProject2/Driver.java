package AlgorithmsProject2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Driver
{
	private static int N;
	
	public static void main(String[] args)
	{
		int[][] G;
		Process[] processList;
		ArrayList<Integer> noDependencies = new ArrayList<Integer>();
		try
		{

			// p2-testcase.txt
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
			processList[0].D = new int[0];
			
			for (int i = 1; i < processList.length; i++) 
			{
				String s = reader.readLine();
				String[] spl = s.split("\\s+");
				String trimmed = spl[2].substring(1, spl[2].length() - 1);
				String[] stringD;
				if(trimmed.length() == 0){
					stringD = new String[0];
					noDependencies.add(i);
				} else {
					stringD = trimmed.split(",");
				}
				processList[i] = new Process();
				processList[i].id = Integer.parseInt(spl[0]);
				processList[i].t = Integer.parseInt(spl[1]);
				processList[i].D = new int[stringD.length];
				for(int j = 0; j < stringD.length; j++){
					processList[i].D[j] = Integer.parseInt(stringD[j]);
					G[processList[i].D[j]][processList[i].id] = processList[i].D[j];
				}
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
//					System.out.print(G[i][j] + " ");
//				}
//				System.out.println();
//			}
			
			// check feasibility
			
			// Negate edge weights
			for(int i = 0; i <= N; i++){
				for(int j = 0; j <= N; j++){
					if(G[i][j] != 0){
						G[i][j] = -G[i][j];
					}
				}
			}
			
			// Run Bellman-Ford for all noDependencies nodes
			double[] shortest = BF(G, processList, noDependencies);
			
			// in order of start time
			for(int i = 0; i <= N; i ++){
				processList[i].startTime = (int)(-shortest[i]);
			}
			Arrays.sort(processList);
			
			// Print solution
			int min = Integer.MAX_VALUE;
			for(int i = 0; i < N; i ++){
				System.out.println("Process " + processList[i].id + " starts at time " + processList[i].startTime);
			}
			for(int i = 0; i <= N; i ++){
				if(min > shortest[i]){
					min = (int)shortest[i];
				}
			}
			
			System.out.println("Tn = " + (-min));
			
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
	
	public static double[] BF(int[][] G, Process[] processList, ArrayList<Integer> start){
		double[] shortest;
		// initialize shortest to infinity
		shortest = new double[N+1];
		for(int i = 0; i <= N; i ++){
			shortest[i] = Double.POSITIVE_INFINITY;
		}
		// initialize starting nodes to 0
		for(int i : start){
			shortest[i] = 0;
		}
		// go through the passes
		for(int pass = 0; pass <= N; pass ++){ // for every node
			for(int i = 0; i <= N; i ++){      //
				for(int j = 0; j <= N; j ++){  // for every edge
					if(G[i][j] != 0){          //
						shortest[j] = Math.min(shortest[i] + G[i][j], shortest[j]);
					}
				}
			}
		}
		// find the smallest after all passes are done
		return shortest;
//		int min = Integer.MAX_VALUE;
//		for(int i = 0; i <= N; i ++){
//			if(min > shortest[i]){
//				min = (int)shortest[i];
//			}
//		}
//		return min;
	}
}
	

