package server.TestServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import common.KeepAlive;
import problemModule.*;

public class GridTestServer {
	
	static String Host;
	static int Port;
	//Known to function, does not implement the Node functionality.
	//Use to debug A new ProblemModule
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		//TestProblemModule Task = new TestProblemModule(TestA, TestA, m, m);
		ServerSocket Server = new ServerSocket(9090);
		Socket Client = Server.accept();
		System.out.println("Client Connected");
		DataOutputStream DataOut = new DataOutputStream(Client.getOutputStream());
		ObjectOutputStream obOut = new ObjectOutputStream(DataOut);
		DataInputStream DataIn = new DataInputStream(Client.getInputStream());
		ObjectInputStream obIn = new ObjectInputStream(DataIn);
		
		Object RecievedObj = obIn.readObject();
		System.out.println("Object Recieved");
		//Client.shutdownInput();
		if(RecievedObj instanceof ProblemModule){
			ProblemModule Task = (ProblemModule) RecievedObj;
			Task.Solve();
			obOut.writeObject(new KeepAlive(3L));
			Thread.sleep(1000);
			obOut.writeObject(new KeepAlive(2L));
			Thread.sleep(1000);
			obOut.writeObject(new KeepAlive(1L));
			Thread.sleep(10000);
			obOut.writeObject(new KeepAlive(0L));
			obOut.writeObject(Task);
			//obOut.writeObject(Task);
		}
		Client.close();	
		Server.close();
	}
}
