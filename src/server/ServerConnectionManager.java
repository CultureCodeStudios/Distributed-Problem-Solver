package server;

import java.io.IOException;
import java.net.Socket;

//ServerConnectionManager
public interface ServerConnectionManager {
	
	public void StartServer(int port);

	void StartServer() throws IOException;
	
	public Runnable newCon(Socket connection) throws IOException;

	void setPort(int P);
	
}
