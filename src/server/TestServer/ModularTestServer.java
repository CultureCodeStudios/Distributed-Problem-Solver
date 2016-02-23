package server.TestServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import server.ServerConnectionManager;
import server.ServerConnectionImpl.MultiThreadedServerConnectionManager;

//TODO: needs testing to make sure we can use this stays relevant. Some of the logic may need to change based off our architecture changes.
public class ModularTestServer {

	private static String Host;
	private static int Port;
	private static ServerConnectionManager SCM;
	private static SingleThreadServerConnectionManager STSCM;
	private static Executor ConnectionServicer = Executors.newCachedThreadPool();
	private static ServerSocket Server;
	private static boolean Running = false;
	
	public ModularTestServer(){
		
	}
	
	public ModularTestServer(int port){
		Port = port;
	}
	
	public void DefaultTestServer(int port) throws IOException{
		Server = new ServerSocket(Port);
		SCM = new MultiThreadedServerConnectionManager(Port);	
		Running = true;
		while(Running){
			Socket newCon = Server.accept();
			ConnectionServicer.execute(SCM.newCon(newCon));
		}
	}
	
	public void Shutdown() throws IOException{
		Running = false;
		Server.close();
	}
	
	public static String getHost() {
		return Host;
	}
	public static void setHost(String host) {
		Host = host;
	}
	public static int getPort() {
		return Port;
	}
	public static void setPort(int port) {
		Port = port;
	}
	public static ServerConnectionManager getSCM() {
		return SCM;
	}
	public static void setSCM(ServerConnectionManager sCM) {
		SCM = sCM;
	}
	public static SingleThreadServerConnectionManager getSTSCM() {
		return STSCM;
	}
	public static void setSTSCM(SingleThreadServerConnectionManager sTSCM) {
		STSCM = sTSCM;
	}
	public static ServerSocket getServer() {
		return Server;
	}
	public static void setServer(ServerSocket server) {
		Server = server;
	}
	public static boolean isRunning() {
		return Running;
	}
	public static void setRunning(boolean running) {
		Running = running;
	}
	
}
