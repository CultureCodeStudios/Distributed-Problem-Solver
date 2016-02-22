package client.SingleThreadedConnectionManagerImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import client.SingleThreadClientConnectionManager;
import problemModule.ProblemModule;
import problemModule.TestProblemModule;
//TODO: Needs work, I started it but didn't finish it.
public class ClientRobustConnectionManager implements SingleThreadClientConnectionManager{
	private Socket Client;
	private DataOutputStream DataOut;
	private ObjectOutputStream obOut;
	private DataInputStream DataIn;
	private ObjectInputStream obIn;
	private int port,attempts,delay;
	
	//UNTESTED
	public ClientRobustConnectionManager(String host,int port,int att,int del) throws IOException, InterruptedException{
		connect();
		setupStreams();
	}
	
	private void connect() throws InterruptedException {
		try {
			Client = new Socket(InetAddress.getByName(null),9090);
			if(Client==null){
				for(int i = 0; i<attempts;i++){
					Thread.sleep(delay);
					retryConnection();
					}
			}
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void retryConnection() {
		try {
			Client = new Socket(InetAddress.getByName(null),9090);
		} catch (UnknownHostException e) {
		
		} catch (IOException e) {
		
		}
	}

	private void setupStreams() throws IOException{
		System.out.println("Connected");
		DataOut = new DataOutputStream(Client.getOutputStream());
		obOut = new ObjectOutputStream(DataOut);
		DataIn = new DataInputStream(Client.getInputStream());
		obIn = new ObjectInputStream(DataIn);
	}

	public Object readObject() throws ClassNotFoundException, IOException {return obIn.readObject();}
	//public void shutdownOutput() throws IOException {Client.shutdownOutput();}
	public void writeObject(TestProblemModule task) throws IOException {obOut.writeObject(task);Client.shutdownOutput();}
	public void close() throws IOException{Client.close();}

	@Override
	public void writeObject(ProblemModule task) throws IOException {
		obOut.writeObject(task);Client.shutdownOutput();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	@Override
	public ProblemModule waitForResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
