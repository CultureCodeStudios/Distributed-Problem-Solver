package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import problemModule.TestProblemModule;

public class GridServer {
	static Integer[][] TestA;
	static Integer[][] TestB;
	static Integer[][] TestResult;
	static Integer n;
	static Integer m;
	static String Host;
	static int Port;

	public static void main(String[] args) throws IOException, ClassNotFoundException {

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
		Client.shutdownInput();
		if(RecievedObj instanceof TestProblemModule){
			TestProblemModule Task = (TestProblemModule) RecievedObj;
			//Integer[][] Result = Returned.getResult();
			Task.Solve();
			obOut.writeObject(Task);
		}
		Client.close();	
		Server.close();
	}
}
