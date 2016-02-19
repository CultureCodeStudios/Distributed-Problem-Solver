package client;
import java.io.IOException;

import problemModule.*;
/*
 * This is just the most basic of clients just to test the concept of the grid and the Problem Module.
 * 
 */
public class BotnetClient {

	/* Basic test Values
	 * static Integer[][] TestA = {{1,2,3},{7,8,9}}; //The first matrix 
	 * static Integer[][] TestB = {{5,6,7},{3,4,5}}; //The second matrix
	 * static Integer[][] TestResult = {{6,8,10},{10,12,14}}; //The expected result from the grid	 *
	 */
	static Integer[][] TestA;
	static Integer[][] TestB; //The second matrix
	static Integer[][] TestResult; //The expected result from the grid
	static Integer n = 2; 
	static Integer m = 3;
	static String Host;
	static int Port;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//TestProblemModule Task = new TestProblemModule(TestA, TestB, n, m);
		TestA = getTestArray(n,m);
		TestB = getTestArray(n,m);
		ProblemModule Task = new TestProblemModule(TestA,TestB,n,m);
		TestResult = new Integer[n][m];
		TestResult = (Integer[][]) Task.TestSolver();
		ClientConnectionManger Client = new TestClientConnectionManager(null,9090);
		Client.writeObject(Task);
		Object RecievedObj = Client.readObject();
		if(RecievedObj instanceof TestProblemModule){
			if(((TestProblemModule) RecievedObj).TEQ((Object)TestResult)){
				System.out.println("Success!");
			}else{
				System.out.println("Shit!");
			}
		}
		Client.close();	
	}
	
	
	
	public static Integer[][] getTestArray(Integer n, Integer m){
		
		Integer [][]array = new Integer[n][m];
		int item = 0;
		Integer Item = 0;
		for(int i=0;i<n;i++){
			for(int k=0;k<m;k++){
			item = (int)(Math.random()*100); 
			Item = (Integer) item;
			array[i][k] = Item;
			}
		}
		return array;
	}
}