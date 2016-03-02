package server.TestServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import problemModule.ProblemModule;
import problemModule.TestProblemModule;

/*
 * Made by: Adam Kozlowski
 * Made on: 2/28/2016
 * Last Updated: 2/28/2016
 * 
 *  Command and Control Server for the Problem Distribution Net.
 *  The server listens for a connection from a node.
 *  If the node sends a -1 as UTF String, the server assigns it a uniqueID number and then uses that ID to store
 *  info about the node in hash tables.
 *  
 *  If a node is in the network it can be sent work at anytime.
 *  Work is sent by serializing a ProblemModule Implementation.
 *  Once the server has sent work to a node it flags that node as
 *  working, and will not bother that node with anymore jobs.
 *  Once the server gets a new connection with a node with work out 
 *  it will immediately expect the class it sent back solved.
 *  
 *  Then the server needs to put the distributed jobs back in order,
 *  call finalize and then print a result.
 *  
 *  TO DO: 
 *  move a lot of the input/output and connection managing to other classes than can be multithreaed
 *  handle job distribution and job return
 * 	
 *  */

public class AuthenticatingServer {

	private Hashtable<Long, Socket> nodeInfo;
	private Hashtable<Long, Boolean> nodeWorkStatus;
	private Hashtable <Long, Boolean> nodeConnectionStatus;
	private Hashtable <Long, Boolean> nodeIDTaken;
	
	//keep track of ProblemModule pieces
	private ArrayList<ProblemModule> problemModulesToSolve;
	private ArrayList<ProblemModule> problemModuleBrokenDown;
	private ArrayList<ProblemModule> problemModuleSolved;
	
	private static ServerSocket server;
	private static int port;
	private DataInputStream input;
	private ObjectInputStream objectIn;
	private DataOutputStream output;
	private ObjectOutputStream objectOut;
	
	
	public AuthenticatingServer(int port) throws IOException{
		this.port = port;
		server = new ServerSocket(port);
		nodeInfo = new Hashtable<Long, Socket>();
		nodeWorkStatus = new Hashtable<Long, Boolean>();
		nodeConnectionStatus = new Hashtable<Long, Boolean>();
		nodeIDTaken = new Hashtable<Long, Boolean>();
		
	}
	
	//listen for a new connection from a node
	public void handleConnection() throws IOException, ClassNotFoundException{ //eventually this needs to be multithreaded
		Socket node = server.accept();
		input = new DataInputStream(node.getInputStream());
		objectIn = new ObjectInputStream(input);
		output = new DataOutputStream(node.getOutputStream());
		objectOut = new ObjectOutputStream(output);
		
		String idAsString = input.readUTF();
		Long nodeID = Long.parseLong(idAsString);
		
		if(nodeID == -1L){
			//handle a new node joining the network
			System.out.println("New node connected, uniqueID " + handleNewNode(node) + "assigned");
		}else if( nodeWorkStatus.get(nodeID) != null && nodeWorkStatus.get(nodeID) ){ //check if we have a pending job for them
			//handle getting a job back solved
			System.out.println("Getting solution back from: " + handleAnswerReturned(nodeID));
		}else{
			//handle an already ID'd client be available for work
			if(nodeConnectionStatus.get(nodeID) != null && !nodeConnectionStatus.get(nodeID)){
				System.out.println("New node connected: "+handleNodeReconnect(node, nodeID));
			}else{ //or remove that client from the list of available clients
				System.out.println("Node disconnecting: "+handleNodeDisconnect(nodeID));
			}
			
		}
		
	}
	
	//handle a new node joining the network
	public long handleNewNode(Socket node) throws IOException{
		
		//assign a unique ID
		boolean idIsGood = false;
		long randomID = 0;
		while(!idIsGood){
			
			//generate a random number
			Random r = new Random();
			r.nextLong();
			
			if(nodeIDTaken.get(randomID) == null)
				idIsGood = true;
			else if(randomID-1 > 0)
				randomID--;
			else
				r.nextLong();
				//generate a new random number
		}
		
		nodeInfo.put(randomID, node);
		nodeConnectionStatus.put(randomID, true);
		nodeWorkStatus.put(randomID, false);
		nodeIDTaken.put(randomID, true);
		
		output.writeLong(randomID);
		
		return randomID;
		
	}
	
	//handle getting a job from the user and distributing it
	
	//handle getting a job back solved
	public long handleAnswerReturned(Long id) throws ClassNotFoundException, IOException{
		
		ProblemModule solvedProblem = (ProblemModule) objectIn.readObject();
		
		nodeWorkStatus.replace(id, false);
		
		problemModuleSolved.add(solvedProblem);
		//sort the problemModuleSolved so our answers are in the right order
		if(problemModuleSolved.size() == problemModuleBrokenDown.size())
		{
			ProblemModule[] solvedArray = (ProblemModule[]) problemModuleSolved.toArray();
			ProblemModule answer = new TestProblemModule(); //for testing only
			answer.finalize(solvedArray);
		}
		
		return id;
		
	}
	
	//handle a node leaving the network
	public Long handleNodeDisconnect(Long id) throws IOException{
		
		nodeConnectionStatus.replace(id, false);
		nodeWorkStatus.replace(id, false);
		
		input.close();
		output.close();
		nodeInfo.get(id).close();
		nodeInfo.replace(id, null);
		
		return id;
	}
	
	//handle a node joining the network
	public Long handleNodeReconnect(Socket node, Long id){
		
		nodeConnectionStatus.replace(id, true);
		nodeWorkStatus.replace(id, false);
		nodeInfo.replace(id, node);
		
		return id;
	}
	
	
	
}
