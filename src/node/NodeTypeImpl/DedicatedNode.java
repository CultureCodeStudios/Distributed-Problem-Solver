package node.NodeTypeImpl;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import node.NodeType;

public class DedicatedNode implements NodeType {
	
	
	private static Socket Node;
	private static DataOutputStream DataOut;
	private static ObjectOutputStream obOut;
	private static DataInputStream DataIn;
	private static ObjectInputStream obIn;
	private static boolean Ready = true;
	private static int Status;
	
	public DedicatedNode(InetAddress host, int port) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException, ExecutionException {
		

	//check for file called "id" to hold unique ID
		File file= new File("id.txt");
		String id=null;

		if ( file.exists() && file.canRead() ) {
			BufferedReader input = new BufferedReader(new FileReader(file));
			id=input.readLine();
			DataOut.writeUTF(id);
			//sending ID
			//output.println( "Found...transmitting contents..." );
		}
		else
		{
			DataOut.writeUTF("-1");
			long newId= DataIn.readLong();
			//sends -1 if not found

		}
	//send the contents to server
	
	//otherwise sends -1
	
	//server sends long with new ID
	
	//probably using the packet interface
	
	//gets unique id, stores in file
	
	//listen on a port hardcoded

		//****** done thus far above

	//receive a packet
	//packet declares number of problem modules

		//we decided to go with 1 problem module per node for now

	//and the type of problem


	
	//once its received, calls the solve method on problem module
	
	//solution is stored internal to module, then that is sent
	
	//open connection to server, serialize solution, send unique id, send it back
	//object reader deserializes automaticaly
	
	}
	@Override
	public void run() {
		
		
		// TODO Auto-generated method stub
		
	}

}
