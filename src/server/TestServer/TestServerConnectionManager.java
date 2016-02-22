package server.TestServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import problemModule.ProblemModule;
import problemModule.TestProblemModule;

public class TestServerConnectionManager implements SingleThreadServerConnectionManager {
	private Socket Client;
	private ServerSocket Server;
	private DataOutputStream DataOut;
	private ObjectOutputStream obOut;
	private DataInputStream DataIn;
	private ObjectInputStream obIn;
	
	public TestServerConnectionManager(String host,int port) throws UnknownHostException, IOException{
		Server = new ServerSocket(9090);
		Socket Client = Server.accept();
		System.out.println("Client Connected");
		
		DataOut = new DataOutputStream(Client.getOutputStream());
		obOut = new ObjectOutputStream(DataOut);
		DataIn = new DataInputStream(Client.getInputStream());
		obIn = new ObjectInputStream(DataIn);
	}

	@Override
	public Object readObject() throws ClassNotFoundException, IOException {return obIn.readObject();}
	//public void shutdownOutput() throws IOException {Client.shutdownOutput();}
	@Override
	public void writeObject(TestProblemModule task) throws IOException {obOut.writeObject(task);Client.shutdownOutput();}
	@Override
	public void close() throws IOException{Client.close();}
	
	@Override
	public void ServerShutdown() throws IOException{Server.close();}

	@Override
	public void writeObject(ProblemModule task) throws IOException {
		obOut.writeObject(task);Client.shutdownOutput();
	}
}
