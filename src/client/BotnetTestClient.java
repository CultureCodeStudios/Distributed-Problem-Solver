package client;
import java.io.IOException;

import client.SingleThreadedConnectionManagerImpl.TestClientConnectionManager;
import problemModule.*;
/**
 * Deprecated: but remaining for reference.
 * This is just the most basic of clients just to test the concept of the grid and the Problem Module.
 * I am replacing this with the ModularTestClient so we can quickly and easily run JUnit Tests.
 * Use the ModularTestClient for running tests.
 */
public class BotnetTestClient {

	/* Basic test Values
	 * static Integer[][] TestA = {{1,2,3},{7,8,9}}; //The first matrix 
	 * static Integer[][] TestB = {{5,6,7},{3,4,5}}; //The second matrix
	 * static Integer[][] TestResult = {{6,8,10},{10,12,14}}; //The expected result from the grid	 *
	 */
	
	static Integer[][] TestA; // The first matrix
	static Integer[][] TestB; // The second matrix
	static Integer[][] TestResult; // The expected result from the grid
	static Integer n = 2; // matrix height
	static Integer m = 3; // matrix width
	static String Host; // host to connect to
	static int Port; // port to connect on
	static boolean Success;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//TestProblemModule Task = new TestProblemModule(TestA, TestB, n, m);
		TestA = getTestMatrix(n,m);
		TestB = getTestMatrix(n,m);
		ProblemModule Task = new TestProblemModule(TestA,TestB,n,m);
		TestResult = new Integer[n][m];
		TestResult = (Integer[][]) Task.TestSolver();

		SingleThreadClientConnectionManager Client = new TestClientConnectionManager(null,9090);
		Client.writeObject(Task);
		/*I would like to make the single threaded connection Manager Blocking from this point
		 *and return to the client main only when the problem is complete. 
		 *
		 *Which means adding ReadObject calls in the Connection manager test for keep alive packets and when it
		 *Receives the ProblemModule it would then pass it back to it's parent and continue flow here.
		 */

		//This Receives the object from the Socket
		Object RecievedObj = Client.readObject();
		//this makes sure the object is for the proper interface so it can be safely cast.
		if(RecievedObj instanceof ProblemModule){
			if(((ProblemModule) RecievedObj).TEQ((Object)TestResult)){
				System.out.println("Success!");
				Success = true;
			}else{
				System.out.println("Shit!");
				Success = false;
			}
		} else { 
			System.out.println("Woah! Object Recieved is not a ProblemModule!");
		}
		Client.close();	
	}

	//USE THE MODULAR TEST CLIENT FOR JUNIT TESTING.
	//Access Methods for JUnit feel free to add to them.
	//public boolean Success(){return Success;}

	public static Integer[][] getTestMatrix(Integer n, Integer m){

		Integer [][] Matrix = new Integer[n][m];
		int item = 0;
		Integer Item = 0;
		for(int i=0;i<n;i++){
			for(int k=0;k<m;k++){
				item = (int)(Math.random()*100); 
				Item = (Integer) item;
				Matrix[i][k] = Item;
			}
		}
		return Matrix;
	}
}