package server;

import server.ServerConnectionImpl.MultiThreadedServerConnectionManager;

public class GridServer {

	static String Host;
	static int Port;
	static ServerConnectionManager SCM;
	
	public static void main(String[] args) {
	SCM = new MultiThreadedServerConnectionManager(Host,Port);
	SCM.StartServer();
	}
	
	
}