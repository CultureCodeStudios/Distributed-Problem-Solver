package server.ServerConnectionImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import common.Packets;
import problemModule.ProblemModule;
import server.NodeFilterStrategy;
import server.ServerConnectionManager;

//plan is to use a decorator to decorate a connection as either a client or a node.
//need to synchronize on the node filter, so the threads cannot reserve the same node
//and it will keep all the nodes busy if each problem management thread gets the proper number of nodes returned.


public class MultiThreadedServerConnectionManager implements ServerConnectionManager,Runnable {

	private static ExecutorService MTSE = Executors.newCachedThreadPool();
	int Port;
	boolean Running;
	String Host;
	private static ConcurrentHashMap<ProblemModule,Client> Clients = new ConcurrentHashMap<ProblemModule,Client>();
	private static ConcurrentHashMap<Socket,Node> Nodes = new ConcurrentHashMap<Socket,Node>();
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
		connection C = Classify(conn);
		if(C instanceof Node){
			Nodes.put(conn, (Node) C);
		}else if(C instanceof Client){
			Clients.put(((Client) C).getTask(), (Client) C);
		}//future maybe else if(C instanceof Admin){}
		else{conn.close();}
	return null;
	}

	private connection Classify(Socket con){
		connection CC = null;
		
		return CC;
	}
	
	
	private synchronized Node[] getReadyNodes(){
		
		
		return null;
	}
	
	@Override
	public void run() {
		//MTSE.submit(task)
		
	}

}

interface connection extends Callable{

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
	public Node(connection Con) {
		super(Con);
		WrappedCon=Con;
		// TODO Auto-generated constructor stub
	}



}

class Client extends connectionDecorator{
	protected connection WrappedCon;
	private ProblemModule Task;
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

