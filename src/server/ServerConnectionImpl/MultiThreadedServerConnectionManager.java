package server.ServerConnectionImpl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import problemModule.ProblemModule;
import server.ServerConnectionManager;

//plan is to use a decorator to decorate a connection as either a client or a node.

public class MultiThreadedServerConnectionManager implements ServerConnectionManager {

	ExecutorService MTSE = Executors.newCachedThreadPool();
	private final BlockingQueue<ProblemModule> Tasks = new LinkedBlockingQueue<ProblemModule>(); 
	
	public MultiThreadedServerConnectionManager(){
		//TODO Constructor
	}

	public MultiThreadedServerConnectionManager(String host, int port) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void StartServer(){
		//TODO: StartServer
	}
	
	@Override
	public void StartServer(String host, int port) {
		// TODO Auto-generated method stub
		
	}
}

interface connection extends Runnable{
	
//TODO:
	
}

class newconnection implements connection{

	//TODO: add methods to handle connection basics.
	//ObjectRead/ObjectWrite/KeepAlive
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}

class connectionDecorator implements connection{

	protected connection Con;
	public connectionDecorator(connection Con){
		
	}
	@Override
	public void run() {
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

abstract class Client extends connectionDecorator{
	protected connection WrappedCon;
	public Client(connection Con) {
		super(Con);
		WrappedCon=Con;
	}
	
}

