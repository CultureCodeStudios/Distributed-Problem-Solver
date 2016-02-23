package client;

import java.io.IOException;
import java.net.UnknownHostException;

import client.SingleThreadedConnectionManagerImpl.TestClientConnectionManager;
import problemModule.ProblemModule;
import problemModule.TestProblemModule;

/**
* Still only for matrix addition but way easier to run JUnit Tests with for testing different configurations. 
*/

public class ModularTestClient {

	private static Integer[][] A;
	private static Integer[][] B; //The second matrix
	private static Integer[][] TestResult; //The expected result from the grid
	private static Integer N; 
	private static Integer M;
	private static String Host;
	private static int Port;
	private static boolean Success;
	private static ProblemModule tosend;
	private static Object recv;
	private static SingleThreadClientConnectionManager CCM;
	private static MultiThreadedClientconnectionManager MTCCM;

	/**
	 * public ModularTestClient()
	 * EmptyConstructor default constructor
	 */
	public ModularTestClient(){

	}
	
	/**
	 * public ModularTestClient(String host,int port)
	 * Constructor
	 * @param host the host name that you are going to connect to.
	 * @param port the server port number you are going to connect on.
	 */

	public ModularTestClient(String host,int port){
		Host = host;
		Port = port;
	}
	
	/**
	 * public ModularTestClient(String host,int port,Integer[][] a, Integer[][] b)
	 * Constructor
	 * @param host the host name that you are going to connect to.
	 * @param port the server port number you are going to connect on.
	 * @param a A two-dimensional array of Integers that represents the first matrix
	 * @param b A two-dimensional array of Integers that represents the second matrix
	 */

	public ModularTestClient(String host,int port,Integer[][] a,Integer[][] b){
		Host = host;
		Port = port;
		A = a;
		B = b;
		N = A.length;
		M = A[0].length;
	}

	/**
	 * public void Start() throws IOException, ClassNotFoundException
	 * @throws IOException Socket can throw an exception
	 * @throws ClassNotFoundException If the object recieved from the client connection manager cannot be decoded
	 */
	public void Start() throws IOException, ClassNotFoundException{
		CCM.writeObject(tosend);
		recv = CCM.readObject();
		computeTestResult();
		TEQ();
	}

	/**
	 * public void MultiThreadStart()
	 * If we create a multithreaded client connection manager we can start from here.
	 */
	public void MultiThreadStart(){
		//TODO: Future/Stretch
	}

	
	/**
	 * public void startWithDefaults(String host,int port, int n, int m) throws UnknownHostException, IOException, ClassNotFoundException
	 * @param host the host name that you are going to connect to.
	 * @param port the server port number you are going to connect on.
	 * @param n the height of the matrix
	 * @param m the width of the matrix
	 * @throws UnknownHostException Bad hostname
	 * @throws IOException 
	 * @throws ClassNotFoundException does not recognize the received object.
	 */
	public void startWithDefaults(String host,int port,int n,int m) throws UnknownHostException, IOException, ClassNotFoundException{
		randMatricies(n,m);
		packProblemModule();
		setSingleThreadClientConnectionManager(new TestClientConnectionManager(host,port));
		CCM.writeObject(tosend);
		recv = CCM.waitForResult();
		computeTestResult();
		TEQ();
	}


	/**
	 * Getter Methods for attributes of ModularTestClient
	 */
	public Integer[][] getA(){return A;}
	public Integer[][] getB(){return B;}
	public Integer getN(){return N;}
	public Integer getM(){return M;}
	public String getHost(){return Host;}
	public int getPort(){return Port;}
	public boolean getSuccess(){return Success;}
	public ProblemModule getProblemModule(){return tosend;}
	public Integer[][] getTestResult(){
		TestResult = new Integer[N][M];
		TestResult = (Integer[][]) tosend.TestSolver(); 
		return TestResult;
	}


	/**
	 * setter Methods for attributes of ModularTestClient
	 */
	public void setSingleThreadClientConnectionManager(SingleThreadClientConnectionManager ccm){CCM = ccm;}
	public void setA(Integer[][] a){A = a;}
	public void setB(Integer[][] b){B = b;}	
	public void setHost(String host){Host=host;}
	public void setPort(int port){Port=port;}
	public void setProblemModule(ProblemModule p){tosend = p;}

	/**
	 * Utility methods for the ModularTestClient
	 */
	
	/**
	 * public void computeTestResult()
	 * Computes the result of the problem module in the tosend attribute
	 * and saves it into the TestResult attribute for later use.
	 */
	public void computeTestResult(){
		TestResult = new Integer[N][M];
		TestResult = (Integer[][]) tosend.TestSolver(); 
	}

	/**
	 * public void packProblemModule()
	 * creates a new TestProblemModule with the attributes in A,B,N,M and saves it to the tosend attribute for later use.
	 */
	public void packProblemModule(){
		tosend = new TestProblemModule(A,B,N,M);
	}
	
	
	/**
	 * public void randMatricies(int n,int m)
	 * creates two identically sized matrices that are then randomly populated and placed in the proper attribute slot
	 * @param n the height of the desired randomly populated matrix
	 * @param m the width of the desired random populated matrix
	 */
	public void randMatricies(int n,int m){
		A=new Integer[n][m];
		B=new Integer[n][m];
		A=getTestMatrix(n,m);
		B=getTestMatrix(n,m);
		recalcNM();
	}

	/**
	 * public void recalcNM()
	 * recalculates the N and M attributes based off of the A matrix height and width.
	 */
	public void recalcNM(){
		N = A.length;
		M = A[0].length;
	}

	/**
	 * public boolean TEQ()
	 * Tests if the received problem modules result matches the calculated test result
	 * @return True if the result in the received problemmodule matches calculated result false if otherwise
	 * False with error message if the received object tested is not a problem module 
	 */
	public boolean TEQ(){
		if(recv instanceof ProblemModule){
			if(((ProblemModule) recv).TEQ((Object)TestResult)){
				Success = true;
				return true;
			}else{
				Success = false;
				return false;
			}
		} else { System.out.println("Woah! Object Recieved is not a ProblemModule!");}
		return false;
	}


	/**
	 * public static Integer[][] getTestMatrix(Integer n, Integer m) 
	 * generates a randomly populated n by m sized matrix 
	 * @param n 
	 * @param m
	 * @return Integer[][] 
	 */
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

	//adding Future MultiThreadedClient Support;
	public static MultiThreadedClientconnectionManager getMTCCM() {
		return MTCCM;
	}

	public static void setMTCCM(MultiThreadedClientconnectionManager mTCCM) {
		MTCCM = mTCCM;
	}
}

