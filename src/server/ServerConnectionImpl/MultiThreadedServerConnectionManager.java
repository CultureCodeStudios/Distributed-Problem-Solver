package server.ServerConnectionImpl;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import problemModule.ProblemModule;
import server.ServerConnectionManager;

//plan is to use a decorator to decorate a connection as either a client or a node.
//need to synchronize on the node filter, so the threads cannot reserve the same node
//and it will keep all the nodes busy if each problem management thread gets the proper number of nodes returned.


public class MultiThreadedServerConnectionManager implements ServerConnectionManager,Runnable {

	//TODO: use the MTSE
	private static ExecutorService MTSE = Executors.newCachedThreadPool();
	int Port;
	boolean Running;
	String Host;
	private static ConcurrentHashMap<ProblemModule,Client> Clients = new ConcurrentHashMap<ProblemModule,Client>();
	private static ConcurrentHashMap<Socket,Node> Nodes = new ConcurrentHashMap<Socket,Node>();
	//TODO: use Tasks
	private final BlockingQueue<ProblemModule> Tasks = new LinkedBlockingQueue<ProblemModule>(); 

	//private NodeFilterStrategy Filter = new AllAvailable();
	
	public MultiThreadedServerConnectionManager(){
		//TODO Constructor
	}

	public MultiThreadedServerConnectionManager(int port) {
		Port = port;
	}

	@Override
	public void StartServer() throws IOException{
		// TODO Auto-generated method stub
	}

	@Override
	public void StartServer(int port) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPort(int P){
		Port = P;
	}

	@Override
	public Runnable newCon(Socket conn) throws IOException {
		//TODO this currently classifies the connection based off of what object is first received
		//but this will need to change based off our discussion in class, were going to need to tweak it to use the connect/disconnect functionality.
		connection C = Classify(conn);
		if(C instanceof Node){
			Nodes.put(conn, (Node) C);
		}else if(C instanceof Client){
			Clients.put(((Client) C).getTask(), (Client) C);
		}//future maybe else if(C instanceof Admin){} to add server control/monitoring
		else{conn.close();}
	return null;
	}

	private connection Classify(Socket con){
		connection CC = null;
		// TODO Auto-generated method stub
		return CC;
	}
	
	
	private synchronized Node[] getReadyNodes(){
		// TODO Auto-generated method stub
		
		return null;
	}
	
	@Override
	public void run() {
		// TODO Might not be needed
		//MTSE.submit(task)
		
	}

}

//TODO needs paramaterziation to Callable
interface connection extends Callable{

	// TODO needs cleanup and definition
	//public void SetupStreams();
	public void WriteObject(Object o);
	public void ReadObject();
	//public Packets Status();
	//public void KeepAlive(Packets p);
}

class newconnection implements connection{
	
	

	Socket Sock;
	public newconnection(Socket sock){
		Sock = sock;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void WriteObject(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ReadObject() {
		// TODO Auto-generated method stub

	}

}

class connectionDecorator implements connection{
	
	//TODO needs cleanup/work

	protected connection Con;
	public connectionDecorator(connection Con){

	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void WriteObject(Object o) {
		// TODO Auto-generated method stub

	}
	@Override
	public void ReadObject() {
		// TODO Auto-generated method stub

	}

}

class Node extends connectionDecorator {
	protected connection WrappedCon;
	
	// TODO Add Node functionality to the wrapper
	
	public Node(connection Con) {
		super(Con);
		WrappedCon=Con;
		// TODO Auto-generated constructor stub
	}



}

class Client extends connectionDecorator{
	protected connection WrappedCon;
	private ProblemModule Task;
	
	// TODO Add Client functionality to the wrapper.
	
	public Client(connection Con) {
		super(Con);
		WrappedCon=Con;
	}
	
	public ProblemModule getTask(){
		return Task;
	}
	
	public void setTask(ProblemModule P){
		Task = P;
	}

}

