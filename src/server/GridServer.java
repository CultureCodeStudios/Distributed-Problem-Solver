package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.ServerConnectionImpl.MultiThreadedServerConnectionManager;


public class GridServer {

	static String Host;
	static int Port;
	static ServerSocket Server;
	static ServerConnectionManager SCM;
	static boolean Running = false;
	private static ExecutorService ConnectionServicer = Executors.newCachedThreadPool();

	public static void main(String[] args) throws IOException {
		Server = new ServerSocket(Port);
		SCM = new MultiThreadedServerConnectionManager(Port);
		Running = true;
		while(Running){
			Socket newCon = Server.accept();
			ConnectionServicer.submit(SCM.newCon(newCon));
		}
	}

	public void Shutdown(){
		Running = false;
	}
}